package edu.ihm.td1.livraison.userFactory;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Sav implements User{
    protected Sav() {

    }
    protected Sav(Parcel in) {

    }
    @Override
    public String getPrenom() {
        return "Therese";
    }

    @Override
    public String getNom() {
        return "Ponsable";
    }

    @Override
    public String getNumeroTelephone() {
        return "0767676767";
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
    public static final Creator<Sav> CREATOR = new Creator<Sav>() {
        @Override
        public Sav createFromParcel(Parcel in) {
            return new Sav(in);
        }

        @Override
        public Sav[] newArray(int size) {
            return new Sav[size];
        }
    };
}