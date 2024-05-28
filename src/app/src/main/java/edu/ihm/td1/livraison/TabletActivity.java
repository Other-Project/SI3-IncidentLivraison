package edu.ihm.td1.livraison;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserFactory;

public class TabletActivity extends AppCompatActivity {

    public static Report report;

    public static void setReport(Parcelable parcelable) {
        report = (Report) parcelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
        Log.d("TabletAcivity", "in tablet activity ");

        ListFragment fragmentPending = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.pending);
        ListFragment fragmentFinished = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.finished);
        Log.d("Fragment Pending", (fragmentPending==null)? "null" : "not null");
        Log.d("Fragment Finished", (fragmentFinished==null)? "null" : "not null");

        /*
        ((TextView) findViewById(R.id.pendingtablet).findViewById(R.id.listTitle)).setText(getString(R.string.titlePendingDeliveries));
        ((TextView) findViewById(R.id.finishedtablet).findViewById(R.id.listTitle)).setText(getString(R.string.titleFinishedDeliveries));
        */

        Bundle bundlePending = new Bundle();
        Bundle bundleFinished = new Bundle();

        bundlePending.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.stream().filter(order -> !order.isTreated()).collect(Collectors.toList()));
        bundleFinished.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.stream().filter(Report::isTreated).collect(Collectors.toList()));

        fragmentPending.setArguments(bundlePending);
        fragmentFinished.setArguments(bundleFinished);

        fragmentPending.notifyCollectionReady();
        fragmentFinished.notifyCollectionReady();
    }

        /*
        fragmentPending.setArguments(bundlePending);
        fragmentFinished.setArguments(bundleFinished);

        //on envoie les données aux fragments
        fragmentPending.notifyCollectionReady();
        fragmentFinished.notifyCollectionReady();


    }

        /*
        TextView text1 = findViewById(R.id.lists).findViewById(R.id.pendingtablet).findViewById(R.id.listTitle);
        Log.d("text1",(text1==null)? "null" : "not null" );

        TextView text2 = findViewById(R.id.finishedtablet).findViewById(R.id.listTitle);
        Log.d("text1",(text1==null)? "null" : "not null" );

        Log.d("string", (getString(R.string.PendingReports)));
        *
        ((TextView) findViewById(R.id.pendingtablet).findViewById(R.id.listTitle)).setText(getString(R.string.PendingReports));
        ((TextView) findViewById(R.id.finishedtablet).findViewById(R.id.listTitle)).setText(getString(R.string.TreatedReports));
    }

        UserFactory userFactory = new UserFactory();
        User user = userFactory.getUser("SAV");
        // data setup
        Toolbar toolbar= (Toolbar) getSupportFragmentManager().findFragmentById(R.id.toolbar);
        assert toolbar != null;
        Bundle bundle = new Bundle();
        bundle.putParcelable("User", user);
        toolbar.setArguments(bundle); // send data to fragment
        toolbar.notifyClientIsReady(findViewById(R.id.toolbar).findViewById(R.id.client_toolbar));

        int currentOrientation = getResources().getConfiguration().orientation;
        ((LinearLayout) findViewById(R.id.lists)).setOrientation(currentOrientation == Configuration.ORIENTATION_LANDSCAPE ? LinearLayout.HORIZONTAL : LinearLayout.VERTICAL);

        //deuxième activité
        Log.d(TAG, "Lancement de l'activity");
        report = (Report)getIntent().getParcelableExtra("report");
        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        Button signalementTraite = findViewById(R.id.button_traite);

        signalementTraite.setOnClickListener(view -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(40);
            Report.setIsTreated(report, true);
            startActivity(new Intent(getApplicationContext(), ReportListActivity.class));
        });

        Bundle bundle1 = new Bundle();
        bundle.putParcelable(ObjectDisplayFragment.ARG_ITEM, report);
        objectFrag.setArguments(bundle1);

        TextView text = findViewById(R.id.text_signalement);
        text.setText(report.descriptionProbleme);

        ImageView image = findViewById(R.id.image_signalement);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //On initialise les fragments
        ListFragment fragmentPending = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.pending);
        ListFragment fragmentFinished = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.finished);

        //on prépare les données qu'on leur envoie
        Bundle bundlePending = new Bundle();
        Bundle bundleFinished = new Bundle();

        bundlePending.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.stream().filter(order -> !order.isTreated()).collect(Collectors.toList()));
        bundleFinished.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.stream().filter(Report::isTreated).collect(Collectors.toList()));

        fragmentPending.setArguments(bundlePending);
        fragmentFinished.setArguments(bundleFinished);

        //on envoie les données aux fragments
        fragmentPending.notifyCollectionReady();
        fragmentFinished.notifyCollectionReady();

    }

         */

}

