package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
                    /**
                    Intent intent = new Intent(getContext(),OrderView Activity);
                    intent.putExtra("object",itemList.get(i));
                    startActivity(intent);
                    */
                    Log.d(TAG, "click sur une order");
                }else{
                    /*
                    Intent intent = new Intent(getContext(), ReportViewAcvtivity );
                    intent.putExtra("object",itemList.get(i));
                    startActivity(intent);
                     */
                    Log.d(TAG,"click sur un report");
                }

            }
        });
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
