package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
        //MapFragment mapFrag = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

        Bundle bundle = new Bundle();
        bundle.putParcelable(ObjectDisplayFragment.ARG_ITEM, order);
        objectFrag.setArguments(bundle);

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
    }
}