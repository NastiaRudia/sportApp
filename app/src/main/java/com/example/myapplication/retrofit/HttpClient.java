package com.example.myapplication.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

public class HttpClient {

    public UserService userService;

    public HttpClient(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, SECONDS)
                .writeTimeout(20, SECONDS)
                .readTimeout(20, SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        userService = retrofit.create(UserService.class);
    }
}

