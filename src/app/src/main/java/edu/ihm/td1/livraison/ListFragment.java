package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private List<Order> collection = new ArrayList<>();
    private CommandAdapter adapter;;

    public ListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CommandAdapter(getContext(), collection);
    }


    public void notifyCollectionReady(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            collection.addAll((ArrayList<Order>) bundle.get("list"));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liste_livraisons, container, false);
        // Log.d(TAG, "in onCreateView(), collection = " + collection);
        ListView listView = rootView.findViewById(R.id.List);
        listView.setAdapter(adapter);

        /*
        spinner.setOnItemClickListener((parent, view, position, id) -> {
            Log.d(TAG, "in onCreateView(), callbackActivity = " + callbackActivity);
            Log.d(TAG, "in onCreateView(), this = " + this);
            callbackActivity.onItemChoosen(this, position);
        });
       */
        return rootView;
    }
}