package edu.ihm.td1.livraison;

import static edu.ihm.td1.livraison.Notification.CHANNEL_1_ID;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeliveryActivity extends AppCompatActivity {
    private ArrayList<Order> deliveries = new ArrayList<>(Order.ORDERS.values().stream().filter(order -> !order.getDelivered()).collect(Collectors.toList()));

    private DeliveryAdapter deliveryAdapter;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        deliveryAdapter = new DeliveryAdapter(getApplicationContext(), deliveries);
        deliveryAdapter.setOnDeliveryDone(this::onDeliveryDone);
        deliveryAdapter.setOnDeliveryIssue(this::onDeliveryIssue);
        ((ListView) findViewById(R.id.next_delivery)).setAdapter(deliveryAdapter);

        // map fragment initialisation
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.delivery_map);
        assert mapFragment != null;

        // data setup
        Bundle bundleMap = new Bundle();
        bundleMap.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Order.ORDERS.values().stream().filter(order -> !order.getDelivered()).collect(Collectors.toList()));
        mapFragment.setArguments(bundleMap); // send data to fragment
        mapFragment.notifyCollectionReady();

    }

    private void onDeliveryDone(Order delivery) {
        delivery.setDelivered(true);
        Order.ORDERS.put(delivery.getId(), delivery);
        deliveries.remove(delivery);
        deliveryAdapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), "Done " + delivery.getAddress(), Toast.LENGTH_SHORT).show();

        //update orders
        mapFragment.updateOrders(delivery);

        //Send notification
        String title = "Livraison validée";
        String message = "Pour l'adresse : "+delivery.getAddress();
        sendNotificationOnChannel( title, message, CHANNEL_1_ID, NotificationCompat.PRIORITY_DEFAULT );
    }

    private void onDeliveryIssue(Order delivery) {
        Toast.makeText(getApplicationContext(), "Issue with " + delivery.getAddress(), Toast.LENGTH_SHORT).show();
    }

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
}
