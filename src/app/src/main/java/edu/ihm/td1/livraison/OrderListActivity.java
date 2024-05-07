package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

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

        ((TextView) findViewById(R.id.pending).findViewById(R.id.listTitle)).setText(getString(R.string.titlePendingDeliveries));
        ((TextView) findViewById(R.id.finished).findViewById(R.id.listTitle)).setText(getString(R.string.titleFinishedDeliveries));
        ViewGroup.LayoutParams finishedListParams = findViewById(R.id.finished).findViewById(R.id.List).getLayoutParams();
        finishedListParams.height = (int) ((int) 255 * getApplicationContext().getResources().getDisplayMetrics().density);
        findViewById(R.id.finished).findViewById(R.id.List).setLayoutParams(finishedListParams);
    }
}