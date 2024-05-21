package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReportListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.command_list_layout);

        ((TextView) findViewById(R.id.pending).findViewById(R.id.listTitle)).setText(getString(R.string.PendingReports));
        ((TextView) findViewById(R.id.finished).findViewById(R.id.listTitle)).setText(getString(R.string.TreatedReports));
        ViewGroup.LayoutParams finishedListParams = findViewById(R.id.finished).findViewById(R.id.List).getLayoutParams();
        finishedListParams.height = (int) ((int) 255 * getApplicationContext().getResources().getDisplayMetrics().density);
        findViewById(R.id.finished).findViewById(R.id.List).setLayoutParams(finishedListParams);
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
