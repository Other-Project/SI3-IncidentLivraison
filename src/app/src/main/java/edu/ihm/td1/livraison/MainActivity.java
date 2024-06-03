package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.function.Consumer;

import edu.ihm.td1.livraison.userFactory.Client;
import edu.ihm.td1.livraison.userFactory.ClientFactory;
import edu.ihm.td1.livraison.userFactory.Livreur;
import edu.ihm.td1.livraison.userFactory.LivreurFactory;
import edu.ihm.td1.livraison.userFactory.SavFactory;
import edu.ihm.td1.livraison.userFactory.UserFactory;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "Livraison" + getClass().getSimpleName();
    private TextView profileSelection;
    private ConstraintLayout user_buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        ImageView deliveryButton = findViewById(R.id.icone_livreur);
        deliveryButton.setOnClickListener(view ->{
            Toolbar.setUser(new LivreurFactory().build());
            startActivity(new Intent(getApplicationContext(), DeliveryActivity.class));
        } );

        ImageView clientButton = findViewById(R.id.icone_client);

        clientButton.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), OrderListActivity.class)));
       

        ImageView savButton = findViewById(R.id.icone_service_client);
        savButton.setOnClickListener(view -> {
            if(isTablet()){
                Log.d(TAG, "En mode tablette");
                startActivity(new Intent(getApplicationContext(), TabletActivity.class));
            }
            else{
                Log.d(TAG, "pas en mode tablette");
                startActivity(new Intent(getApplicationContext(), ReportListActivity.class));
            }
        });


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.d(TAG, "erreur");
            } else {
                //((TextView) findViewById(R.id.token)).setText("token = " + task.getResult());
                Log.d(TAG, "Token reçu :" + task.getResult());
                //task.getResult() => dans le log de base
            }
        });
        ImageView logo = findViewById(R.id.appLogo);
        TextView welcomeMessage = findViewById(R.id.textView2);
        profileSelection = findViewById(R.id.profile_selection);
        user_buttons = findViewById(R.id.user_buttons);

        Animation firstFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.first_fadein);
        Animation secondFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.second_fadein);
        Animation thirdFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.third_fadein);

        profileSelection.setVisibility(View.INVISIBLE);
        user_buttons.setVisibility(View.INVISIBLE);
        welcomeMessage.startAnimation(firstFadeIn);
        logo.startAnimation(firstFadeIn);

        firstFadeIn.setAnimationListener(animationEndListener(animation -> {
            profileSelection.setVisibility(View.VISIBLE);
            profileSelection.startAnimation(secondFadeIn);
        }));

        secondFadeIn.setAnimationListener(animationEndListener(animation -> {
            user_buttons.setVisibility(View.VISIBLE);
            user_buttons.startAnimation(thirdFadeIn);
        }));

        Toolbar.setUser(null);
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.is_tablet);

    }

    private Animation.AnimationListener animationEndListener(Consumer<Animation> onEnd) {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onEnd.accept(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }
}