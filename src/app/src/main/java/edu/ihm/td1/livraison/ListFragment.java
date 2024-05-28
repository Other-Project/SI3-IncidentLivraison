package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListFragment extends Fragment {
    private static String TAG = "ListFragment";
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
            itemList.clear();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(itemList.get(0) instanceof Order){

                    Intent intent = new Intent(getContext(),OrderViewActivity.class);
                    intent.putExtra("order",itemList.get(i));
                    startActivity(intent);
                    Log.d(TAG, "click sur une order");
                }else{
                    if(getResources().getBoolean(R.bool.is_tablet)){
                        TabletActivity.setReport(itemList.get(i));
                    }
                    if(!((Report)itemList.get(i)).isTreated()){
                        Intent intent = new Intent(getContext(), ReviewReportActivity.class );
                        intent.putExtra("report",itemList.get(i));
                        startActivity(intent);
                        Log.d(TAG,"click sur un report");
                    }
                }
            }
        });
        return rootView;
    }

}
