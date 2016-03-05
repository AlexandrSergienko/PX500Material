package com.test.android.a500px.picx500.api;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by Alex on 05.03.2016.
 */
public class PhotoService {

    private PhotoService() { }

    public static PhotosApi createPhotoService() {
        Retrofit.Builder builder = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.500px.com");

        OkHttpClient client = new OkHttpClient.Builder().build();
        builder.client(client);

        return builder.build().create(PhotosApi.class);
    }
}
