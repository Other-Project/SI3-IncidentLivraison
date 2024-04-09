package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "Livraison" + getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton deliveryButton = findViewById(R.id.icone_livreur);
        deliveryButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DeliveryActivity.class)));

        ImageButton clientButton = findViewById(R.id.icone_client);
        clientButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CommandListActivity.class)));

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