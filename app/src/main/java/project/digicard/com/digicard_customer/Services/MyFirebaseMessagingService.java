package project.digicard.com.digicard_customer.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import project.digicard.com.digicard_customer.AdDisplay;
import project.digicard.com.digicard_customer.MainActivity;
import project.digicard.com.digicard_customer.R;

/**
 * Created by sarveshpalav on 22/03/17.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessageService";
    Bitmap bitmap;
    public static String AdTitle,AdDesc,AdImageurl;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        //
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().get("data"));

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.
        JSONObject a =null;
        try {
             a = new JSONObject( remoteMessage.getData().get("data"));

        } catch (JSONException e) {
            Log.d("I reached","lol");
            e.printStackTrace();
        }
        //message will contain the Push Message
        String message = remoteMessage.getData().get("message");

        //imageUri will contain URL of the image to be displayed with Notification
        String imageUri = null;
        try {
            imageUri = a.getString("image");
            message = a.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened.
        //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened.
        String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");

        //To get a Bitmap image from the URL received
        bitmap = getBitmapfromUrl(imageUri);
        Log.d(TAG, "Url: " + imageUri);


        sendNotification(message, bitmap,imageUri);

    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

        private void sendNotification(String messageBody, Bitmap image, String ImageUrl) {
            Intent intent = new Intent(this,AdDisplay.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //    intent.putExtra("AdDesc", messageBody);
          //  intent.putExtra("AdTitle",messageBody);
        //    intent.putExtra("AdImageUrl",ImageUrl);
            AdTitle = messageBody;
            AdDesc = messageBody;
            AdImageurl = ImageUrl;
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setLargeIcon(image)/*Notification icon image*/
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(messageBody)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(image))/*Notification with Image*/
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }


}
