package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private String TAG = getClass().getSimpleName();
    private final List<Parcelable> itemList = new ArrayList<>();

    private ListAdapter listAdapter;
    private String title;
    private TextView listTitle;
    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new ListAdapter(getContext(), itemList);
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void refreshTitle(){
        this.listTitle.setText(this.title);
    }

    public void notifyCollectionReady() {
        Bundle bundle = getArguments();
        if (bundle == null) return;
        ArrayList<Parcelable> list = bundle.getParcelableArrayList("list");
        if (list == null) return;
        itemList.clear();
        itemList.addAll(list);
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_liste_livraisons, container, false);
        // Log.d(TAG, "in onCreateView(), collection = " + collection);
        ListView listView = rootView.findViewById(R.id.List);
        this.listTitle = rootView.findViewById(R.id.listTitle);
        this.listTitle.setText(this.title);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (itemList.get(0) instanceof Order) {
                Intent intent = new Intent(getContext(), OrderViewActivity.class);
                intent.putExtra("order", itemList.get(i));
                startActivity(intent);
                Log.d(TAG, "click sur une order");
            } else {
                if (getResources().getBoolean(R.bool.is_tablet) && !((Report) itemList.get(i)).isTreated()) {
                    TabletActivity.setReport((Report) itemList.get(i));
                    TabletActivity.notifyDataHasChanged();
                    TabletActivity.objectDisplayFragment.setItem(itemList.get(i));
                    TabletActivity.objectDisplayFragment.changeDisplayedObject();
                } else if (!((Report) itemList.get(i)).isTreated()) {
                    Intent intent = new Intent(getContext(), ReviewReportActivity.class);
                    intent.putExtra("report", itemList.get(i));
                    startActivity(intent);
                    Log.d(TAG, "click sur un report");
                }
            }
        });
        return rootView;
    }

}
