package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;


/**
 * This is the View
 */
public class DeliveryActivity extends AppCompatActivity implements Observer {

    private DeliveryViewModel viewModel;

    private DeliveryAdapter deliveryAdapter;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        viewModel = new DeliveryViewModel(getApplicationContext());

        deliveryAdapter = new DeliveryAdapter(getApplicationContext(), viewModel.getDeliveries());
        deliveryAdapter.setOnDeliveryDone(viewModel::onDeliveryDone);
        deliveryAdapter.setOnDeliveryIssue(viewModel::onDeliveryIssue);
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

    @Override
    public void update(Observable o, Object arg) {
        //update orders
        deliveryAdapter.notifyDataSetChanged();
        mapFragment.notifyCollectionReady();
    }
}
