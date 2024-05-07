package edu.ihm.td1.livraison;

import static edu.ihm.td1.livraison.Notification.CHANNEL_1_ID;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ProfileActivity extends AppCompatActivity {
    private void sendNotificationOnChannel(String title, String content, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.check)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(priority);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        NotificationManagerCompat.from(this).notify(0, notification.build());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById( R.id.button ).setOnClickListener( click -> {
            String title = "Livraisoon";
            String message = "Modifications enregristré";
            sendNotificationOnChannel( title, message, CHANNEL_1_ID, NotificationCompat.PRIORITY_DEFAULT );
        });
    }
}
