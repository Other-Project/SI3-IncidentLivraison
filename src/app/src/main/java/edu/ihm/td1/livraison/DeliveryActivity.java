package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeliveryActivity extends AppCompatActivity {
    private ArrayList<Order> deliveries = new ArrayList<>(Order.ORDERS.values().stream().filter(order -> !order.getDelivered()).collect(Collectors.toList()));

    private DeliveryAdapter deliveryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        deliveryAdapter = new DeliveryAdapter(getApplicationContext(), deliveries);
        deliveryAdapter.setOnDeliveryDone(this::onDeliveryDone);
        deliveryAdapter.setOnDeliveryIssue(this::onDeliveryIssue);
        ((ListView) findViewById(R.id.next_delivery)).setAdapter(deliveryAdapter);

        // map fragment initialisation
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.delivery_map);
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
    }

    private void onDeliveryIssue(Order delivery) {
        Toast.makeText(getApplicationContext(), "Issue with " + delivery.getAddress(), Toast.LENGTH_SHORT).show();
    }
}
