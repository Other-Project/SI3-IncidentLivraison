package edu.ihm.td1.livraison.userFactory;

import android.os.Parcelable;

public abstract class User implements Parcelable {
    private final String prenom;
    private final String nom;
    private final String imageProfile;
    protected User(String prenom, String nom, String imgProfile) {
        this.prenom = prenom;
        this.nom = nom;
        this.imageProfile = imgProfile;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getImageProfile() {
        return imageProfile;
    }
}
