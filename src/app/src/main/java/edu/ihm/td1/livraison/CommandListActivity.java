package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandListActivity extends AppCompatActivity {

    List<Command> commandeList = new ArrayList<>(Arrays.asList(new Command(1,"Sac à dos Dora", R.drawable.sac, false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac,false)));
    List<Command> finishedCommandList = new ArrayList<>(Arrays.asList(new Command(1,"Sac à dos Dora Taille Adulte", R.drawable.sac,true), new Command(2, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac,true), new Command(2, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac,true), new Command(2, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac,true)));
    ListView pendingListView;
    ListView finishedListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.command_list_layout);
        pendingListView = findViewById(R.id.PendingList);
        finishedListView = findViewById(R.id.FinishedList);
        CommandAdapter pendingCommandAdapter = new CommandAdapter(getApplicationContext(),commandeList);
        CommandAdapter finishedCommandAdapter = new CommandAdapter(getApplicationContext(), finishedCommandList);
        pendingListView.setAdapter(pendingCommandAdapter);
        finishedListView.setAdapter(finishedCommandAdapter);
    }
}