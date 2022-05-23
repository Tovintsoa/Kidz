package com.m1p9.kidz.service;

import com.m1p9.kidz.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @POST("authenticateUser/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("addUser/")
    Call<User> insertUser(@Body User user);

}
