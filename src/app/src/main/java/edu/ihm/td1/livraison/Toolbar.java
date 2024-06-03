package edu.ihm.td1.livraison;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserMap;

public class Toolbar extends Fragment {
    private static Integer connectedUser;

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
            if (connectedUser == null) return;
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("User", connectedUser);
            startActivity(intent);
        });

        User user = getUser();
        if (user != null)
            profile.setText(String.format("%s %s", user.getPrenom(), user.getNom()));

        return rootView;
    }

    public static User getUser() {
        return connectedUser == null ? null : UserMap.getUser(connectedUser);
    }

    public static void setUser(Integer connectedUser) {
        Toolbar.connectedUser = connectedUser;
    }
}