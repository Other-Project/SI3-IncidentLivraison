package edu.ihm.td1.livraison;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.test_activity);

       Button button = findViewById(R.id.MonBouton);
       button.setOnClickListener(view->{
           Calendar cal = Calendar.getInstance();
           Intent intent = new Intent(Intent.ACTION_EDIT);
           intent.setType("vnd.android.cursor.item/event");
           //intent.putExtra("beginTime", cal.getTimeInMillis());
           intent.putExtra("allDay", true);
           //intent.putExtra("rrule", "FREQ=YEARLY");
           //intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
           intent.putExtra("title", "Livraison de mon colis");
           intent.putExtra("eventLocation", "au point relais sah");
           startActivity(intent);
       });
    }
}
