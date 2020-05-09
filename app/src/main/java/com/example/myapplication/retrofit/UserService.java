package com.example.myapplication.retrofit;

import com.example.myapplication.retrofit.models.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("user/register")
    Call<Response<User>> register(@Body User user);

    @POST("user/authorise")
    Call<ResponseBody> authorise(@Body User user);

    @GET("user/list")
    Call<ResponseBody> getUsersList();

}
