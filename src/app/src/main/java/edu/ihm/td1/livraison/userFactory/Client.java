package edu.ihm.td1.livraison.userFactory;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Client implements User{
    protected Client() {

    }
    protected Client(Parcel in) {

    }
    @Override
    public String getPrenom() {
        return "Eva";
    }

    @Override
    public String getNom() {
        return "Zion";
    }

    @Override
    public String getNumeroTelephone() {
        return "0666666666";
    }

    @Override
    public String getAdresse() {
        return "67 rue de l'arbre rouge, Biot";
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
    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };
}
