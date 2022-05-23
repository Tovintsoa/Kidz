package com.m1p9.kidz.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.m1p9.kidz.MainActivity;
import com.m1p9.kidz.CategoryActivity;
import com.m1p9.kidz.R;
import com.m1p9.kidz.manager.SessionManagement;
import com.m1p9.kidz.service.ApiClient;
import com.m1p9.kidz.service.LoadingDialog;
import com.m1p9.kidz.service.LoginRequest;
import com.m1p9.kidz.service.LoginResponse;
import com.m1p9.kidz.ui.registration.RegistrationActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    public static final String base_url = "https://api-kids.herokuapp.com/user/";
    private EditText username;
    private EditText password;
    private Button login;
    private Button inscription;
    ProgressDialog progressDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        inscription =(Button) findViewById(R.id.inscription);
        //inscription = (TextView) findViewById(R.id.inscription);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingDialog.startLoadingDialog();

                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    loadingDialog.dismissDialog();
                    String message = "Tout les champs sont obligatoires";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();

                }
                else{
                    LoginRequest loginRequest = new LoginRequest();

                    loginRequest.setUsername(username.getText().toString());
                    loginRequest.setPassword(password.getText().toString());
                    loginUser(loginRequest,loadingDialog);

                }
            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }
    protected void onStart(){
        super.onStart();
        checkSession();
    }
    public void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userId = sessionManagement.getSession();
        if(userId != -1){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
    public void loginUser(LoginRequest loginRequest,LoadingDialog loadingDialog){
        Call<LoginResponse> loginResponseCall = ApiClient.getService().loginUser(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();

                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(loginResponse);
                    loadingDialog.dismissDialog();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("data",loginResponse));
                    finish();

                }
                else{
                    loadingDialog.dismissDialog();
                    String message = "La combinaison adresse e-mail / mot de passe n'existe pas";
                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                String message = "Une erreur est survenue. Essayez plutard";
                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }
}