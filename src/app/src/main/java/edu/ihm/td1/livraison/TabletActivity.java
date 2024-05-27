package edu.ihm.td1.livraison;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserFactory;

public class TabletActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        TextView text1 = findViewById(R.id.pending).findViewById(R.id.listTitle);
        Log.d("text1",(text1==null)? "null" : "not null" );

        TextView text2 = findViewById(R.id.finished).findViewById(R.id.listTitle);
        Log.d("text1",(text1==null)? "null" : "not null" );

        Log.d("string", (getString(R.string.PendingReports)));
        //((TextView) findViewById(R.id.pending).findViewById(R.id.listTitle)).setText(getString(R.string.PendingReports));
        //((TextView) findViewById(R.id.finished).findViewById(R.id.listTitle)).setText(getString(R.string.TreatedReports));

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
}
