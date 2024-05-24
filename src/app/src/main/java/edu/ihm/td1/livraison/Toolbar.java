package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

import edu.ihm.td1.livraison.userFactory.User;

public class Toolbar extends Fragment {
    private String nomPrenom;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.toolbar, container, false);
        ImageView logout = rootView.findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        TextView profile = rootView.findViewById(R.id.client_toolbar);
        profile.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("User", user);
            startActivity(intent);
        });
        profile.setText(nomPrenom);



        return rootView;
    }

    public void notifyClientIsReady(TextView textView) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getParcelable("User");
            nomPrenom = user.getPrenom() + " " + user.getNom();
        }
    }
}