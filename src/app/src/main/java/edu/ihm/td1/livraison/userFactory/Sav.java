package edu.ihm.td1.livraison.userFactory;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Sav extends User{

    protected Sav(String prenom, String nom, String imgProfile){
        super(prenom,nom,imgProfile);
    }

    protected Sav(Parcel in) {
        super(in.readString(), in.readString(), in.readString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(getPrenom());
        dest.writeString(getNom());
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