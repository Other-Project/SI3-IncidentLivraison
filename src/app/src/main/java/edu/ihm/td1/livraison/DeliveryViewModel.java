package edu.ihm.td1.livraison;

import static androidx.core.content.ContextCompat.getSystemService;
import static edu.ihm.td1.livraison.Notification.CHANNEL_1_ID;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * This is the View Model
 * View : {@link DeliveryActivity}
 * Model : {@link Order}
 */
public class DeliveryViewModel extends Observable {
    private final Context context;
    private final ArrayList<Order> deliveries = new ArrayList<>();

    public DeliveryViewModel(Context context) {
        this.context = context;
        deliveries.addAll(Order.ORDERS.values().stream()
                .filter(order -> !order.getDelivered())
                .collect(Collectors.toList()));
    }

    public ArrayList<Order> getDeliveries() {
        return deliveries;
    }

    void onDeliveryDone(Order delivery) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(40);

        delivery.setDelivered(true);
        Order.ORDERS.put(delivery.getId(), delivery);
        deliveries.remove(delivery);
        Toast.makeText(context, "Done " + delivery.getAddress(), Toast.LENGTH_SHORT).show();

        this.setChanged();
        this.notifyObservers(delivery);

        //Send notification
        String title = "Livraison validée";
        String message = "Pour l'adresse : " + delivery.getAddress();
        sendNotificationOnChannel(title, message, CHANNEL_1_ID, NotificationCompat.PRIORITY_DEFAULT);
    }

    void onDeliveryIssue(Order delivery) {
        Toast.makeText(context, "Issue with " + delivery.getAddress(), Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse("smsto:"+delivery.getDestinatedTo().getNumeroTelephone()    );
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Bonjour, c'est le livreur !\nJ'ai un problème avec votre commande n°" + delivery.getId());
        context.startActivity(it);
    }

    void sendNotificationOnChannel(String title, String content, String channelId, int priority) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.check)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(priority);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        NotificationManagerCompat.from(context).notify(0, notification.build());
    }
}
