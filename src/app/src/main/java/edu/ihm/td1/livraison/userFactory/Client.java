package edu.ihm.td1.livraison.userFactory;

import android.os.Parcel;

import androidx.annotation.NonNull;

public class Client extends User{
    private final long numTelephone;
    private final Adresse adresse;
    protected Client(Parcel in) {
        this(in.readString(), in.readString(), in.readString(),in.readLong(),in.readParcelable(Adresse.class.getClassLoader()));
    }

    protected Client(String prenom, String nom, String imageProfile,long numTelephone, Adresse adresse) {
        super(prenom,nom,imageProfile);
        this.numTelephone = numTelephone;
        this.adresse = adresse;
    }

    public long getNumTelephone() {
        return numTelephone;
    }

    public Adresse getAdresse() {
        return adresse;
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
        dest.writeParcelable(getAdresse(),flags);
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
