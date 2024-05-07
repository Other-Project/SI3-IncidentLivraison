package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "Livraison" + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ImageButton deliveryButton = findViewById(R.id.icone_livreur);
        deliveryButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DeliveryActivity.class)));

        ImageButton clientButton = findViewById(R.id.icone_client);
        clientButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), OrderListActivity.class)));

        ImageButton savButton = findViewById(R.id.icone_service_client);
        savButton.setOnClickListener(view-> startActivity(new Intent(getApplicationContext(), ReportListActivity.class)));

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d(TAG, "erreur");
            } else {
                //((TextView) findViewById(R.id.token)).setText("token = " + task.getResult());
                Log.d(TAG, "Token reçu");
                //task.getResult() => dans le log de base
            }
        });
    }
}