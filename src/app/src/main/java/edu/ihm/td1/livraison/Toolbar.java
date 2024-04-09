package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Toolbar extends Fragment {
    private TextView profile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.toolbar, container, false);

        profile =  rootView.findViewById(R.id.client_toolbar);
        profile.setOnClickListener(view -> startActivity(new Intent(getContext(), ProfileActivity.class)));

        return rootView;
    }
}