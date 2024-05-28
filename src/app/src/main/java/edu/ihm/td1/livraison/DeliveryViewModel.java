package edu.ihm.td1.livraison;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * This is the View Model
 * View : {@link DeliveryActivity}
 * Model : {@link Order}
 */
public class DeliveryViewModel extends Observable {
    private final Activity activity;
    private final Context context;
    private final ArrayList<Order> deliveries = new ArrayList<>();

    public DeliveryViewModel(Activity activity) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
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
        Notification.sendNotificationOnChannel(activity, title, message);
    }

    void onDeliveryIssue(Order delivery) {
        Toast.makeText(context, "Issue with " + delivery.getAddress(), Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse("smsto:"+delivery.getDestinatedTo().getNumTelephone()    );
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Bonjour, c'est le livreur !\nJ'ai un problème avec votre commande n°" + delivery.getId());
        context.startActivity(it);
    }
}
