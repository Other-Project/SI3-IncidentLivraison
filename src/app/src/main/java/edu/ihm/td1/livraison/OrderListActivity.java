package edu.ihm.td1.livraison;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserFactory;

public class OrderListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.command_list_layout);

        //On initialise les fragments
        ListFragment fragmentPending = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.pending);
        ListFragment fragmentFinished = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.finished);

        //on prépare les données qu'on leur envoie
        Bundle bundlePending = new Bundle();
        Bundle bundleFinished = new Bundle();

        bundlePending.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Order.ORDERS.values().stream().filter(order -> !order.getDelivered()).collect(Collectors.toList()));
        bundleFinished.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Order.ORDERS.values().stream().filter(Order::getDelivered).collect(Collectors.toList()));

        fragmentPending.setArguments(bundlePending);
        fragmentFinished.setArguments(bundleFinished);

        //on envoie les données aux fragments
        fragmentPending.notifyCollectionReady();
        fragmentFinished.notifyCollectionReady();

        fragmentFinished.setTitle(getString(R.string.titlePendingDeliveries));
        fragmentPending.setTitle(getString(R.string.titleFinishedDeliveries));


        UserFactory userFactory = new UserFactory();
        User user = userFactory.getUser("CLIENT");
        // data setup
        Toolbar toolbar= (Toolbar) getSupportFragmentManager().findFragmentById(R.id.toolbar);
        assert toolbar != null;
        Bundle bundleMap = new Bundle();
        bundleMap.putParcelable("User", user);
        toolbar.setArguments(bundleMap); // send data to fragment
        toolbar.notifyClientIsReady(findViewById(R.id.toolbar).findViewById(R.id.client_toolbar));

        int currentOrientation = getResources().getConfiguration().orientation;
        ((LinearLayout) findViewById(R.id.lists)).setOrientation(currentOrientation == Configuration.ORIENTATION_LANDSCAPE ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);
    }
}