package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandListActivity extends AppCompatActivity {

    List<Command> commandeList = new ArrayList<>(Arrays.asList(new Command(1,"Sac à dos Dora", R.drawable.sac, false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false)));
    List<Command> finishedCommandList = new ArrayList<>(Arrays.asList(new Command(1,"Sac à dos Dora Taille Adulte", R.drawable.sac,true), new Command(2, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac,true), new Command(2, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac,true), new Command(2, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac,true)));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.command_list_layout);

        //On initialise les fragments
        ListFragment fragmentPending =(ListFragment) getSupportFragmentManager().findFragmentById(R.id.pending);
        ListFragment fragmentFinished =(ListFragment) getSupportFragmentManager().findFragmentById(R.id.finished);

        //on prépare les données qu'on leur envoie
        Bundle bundlePending = new Bundle();
        Bundle bundleFinished = new Bundle();

        bundlePending.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) commandeList);
        bundleFinished.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) finishedCommandList);

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