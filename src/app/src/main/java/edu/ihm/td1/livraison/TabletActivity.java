package edu.ihm.td1.livraison;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabletActivity extends AppCompatActivity implements IMenuSelect {

    private Report report;
    private ObjectDisplayFragment objectDisplayFragment;
    private TextView textView;

    public void setObjectDisplayFragment(ObjectDisplayFragment o) {
        objectDisplayFragment = o;
    }

    public void setTextView(TextView t) {
        textView = t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);
        Log.d("TabletAcivity", "in tablet activity ");

        ListFragment fragmentPending = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.pending);
        ListFragment fragmentFinished = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.finished);
        Log.d("Fragment Pending", (fragmentPending == null) ? "null" : "not null");
        Log.d("Fragment Finished", (fragmentFinished == null) ? "null" : "not null");


        Bundle bundlePending = new Bundle();
        Bundle bundleFinished = new Bundle();

        bundlePending.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.values().stream().filter(order -> !order.isTreated()).collect(Collectors.toList()));
        bundleFinished.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.values().stream().filter(Report::isTreated).collect(Collectors.toList()));

        fragmentPending.setArguments(bundlePending);
        fragmentFinished.setArguments(bundleFinished);

        fragmentPending.setTitle(getString(R.string.PendingReports));
        fragmentFinished.setTitle(getString(R.string.TreatedReports));

        fragmentPending.notifyCollectionReady();
        fragmentFinished.notifyCollectionReady();

        if (report == null || report.isTreated()) {
            List<Report> reportListPending = Report.REPORTS.values().stream().filter(order -> !order.isTreated()).collect(Collectors.toList());
            Log.d("ReportListPendaing", reportListPending.toString());
            if (!reportListPending.isEmpty()) {
                report = reportListPending.get(0);
                Log.d("Report", (report == null) ? "null" : "not null");
            } else {
                report = null;
                Log.d(TAG, "No report");
            }
        }
        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        setObjectDisplayFragment(objectFrag);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable(ObjectDisplayFragment.ARG_ITEM, report);
        objectFrag.setArguments(bundle1);
        TextView text = findViewById(R.id.text_signalement);
        setTextView(text);
        text.setText(report.getDescriptionProbleme());
        ImageView image = findViewById(R.id.image_signalement);
        image.setImageResource(report.getImgProblem());
        //Deuxième activité :
        //this.refreshReport();


        Button signalementTraite = findViewById(R.id.button_traite);
        signalementTraite.setOnClickListener(view -> {
            if (report == null) return;
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(40);
            Report.setIsTreated(report.getId(), true);
            this.onRefresh();
            this.refreshReport();
        });

    }

    private void refreshReport() {
        if (report == null || report.isTreated()) {
            List<Report> reportListPending = Report.REPORTS.values().stream().filter(order -> !order.isTreated()).collect(Collectors.toList());
            Log.d("ReportListPendaing", reportListPending.toString());
            if (!reportListPending.isEmpty()) {
                report = reportListPending.get(0);
                Log.d("Report", (report == null) ? "null" : "not null");
            } else {
                report = null;
                Log.d(TAG, "No report");
            }
        }

        findViewById(R.id.container_b).setVisibility(report == null ? View.INVISIBLE : View.VISIBLE);
        if (report == null) return;

        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        setObjectDisplayFragment(objectFrag);
        Log.d("Report", (report == null) ? "null" : "not null");
        objectFrag.setItem(report);
        objectFrag.changeDisplayedObject();

        TextView text = findViewById(R.id.text_signalement);
        setTextView(text);
        text.setText(report.getDescriptionProbleme());
        ImageView image = findViewById(R.id.image_signalement);
        image.setImageResource(report.getImgProblem());
    }

    public void onRefresh() {
        //On initialise les fragments
        ListFragment fragmentPending = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.pending);
        ListFragment fragmentFinished = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.finished);

        //on prépare les données qu'on leur envoie
        Bundle bundlePending = new Bundle();
        Bundle bundleFinished = new Bundle();

        bundlePending.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.values().stream().filter(order -> !order.isTreated()).collect(Collectors.toList()));
        bundleFinished.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.values().stream().filter(Report::isTreated).collect(Collectors.toList()));

        fragmentPending.setArguments(bundlePending);
        fragmentFinished.setArguments(bundleFinished);

        //on envoie les données aux fragments
        fragmentPending.notifyCollectionReady();
        fragmentFinished.notifyCollectionReady();

    }

    @Override
    public void selectItem(Report item) {
        report = item;
        textView.setText(item.getDescriptionProbleme());
        objectDisplayFragment.setItem(item);
        objectDisplayFragment.changeDisplayedObject();
    }
}

