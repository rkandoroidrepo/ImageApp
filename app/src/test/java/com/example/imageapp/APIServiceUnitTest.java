package com.example.imageapp;

import com.example.imageapp.modal.SizeResponse;
import com.example.imageapp.repo.APIServices;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class APIServiceUnitTest {


    private static String BASE_URL = "https://api.flickr.com/";
    private APIServices apiService;

    @Before
    public void createService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        apiService = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build().create(APIServices.class);

    }

    @Test
    public void getPhotos() {
        try {
            String method = "flickr.people.getPublicPhotos";
            String apiKey = "227be805b3d6e934926d049533bb938a";
            String userId = "135487628%40N06";
            String format = "json";
            String jsoncallback = "1";
            Response response = apiService.getPhotos(method, apiKey, userId, format,
                    jsoncallback).execute();
            assertEquals(response.code(), 200);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSizes() {
        String method = "flickr.photos.getSizes";
        String apiKey = "227be805b3d6e934926d049533bb938a";
        String userId = "135487628%40N06";
        String format = "json";
        String jsoncallback = "1";
        String photoId = "20463141838";

        Flowable<SizeResponse> responseFlowable = apiService.getSize2(method, photoId, apiKey, userId, format,
                jsoncallback);
        responseFlowable.subscribe(sizeResponse -> {
            assertNotNull(sizeResponse);
            assertNotNull(sizeResponse.getSizes());

        });
    }
}
