package edu.ihm.td1.livraison.userFactory;


import android.os.Parcel;

import androidx.annotation.NonNull;

public class Livreur extends User {
    private final long numTelephone;
    protected Livreur(Parcel in) {
        this(in.readString(), in.readString(), in.readString(),in.readLong());
    }

    protected Livreur(String prenom, String nom, String imageProfile, long numTelephone) {
        super(prenom,nom,imageProfile);
        this.numTelephone = numTelephone;
    }

    public long getNumTelephone() {
        return numTelephone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(getPrenom());
        dest.writeString(getNom());
        dest.writeString(getImageProfile());
        dest.writeLong(getNumTelephone());
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
