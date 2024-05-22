package edu.ihm.td1.livraison.userFactory;


import android.os.Parcel;

import androidx.annotation.NonNull;

import edu.ihm.td1.livraison.Order;

public class Livreur implements User{
    protected Livreur() {

    }
    protected Livreur(Parcel in) {

    }
    @Override
    public String getPrenom() {
        return "Camille";
    }

    @Override
    public String getNom() {
        return "Honnete";
    }

    @Override
    public String getNumeroTelephone() {
        return "0777777777";
    }

    @Override
    public String getAdresse() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(getPrenom());
        dest.writeString(getNom());
        dest.writeString(getNumeroTelephone());
        dest.writeString(getAdresse());
    }
    public static final Creator<Livreur> CREATOR = new Creator<Livreur>() {
        @Override
        public Livreur createFromParcel(Parcel in) {
            return new Livreur(in);
        }

        @Override
        public Livreur[] newArray(int size) {
            return new Livreur[size];
        }
    };
}
