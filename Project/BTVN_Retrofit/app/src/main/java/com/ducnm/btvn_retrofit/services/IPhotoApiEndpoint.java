package com.ducnm.btvn_retrofit.services;

import com.ducnm.btvn_retrofit.dto.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPhotoApiEndpoint {
    @GET("photos")
    Call<List<Photo>> getAll();

    @GET("photos")
    Call<List<Photo>> getPhotosByAlbumId(@Query("albumId") long albumId);

    @GET("photos/{id}")
    Call<Photo> getPhotoById(@Path("id") long id);

    @PUT("photos/{id}")
    Call<Photo> update(@Path("id") long id, @Body Photo post);

    @DELETE("photos/{id}")
    Call<Object> delete(@Path("id") long id);
}
