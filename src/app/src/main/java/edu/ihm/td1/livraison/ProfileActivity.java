package edu.ihm.td1.livraison;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import edu.ihm.td1.livraison.userFactory.Client;
import edu.ihm.td1.livraison.userFactory.Livreur;
import edu.ihm.td1.livraison.userFactory.Sav;
import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserMap;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int userId = getIntent().getIntExtra("User", -1);
        User user = UserMap.getUser(userId);
        assert user != null;

        setContentView(R.layout.activity_profile);
        findViewById(R.id.button).setOnClickListener(click -> {
            String title = "Livraisoon";
            String message = "Modifications enregristrées";
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icone_client);
            Notification.sendNotificationOnChannel(this, title, message, bitmap);
        });

        TextView prenom = findViewById(R.id.prenom_edit);
        TextView numTel = findViewById(R.id.num_tel_edit);
        TextView adresse = findViewById(R.id.adress_edit);
        prenom.setText(user.getPrenom());
        TextView nom = findViewById(R.id.nom_edit);
        nom.setText(user.getNom());
        if (user instanceof Sav) {
            findViewById(R.id.adress).setVisibility(View.GONE);
            findViewById(R.id.num_tel).setVisibility(View.GONE);
        } else if (user instanceof Livreur) {
            Livreur livreur = (Livreur) user;
            numTel.setText(String.format(Locale.FRANCE, "%010d", livreur.getNumTelephone()));
            findViewById(R.id.adress).setVisibility(View.GONE);
        } else {
            Client client = (Client) user;
            numTel.setText(String.format(Locale.FRANCE, "%010d", client.getNumTelephone()));
            adresse.setText(client.getAdresse().toString());
        }
    }
}
