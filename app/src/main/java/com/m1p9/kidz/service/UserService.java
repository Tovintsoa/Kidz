package com.m1p9.kidz.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("authenticateUser/")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

}
