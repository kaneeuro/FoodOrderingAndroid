package com.sadicomputing.foodordering.firebase;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sadicomputing.foodordering.activity.accueil.LoginActivity;
import com.sadicomputing.foodordering.activity.comptable.ComptableCommandeActivity;
import com.sadicomputing.foodordering.activity.cuisinier.CuisineCommandeActivity;
import com.sadicomputing.foodordering.activity.serveur.ServeurCommandesdujourActivity;

import org.json.JSONException;
import org.json.JSONObject;

import static com.sadicomputing.foodordering.activity.accueil.LoginActivity.compte;
import static com.sadicomputing.foodordering.activity.accueil.LoginActivity.session;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private Intent intent;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            //if the employee is not logged into the app
            if(session == 0){
                //redirecting to the LoginActivity when clicking on the push notification
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            }else if (session == 1){
                //if the user is logged into the app
                //redirecting to the employee's commandes when clicking on the push notification
                if (compte.getRole().equalsIgnoreCase("SERVEUR")){
                    intent = new Intent(getApplicationContext(), ServeurCommandesdujourActivity.class);
                }else if (compte.getRole().equalsIgnoreCase("CUISINIER")){
                    intent = new Intent(getApplicationContext(), CuisineCommandeActivity.class);
                }else if (compte.getRole().equalsIgnoreCase("COMPTABLE")){
                    intent = new Intent(getApplicationContext(), ComptableCommandeActivity.class);
                }
            }

            //if there is no image
            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}
