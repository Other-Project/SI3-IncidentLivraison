package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;


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
        viewModel.addObserver(this);

        deliveryAdapter = new DeliveryAdapter(getApplicationContext(), viewModel.getDeliveries());
        deliveryAdapter.setOnDeliveryDone(viewModel::onDeliveryDone);
        deliveryAdapter.setOnDeliveryIssue(viewModel::onDeliveryIssue);
        ((ListView) findViewById(R.id.next_delivery)).setAdapter(deliveryAdapter);

        // map fragment initialisation
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.delivery_map);
        assert mapFragment != null;

        // data setup
        Bundle bundleMap = new Bundle();
        bundleMap.putParcelableArrayList("list", viewModel.getDeliveries());
        mapFragment.setArguments(bundleMap); // send data to fragment
        mapFragment.notifyCollectionReady();
    }

    @Override
    public void update(Observable o, Object arg) {
        //update orders
        if (!(arg instanceof Order)) return;
        deliveryAdapter.notifyDataSetChanged();
        mapFragment.updateOrders((Order) arg);
    }
}
