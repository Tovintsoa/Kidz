package com.m1p9.kidz;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiFirebaseMessagingService  extends FirebaseMessagingService {
    private static final String CANAL = "MyNotifyCanal";
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String mymessage = remoteMessage.getNotification().getBody();
        Log.d("FireBase","new notif"+ mymessage);

        Intent intent = new Intent(getApplicationContext(),SplashScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CANAL);
        notificationBuilder.setContentTitle("New Video added");
        notificationBuilder.setContentText(mymessage);

        notificationBuilder.setContentIntent(pendingIntent);

        notificationBuilder.setSmallIcon(R.drawable.abc);

        notificationBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService((NOTIFICATION_SERVICE));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = getString(R.string.notificaton_channel_id);
            String channelTitle = getString(R.string.notification_channel_title);
            String channelDescription = getString(R.string.notification_channe_desc);
            NotificationChannel channel =  new NotificationChannel(channelId,channelTitle,notificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription(channelDescription);
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
        }
        notificationManager.notify(1,notificationBuilder.build());

    }
    @Override
    public void onNewToken(String token) {
        Log.d("FireBase", "Refreshed token: " + token);

        //Toast.makeText(getApplicationContext(), "new token" + token, Toast.LENGTH_LONG).show();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        try {
            sendRegistrationToServer(token);
        } catch (IOException e) {
            Log.d("FireBase", e.getMessage());
            e.printStackTrace();
        }
    }
    private void sendRegistrationToServer(String token) throws IOException {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder().add("Token",token).build();

        //RequestBody body = RequestBody.create("'Token:':'"+token+"'",JSON);
        ;
        Request request = new Request.Builder()
                .url("https://api-kids.herokuapp.com/token/addTokenAndroid")
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        // client.newCall(request).execute();
        //assertThat
    }
}
