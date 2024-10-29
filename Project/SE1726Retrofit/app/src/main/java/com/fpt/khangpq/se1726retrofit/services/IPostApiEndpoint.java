package com.fpt.khangpq.se1726retrofit.services;
import com.fpt.khangpq.se1726retrofit.dto.Post;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IPostApiEndpoint {

    @GET("posts")
    Call<List<Post>> getAll();

    @GET("posts/{id}")
    Call<Post> getById(@Path("id") long id);

    @GET("posts")
    Call<List<Post>> getByUserId(@Query("userId") long uId);

    @PUT("posts/{id}")
    Call<Post> update(@Path("id") long id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Object> delete(@Path("id") long id);
}
