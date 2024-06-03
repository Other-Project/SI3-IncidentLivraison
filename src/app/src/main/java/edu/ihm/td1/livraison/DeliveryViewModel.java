package edu.ihm.td1.livraison;

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
    private final Context context;
    private final ArrayList<Order> deliveries = new ArrayList<>();

    public DeliveryViewModel(Context context) {
        this.context = context;
        deliveries.addAll(OrderMap.getAllOrders().stream()
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
        OrderMap.addOrder(delivery);

        Toast.makeText(context, "Done " + delivery.getAddress(), Toast.LENGTH_SHORT).show();

        this.setChanged();
        this.notifyObservers(delivery);
    }

    public void onDeliveryCancel(Order delivery) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(40);

        delivery.setDelivered(false);

        Toast.makeText(context, "Cancelled " + delivery.getAddress(), Toast.LENGTH_SHORT).show();

        this.setChanged();
        this.notifyObservers(delivery);
    }

    void onDeliveryIssue(Order delivery) {
        Toast.makeText(context, "Issue with " + delivery.getAddress(), Toast.LENGTH_SHORT).show();

        Uri uri = Uri.parse("smsto:"+delivery.getDestinatedTo().getNumTelephone()    );
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", "Bonjour, c'est le livreur !\nJ'ai un problème avec votre commande n°" + delivery.getId());
        context.startActivity(it);
    }
}
