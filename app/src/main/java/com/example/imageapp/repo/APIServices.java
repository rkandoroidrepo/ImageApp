package com.example.imageapp.repo;

import com.example.imageapp.modal.PhotoReponse;
import com.example.imageapp.modal.SizeResponse;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {
    @GET("services/rest/")
    Call<PhotoReponse> getPhotos(@Query("method") String method,
                                 @Query("api_key") String apiKey,
                                 @Query(value = "user_id", encoded = true) String userId,
                                 @Query("format") String format,
                                 @Query("nojsoncallback") String nojsoncallback);

    @GET("services/rest/")
    Flowable<SizeResponse> getSize2(@Query("method") String method,
                                    @Query("photo_id") String photoId,
                                    @Query("api_key") String apiKey,
                                    @Query(value = "user_id", encoded = true) String userId,
                                    @Query("format") String format,
                                    @Query("nojsoncallback") String nojsoncallback);
}
