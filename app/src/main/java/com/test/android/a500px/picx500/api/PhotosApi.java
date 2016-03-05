package com.test.android.a500px.picx500.api;

import com.test.android.a500px.picx500.models.PhotosPage;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Alex on 05.03.2016.
 */
public interface PhotosApi {

    /**
     * See https://developer.github.com/v3/repos/#list-contributors
     */
    @GET("/v1/photos?feature=popular&consumer_key=wB4ozJxTijCwNuggJvPGtBGCRqaZVcF6jsrzUadF")
    Observable<PhotosPage> photosPage(@Query("page") String pageNumber);

}
