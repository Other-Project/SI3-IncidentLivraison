package edu.ihm.td1.livraison;

import static java.util.Objects.*;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ListFragment extends Fragment {

    private List<Parcelable> itemList = new ArrayList<>();

    private ListAdapter listAdapter;


    public ListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new ListAdapter(getContext(), itemList);
    }


    public void notifyCollectionReady(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            itemList.addAll((Collection<? extends Parcelable>) bundle.get("list"));
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liste_livraisons, container, false);
        // Log.d(TAG, "in onCreateView(), collection = " + collection);
        ListView listView = rootView.findViewById(R.id.List);
        listView.setAdapter(listAdapter);
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
