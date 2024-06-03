package edu.ihm.td1.livraison;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;
/**
 * This is the View
 * ViewModel : {@link DeliveryViewModel}
 * Model : {@link Order}
 */
public class DeliveryActivity extends AppCompatActivity implements Observer {
    private DeliveryViewModel viewModel;
    private DeliveryAdapter deliveryAdapter;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        viewModel = new DeliveryViewModel(this);
        viewModel.addObserver(this);

        deliveryAdapter = new DeliveryAdapter(getApplicationContext(), viewModel.getDeliveries());
        deliveryAdapter.setOnDeliveryDone(viewModel::onDeliveryDone);
        deliveryAdapter.setOnDeliveryCancel(viewModel::onDeliveryCancel);
        deliveryAdapter.setOnDeliveryIssue(viewModel::onDeliveryIssue);
        ((ListView) findViewById(R.id.next_delivery)).setAdapter(deliveryAdapter);

        // map fragment initialisation
        mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.delivery_map);
        assert mapFragment != null;
        mapFragment.setOnLocationChanged(deliveryAdapter::onLocationChanged);

        // data setup
        Bundle bundleMap = new Bundle();
        bundleMap.putParcelableArrayList("list", viewModel.getDeliveries());
        mapFragment.setArguments(bundleMap); // send data to fragment
        mapFragment.notifyCollectionReady();

        int currentOrientation = getResources().getConfiguration().orientation;
        ((LinearLayout) findViewById(R.id.layout)).setOrientation(currentOrientation == Configuration.ORIENTATION_LANDSCAPE ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
    }

    @Override
    public void update(Observable o, Object arg) {
        //update orders
        if (!(arg instanceof Order)) return;
        Order order = (Order) arg;
        deliveryAdapter.notifyDataSetChanged();
        mapFragment.updateOrders(order);

        //Send notification
        String title = "Livraison validée";
        String message = "Pour l'adresse : " + order.getAddress();
        Notification.sendNotificationOnChannel(this, title, message);
    }
}
