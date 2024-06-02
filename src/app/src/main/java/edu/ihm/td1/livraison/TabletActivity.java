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
import java.util.List;
import java.util.stream.Collectors;

import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserFactory;

public class TabletActivity extends AppCompatActivity {

    public static Report report;
    public static ObjectDisplayFragment objectDisplayFragment;
    public static TextView textView;
    public final static Report NO_REPORT = new Report( new Order(0, "Pas de commandes à traiter", 0 ,true,"",0), "Pas de commandes à traiter", "", false,0);
    public static void setReport(Report r) {
        report = r;
    }
    public static void setObjectDisplayFragment(ObjectDisplayFragment o){
        objectDisplayFragment = o;
    }
    public static void setTextView(TextView t){ textView = t;}
    public static void notifyDataHasChanged(){
        textView.setText(report.getDescriptionProbleme());
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


        Bundle bundlePending = new Bundle();
        Bundle bundleFinished = new Bundle();

        bundlePending.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.stream().filter(order -> !order.isTreated()).collect(Collectors.toList()));
        bundleFinished.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) Report.REPORTS.stream().filter(Report::isTreated).collect(Collectors.toList()));

        fragmentPending.setArguments(bundlePending);
        fragmentFinished.setArguments(bundleFinished);

        fragmentPending.setTitle(getString(R.string.PendingReports));
        fragmentFinished.setTitle(getString(R.string.TreatedReports));

        fragmentPending.notifyCollectionReady();
        fragmentFinished.notifyCollectionReady();

        if(report==null || report.isTreated) {
            List<Report> reportListPending = Report.REPORTS.stream().filter(order -> !order.isTreated()).collect(Collectors.toList());
            Log.d("ReportListPendaing", reportListPending.toString());
            if(!reportListPending.isEmpty()){
                report = reportListPending.get(0);
                Log.d("Report", (report==null)? "null" : "not null");
            }
            else{
                report = NO_REPORT;
                Log.d(TAG, "No report");
            }
        }
        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        setObjectDisplayFragment(objectFrag);
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable(ObjectDisplayFragment.ARG_ITEM, report);
        objectFrag.setArguments(bundle1);
        //Deuxième activité :
        //this.refreshReport();


        Button signalementTraite = findViewById(R.id.button_traite);
        signalementTraite.setOnClickListener(view -> {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(40);
            Report.setIsTreated(report, true);
            this.onRefresh();
            this.refreshReport();
        });

    }

    private void refreshReport() {
        if(report==null || report.isTreated) {
            List<Report> reportListPending = Report.REPORTS.stream().filter(order -> !order.isTreated()).collect(Collectors.toList());
            Log.d("ReportListPendaing", reportListPending.toString());
            if(!reportListPending.isEmpty()){
                report = reportListPending.get(0);
                Log.d("Report", (report==null)? "null" : "not null");
            }
            else{
                report = NO_REPORT;
                Log.d(TAG, "No report");
            }
        }
        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        setObjectDisplayFragment(objectFrag);
        Log.d("Report", (report==null)? "null" : "not null");/*
        */
        objectFrag.setItem(report);
        objectFrag.changeDisplayedObject();

        TextView text = findViewById(R.id.text_signalement);
        setTextView(text);
        text.setText(report.descriptionProbleme);
        ImageView image = findViewById(R.id.image_signalement);

    }

    /*


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
*/
    public void onRefresh() {
        Log.d("on Resume", "refresh");
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

