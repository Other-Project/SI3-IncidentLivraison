package edu.ihm.td1.livraison;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObjectDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectDisplayFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_ITEM = "item";
    View currentView;
    // TODO: Rename and change types of parameters
    private Parcelable item;

    public ObjectDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param item item
     * @return A new instance of fragment ObjectDisplayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ObjectDisplayFragment newInstance(Parcelable item) {
        ObjectDisplayFragment fragment = new ObjectDisplayFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ITEM, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = getArguments().getParcelable(ARG_ITEM);
        }
    }

    public void setItem(Parcelable p){
        item = p;
    }

    public void changeDisplayedObject(){
        ImageView img = currentView.findViewById(R.id.imageColis);
        TextView txt = currentView.findViewById(R.id.textView);
        if(item instanceof Order){
            Order o = (Order)item;
            img.setImageResource(o.getImage());
            txt.setText(o.fullDesc());
        }else if(item instanceof Report){
            Report r = (Report)item;
            img.setImageResource(r.getOrder().getImage());
            txt.setText(r.getOrder().getDescription());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        item = getArguments().getParcelable(ARG_ITEM);
        View rootView = inflater.inflate(R.layout.fragment_object_display, container, false);
        this.currentView = rootView;
        ImageView img = rootView.findViewById(R.id.imageColis);
        TextView txt = rootView.findViewById(R.id.textView);
        if(item instanceof Order){
            Order o = (Order)item;
            img.setImageResource(o.getImage());
            txt.setText(o.fullDesc());
        }else if(item instanceof Report){
            Report r = (Report)item;
            img.setImageResource(r.getOrder().getImage());
            txt.setText(r.getOrder().getDescription());
        }
        return rootView;
    }
}