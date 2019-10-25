package com.example.imageapp.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imageapp.R;
import com.example.imageapp.modal.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Photo> images;
    private Set<String> subRequest;
    private AddSubRequest addSubRequestListener;
    private Context context;

    ImageAdapter(AddSubRequest addSubRequestListener) {
        this.addSubRequestListener = addSubRequestListener;
    }

    void setData(List<Photo> images) {
        this.images = images;
        notifyDataSetChanged();
    }

    void setRequestData(Set<String> subRequest) {
        this.subRequest = subRequest;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_view, null);
        context = parent.getContext();
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Photo photo = images.get(position);
        ImageViewHolder iv = (ImageViewHolder) holder;
        if (photo.getSizes() != null) {
            Picasso.with(context).load(photo.getSizes().getSize().get(2).getSource())
                    .placeholder(R.drawable.place_holder)
                    .into(iv.imageView);
        } else {
            if (!subRequest.contains(photo.getId())) {
                addSubRequestListener.addRequest(photo);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (images != null) {
            return images.size();
        } else {
            return 0;
        }
    }

    public interface AddSubRequest {
        void addRequest(Photo photo);
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
