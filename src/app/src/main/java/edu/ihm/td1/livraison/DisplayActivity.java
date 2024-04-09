package edu.ihm.td1.livraison;

import static edu.ihm.td1.livraison.ApplicationDemo.CHANNEL_1_ID;
import static edu.ihm.td1.livraison.ApplicationDemo.CHANNEL_2_ID;
import static edu.ihm.td1.livraison.ApplicationDemo.CHANNEL_3_ID;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class DisplayActivity extends AppCompatActivity {
    private final String TAG = "frallo " + getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //some code
        super.onCreate(savedInstanceState);
    }
    private void sendNotificationOnChannel(String title, String content, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(priority)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        switch (channelId) {
            case CHANNEL_1_ID: notification.setSmallIcon(R.drawable.icon);   break;
            case CHANNEL_2_ID: notification.setSmallIcon(R.drawable.icon);   break;
            case CHANNEL_3_ID: notification.setSmallIcon(R.drawable.icon);   break;
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext()
                , android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: for another course... ;-)
            Log.d(TAG, "permission needed to send notification !");
        }
        else {
            // TODO: for another course... ;-)
            Log.d(TAG, "permission granted… we can send notification");
        }
    }
}