package edu.ihm.td1.livraison;

import static edu.ihm.td1.livraison.Notification.CHANNEL_1_ID;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class ProfileActivity extends AppCompatActivity {
    private void sendNotificationOnChannel(String title, String content, String channelId, int priority) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icone_client);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.check)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(priority)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
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
