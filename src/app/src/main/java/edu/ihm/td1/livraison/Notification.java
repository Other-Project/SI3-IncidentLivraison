package edu.ihm.td1.livraison;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Objects;

public class Notification extends Application {
    public static final String CHANNEL_1_ID = "channel LOW";
    public static final String CHANNEL_2_ID = "channel DEFAULT";
    public static final String CHANNEL_3_ID = "channel HIGH";

    private NotificationChannel createNotificationChannel(String channelId, CharSequence name, int importance, String channelDescription) {
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(channelDescription);
            return channel;
        }
        return null;
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // Créer le NotificationChannel, seulement pour API 26+
            NotificationChannel channel1 = createNotificationChannel(CHANNEL_1_ID, "Information", NotificationManager.IMPORTANCE_LOW, "This Channel has low priority");
            NotificationChannel channel2 = createNotificationChannel(CHANNEL_2_ID, "Notification", NotificationManager.IMPORTANCE_DEFAULT, "This Channel has default priority");
            NotificationChannel channel3 = createNotificationChannel(CHANNEL_3_ID, "Urgence", NotificationManager.IMPORTANCE_HIGH, "This Channel has high priority");
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            NotificationManager manager = getSystemService(NotificationManager.class);
            Objects.requireNonNull(manager).createNotificationChannel(Objects.requireNonNull(channel1));
            Objects.requireNonNull(manager).createNotificationChannel(Objects.requireNonNull(channel2));
            Objects.requireNonNull(manager).createNotificationChannel(Objects.requireNonNull(channel3));
        }
    }


    public static void sendNotificationOnChannel(Activity activity, String title, String content) {
        sendNotificationOnChannel(activity, title, content, null);
    }

    public static void sendNotificationOnChannel(Activity activity, String title, String content, Bitmap icon) {
        sendNotificationOnChannel(activity, title, content, icon, CHANNEL_1_ID, NotificationCompat.PRIORITY_DEFAULT);
    }

    public static void sendNotificationOnChannel(Activity activity, String title, String content, Bitmap icon, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(activity.getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.check)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(priority)
                .setTimeoutAfter(5000);
        if (icon != null) notification.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(icon));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
                && ActivityCompat.checkSelfPermission(activity.getApplicationContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
        else NotificationManagerCompat.from(activity.getApplicationContext()).notify(0, notification.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }
}
