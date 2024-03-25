package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DeliveryActivity extends AppCompatActivity {

    private ArrayList<Delivery> deliveries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        deliveries.add(new Delivery("435 Rue du Chemin, Valbonne 06560"));
        deliveries.add(new Delivery("25 avenue de l’église, Biot 06510"));
        deliveries.add(new Delivery("1 bis rue de l’étoile, Antibes 06600"));

        DeliveryAdapter deliveryAdapter = new DeliveryAdapter(getApplicationContext(), deliveries);
        ListView nextDeliveries = findViewById(R.id.next_delivery);
        nextDeliveries.setAdapter(deliveryAdapter);
    }
}
