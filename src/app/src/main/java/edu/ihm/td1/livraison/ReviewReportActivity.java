package edu.ihm.td1.livraison;

import static android.content.ContentValues.TAG;
import static android.widget.Toast.makeText;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ReviewReportActivity extends AppCompatActivity {

    Report report;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_report);

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

        Bundle bundle = new Bundle();
        bundle.putParcelable(ObjectDisplayFragment.ARG_ITEM, report);
        objectFrag.setArguments(bundle);

        TextView text = findViewById(R.id.text_signalement);
        text.setText(report.descriptionProbleme);

        ImageView image = findViewById(R.id.image_signalement);
        //image.setImageResource(report.imgProblem);

    }
}
