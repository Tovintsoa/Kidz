package com.m1p9.kidz.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.m1p9.kidz.model.User;
import com.m1p9.kidz.service.LoginResponse;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String SHARED_PREFERENCE_NAME = "session";
    private static final String SESSION_KEY = "session_user";
    private static final String SESSION_MAIL = "session_mail_user";
    private static final String SESSION_NAME = "session_name_user";
    private static final String SESSION_USERNAME = "session_username_user";
    private static final String SESSION_PASSWORD = "session_password_user";
    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveSession(LoginResponse user){
        String id = user.getId();
        editor.putInt(SESSION_KEY,Integer.parseInt(id)).commit();
        editor.putString(SESSION_MAIL,user.getuEmail()).commit();
        editor.putString(SESSION_NAME,user.getuName()).commit();
        editor.putString(SESSION_USERNAME,user.getuUsername()).commit();
        editor.putString(SESSION_PASSWORD,user.getuUsername()).commit();


    }
    public int getSession(){
        //return new LoginResponse();
        return sharedPreferences.getInt(SESSION_KEY,-1);
    }
    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }

    public LoginResponse getUserSession(){
        LoginResponse userSession = new LoginResponse(String.valueOf(sharedPreferences.getInt(SESSION_KEY,-1)),
                sharedPreferences.getString(SESSION_NAME,"Nom"),
                sharedPreferences.getString(SESSION_USERNAME,"username"),
                sharedPreferences.getString(SESSION_MAIL,"mail"),
                sharedPreferences.getString(SESSION_PASSWORD,"password")
                );

        return userSession;
    }
}
