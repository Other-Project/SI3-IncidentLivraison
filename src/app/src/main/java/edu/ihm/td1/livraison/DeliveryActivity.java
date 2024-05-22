package edu.ihm.td1.livraison;

import static edu.ihm.td1.livraison.Notification.CHANNEL_1_ID;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Observable;
import java.util.Observer;


import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserFactory;
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

        UserFactory userFactory = new UserFactory();
        User user = userFactory.getUser("LIVREUR");
        // data setup
        Toolbar toolbar= (Toolbar) getSupportFragmentManager().findFragmentById(R.id.toolbar);
        assert toolbar != null;
        Bundle bundle = new Bundle();
        bundle.putParcelable("User", user);
        toolbar.setArguments(bundle); // send data to fragment
        toolbar.notifyClientIsReady(findViewById(R.id.toolbar).findViewById(R.id.client_toolbar));

    }

    @Override
    public void update(Observable o, Object arg) {
        //update orders
        if (!(arg instanceof Order)) return;
        deliveryAdapter.notifyDataSetChanged();
        mapFragment.updateOrders((Order) arg);
        //Send notification
        Order delivery = (Order) arg;
        String title = "Livraison validée";
        String message = "Pour l'adresse : "+delivery.getAddress();
        sendNotificationOnChannel( title, message, CHANNEL_1_ID, NotificationCompat.PRIORITY_DEFAULT );

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
