package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.stream.Collectors;

public class DeliveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        DeliveryAdapter deliveryAdapter = new DeliveryAdapter(getApplicationContext(), Order.ORDERS.stream().filter(order -> !order.getDelivered()).collect(Collectors.toList()));
        deliveryAdapter.setOnDeliveryDone(delivery -> {
            delivery.setDelivered(true);
            deliveryAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Done " + delivery, Toast.LENGTH_SHORT).show();
        });
        deliveryAdapter.setOnDeliveryIssue(delivery -> Toast.makeText(getApplicationContext(), "Issue with " + delivery, Toast.LENGTH_SHORT).show());

        ListView nextDeliveries = findViewById(R.id.next_delivery);
        nextDeliveries.setAdapter(deliveryAdapter);
    }
}
