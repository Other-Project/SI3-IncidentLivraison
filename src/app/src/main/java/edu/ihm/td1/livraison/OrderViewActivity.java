package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OrderViewActivity extends AppCompatActivity {
    private static String TAG = "OrderViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_view);
        Log.d(TAG, "Lancement de l'acitivty");
        Order order = (Order)getIntent().getParcelableExtra("order");
        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        //MapFragment mapFrag = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMap);

        Bundle bundle = new Bundle();
        bundle.putParcelable(ObjectDisplayFragment.ARG_ITEM, order);
        objectFrag.setArguments(bundle);

    }
}