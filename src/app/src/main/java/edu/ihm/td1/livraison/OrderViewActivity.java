package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class OrderViewActivity extends AppCompatActivity {
    private static String TAG = "OrderViewActivity";
    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        Log.d(TAG, "Lancement de l'activity");
        order = (Order)getIntent().getParcelableExtra("order");

        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        MapFragment mapFrag = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);


        // data setup
        Bundle bundleMap = new Bundle();
        Bundle bundle = new Bundle();

        ArrayList<Order> listForFragMap = new ArrayList<>();
        listForFragMap.add(order);

        bundleMap.putParcelableArrayList("list", listForFragMap);
        mapFrag.setArguments(bundleMap); // send data to fragment
        mapFrag.notifyCollectionReady();

        bundle.putParcelable(ObjectDisplayFragment.ARG_ITEM, order);
        objectFrag.setArguments(bundle);
        Log.d("OrderTest", mapFrag.getArguments().toString());

        findViewById(R.id.buttonCalendar).setOnClickListener( (view) ->{
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType("vnd.android.cursor.item/event");
            intent.putExtra("title", "Livraison "+order.getDescription());
            intent.putExtra("description", "Votre colis devrait arriver à cette date, n'oubliez pas vos papiers d'identité !");
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, order.getDate());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, ( order.getDate() + 8*(60*60*1000) ) );
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, order.getAddress());
            startActivity(intent);
        });

        Button reportButton = findViewById(R.id.buttonReport);
        if (!order.getDelivered()) reportButton.setVisibility(View.INVISIBLE);
        reportButton.setOnClickListener( (view) -> {
            Intent intent = new Intent(getApplicationContext(), ReportCommandActivity.class);
            intent.putExtra("order", this.order);
            startActivity(intent);
        });
    }
}