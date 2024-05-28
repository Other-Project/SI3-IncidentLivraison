package edu.ihm.td1.livraison.userFactory;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Adresse implements Parcelable {
    private String pays;
    private String ville;
    private String rue;
    private int numero;
    public Adresse(String pays, String ville, String rue, int numero) {
        this.pays = pays;
        this.ville = ville;
        this.rue = rue;
        this.numero = numero;
    }

    protected Adresse(Parcel in) {
        pays = in.readString();
        ville = in.readString();
        rue = in.readString();
        numero = in.readInt();
    }

    public static final Creator<Adresse> CREATOR = new Creator<Adresse>() {
        @Override
        public Adresse createFromParcel(Parcel in) {
            return new Adresse(in);
        }

        @Override
        public Adresse[] newArray(int size) {
            return new Adresse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(pays);
        dest.writeString(ville);
        dest.writeString(rue);
        dest.writeInt(numero);
    }
}
