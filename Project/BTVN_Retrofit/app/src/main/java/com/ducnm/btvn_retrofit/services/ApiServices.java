package com.ducnm.btvn_retrofit.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServices {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private final IPhotoApiEndpoint iPhotoApiEndpoint;

    private static ApiServices instance;

    private static ApiServices getInstance() {
        if (instance == null) {
            instance = new ApiServices();
        }
        return instance;
    }

    private ApiServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iPhotoApiEndpoint = retrofit.create(IPhotoApiEndpoint.class);
    }

    public static IPhotoApiEndpoint getPhotoApiEndpoint() {
        return getInstance().iPhotoApiEndpoint;
    }

}
