package edu.ihm.td1.livraison;

import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "Livraison" + getClass().getSimpleName();
    private ImageView logo;
    private TextView welcomeMessage;
    private TextView profileSelection;
    private ImageView clientButton;
    private TextView clientText;
    private ImageView deliveryButton;
    private TextView deliveryText;
    private ImageView savButton;
    private TextView savText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        deliveryButton = findViewById(R.id.icone_livreur);
        deliveryButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), DeliveryActivity.class)));

        clientButton = findViewById(R.id.icone_client);
        clientButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), OrderListActivity.class)));

        savButton = findViewById(R.id.icone_service_client);
        savButton.setOnClickListener(view-> startActivity(new Intent(getApplicationContext(), ReportListActivity.class)));

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d(TAG, "erreur");
            } else {
                //((TextView) findViewById(R.id.token)).setText("token = " + task.getResult());
                Log.d(TAG, "Token reçu :"+task.getResult());
                //task.getResult() => dans le log de base
            }
        });
        logo = findViewById(R.id.appLogo);
        welcomeMessage = findViewById(R.id.textView2);
        profileSelection = findViewById(R.id.profile_selection);
        clientText = findViewById(R.id.text_client);
        deliveryText = findViewById(R.id.text_livreur);
        savText = findViewById(R.id.text_service_client);

        Animation firstFadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.first_fadein);
        Animation secondFadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.second_fadein);
        Animation thirdFadeIn = AnimationUtils.loadAnimation(MainActivity.this, R.anim.third_fadein);

        welcomeMessage.startAnimation(firstFadeIn);
        logo.startAnimation(firstFadeIn);

        firstFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                profileSelection.setVisibility(View.VISIBLE);
                profileSelection.startAnimation(secondFadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        secondFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clientButton.setVisibility(View.VISIBLE);
                clientText.setVisibility(View.VISIBLE);
                deliveryButton.setVisibility(View.VISIBLE);
                deliveryText.setVisibility(View.VISIBLE);
                savButton.setVisibility(View.VISIBLE);
                savText.setVisibility(View.VISIBLE);
                clientButton.startAnimation(thirdFadeIn);
                clientText.startAnimation(thirdFadeIn);
                deliveryButton.startAnimation(thirdFadeIn);
                deliveryText.startAnimation(thirdFadeIn);
                savButton.startAnimation(thirdFadeIn);
                savText.startAnimation(thirdFadeIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}