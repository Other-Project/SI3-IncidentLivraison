package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandListActivity extends AppCompatActivity {

    List<Command> commandeList = new ArrayList<>(Arrays.asList(new Command(1,"Sac à dos Dora", R.drawable.sac), new Command(2, "Sac Dora Taille Enfant", R.drawable.sac)));
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.command_list_layout);
        listView = (ListView) findViewById(R.id.PendingList);
        CommandAdapter commandAdapter = new CommandAdapter(getApplicationContext(),commandeList);
        listView.setAdapter(commandAdapter);
    }
}