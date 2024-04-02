package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class Toolbar extends Fragment {
    private ImageView profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.toolbar, container, false);

        profile = getView().findViewById(R.id.icone_client);
        profile.setOnClickListener(view -> startActivity(new Intent(getContext(), DeliveryActivity.class)));

        return rootView;
    }
}