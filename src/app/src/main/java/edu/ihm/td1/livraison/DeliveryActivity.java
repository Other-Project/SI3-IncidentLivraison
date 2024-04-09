package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeliveryActivity extends AppCompatActivity {
    private ArrayList<Order> deliveries = new ArrayList<>(Order.ORDERS.values().stream().filter(order -> !order.getDelivered()).collect(Collectors.toList()));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);


        DeliveryAdapter deliveryAdapter = new DeliveryAdapter(getApplicationContext(), deliveries);
        deliveryAdapter.setOnDeliveryDone(delivery -> {
            delivery.setDelivered(true);
            Order.ORDERS.put(delivery.getId(), delivery);
            deliveries.remove(delivery);
            deliveryAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Done " + delivery.getAddress(), Toast.LENGTH_SHORT).show();
        });
        deliveryAdapter.setOnDeliveryIssue(delivery -> {
            Toast.makeText(getApplicationContext(), "Issue with " + delivery.getAddress(), Toast.LENGTH_SHORT).show();
        });

        ListView nextDeliveries = findViewById(R.id.next_delivery);
        nextDeliveries.setAdapter(deliveryAdapter);
    }
}
