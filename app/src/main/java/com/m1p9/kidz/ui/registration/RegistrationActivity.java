package com.m1p9.kidz.ui.registration;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m1p9.kidz.MainActivity;
import com.m1p9.kidz.R;
import com.m1p9.kidz.manager.SessionManagement;
import com.m1p9.kidz.model.User;
import com.m1p9.kidz.service.ApiClient;
import com.m1p9.kidz.service.LoginRequest;
import com.m1p9.kidz.service.LoginResponse;
import com.m1p9.kidz.ui.login.LoginActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private static Button btn_inscription;
    private static EditText nomPrenom;
    private static EditText loginString;
    private static EditText email;
    private  static EditText password1;
    private  static EditText password2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF6200EE"));
        actionBar.setBackgroundDrawable(colorDrawable);


        btn_inscription = findViewById(R.id.btn_insc);
        nomPrenom = findViewById(R.id.nomPrenom);
        loginString = findViewById(R.id.loginString);
        email = findViewById(R.id.email);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);

        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(nomPrenom.getText().toString()) || TextUtils.isEmpty(loginString.getText().toString())
                || TextUtils.isEmpty(password2.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) ||
                        TextUtils.isEmpty(password1.getText().toString())
                ){

                    String message = "Tout les champs sont obligatoires";
                    Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_LONG).show();

                }
                else if(!password1.getText().toString().equals(password2.getText().toString())){
                    String message = "Les deux mot de passes doivent être identiques";
                    Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else if(!isEmailValid(email.getText().toString())){
                    String message = "Vérifier le format de l'adresse Email";
                    Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_LONG).show();
                }
                else{
                   User user = new User(nomPrenom.getText().toString(),loginString.getText().toString(),email.getText().toString(),password1.getText().toString());
                   inscriptionUser(user);

                }
            }
        });

    }
    private void inscriptionUser(User user){
        Call<User> userCall = ApiClient.getService().insertUser(user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                   /* User userResponse = response.body();*/
                    String message = "Inscription effectué";
                    Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_LONG).show();

                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();

                }
                else{

                    String message = "La combinaison adresse e-mail / mot de passe n'existe pas";
                    Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                String message = "Une erreur est survenue. Vérifier que l'adresse email n'est pas encore utilisé";
                Toast.makeText(RegistrationActivity.this,message,Toast.LENGTH_LONG).show();
            }

        });
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}