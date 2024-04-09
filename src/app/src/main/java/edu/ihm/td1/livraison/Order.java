package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order implements Parcelable {

    public static final List<Order> ORDERS = new ArrayList<>(List.of(
            new Order(0, "Sac à dos Dora", R.drawable.sac, false, "435 Rue du Chemin, Valbonne 06560"),
            new Order(1, "Sac Dora Taille Enfant", R.drawable.sac, false, "25 avenue de l’église, Biot 06510"),
            new Order(2, "Sac Dora Taille Enfant", R.drawable.sac, false, "1 bis rue de l’étoile, Antibes 06600"),
            new Order(3, "Sac Dora Taille Enfant", R.drawable.sac, false, "435 Rue du Chemin, Valbonne 06560"),
            new Order(4, "Sac Dora Taille Enfant", R.drawable.sac, false, "25 avenue de l’église, Biot 06510"),
            new Order(5, "Sac à dos Dora Taille Adulte", R.drawable.sac, true, "1 bis rue de l’étoile, Antibes 06600"),
            new Order(6, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac, true, "435 Rue du Chemin, Valbonne 06560"),
            new Order(7, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac, true, "25 avenue de l’église, Biot 06510"),
            new Order(8, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac, true, "1 bis rue de l’étoile, Antibes 06600")
    ));


    private int id;
    private String description;
    private int image;
    private boolean delivered;
    private String address;

    public Order(int id, String desc, int img, boolean del, String address) {
        this.id = id;
        description = desc;
        image = img;
        delivered = del;
        this.address = address;
    }

    protected Order(Parcel in) {
        description = in.readString();
        id = in.readInt();
        image = in.readInt();
        delivered = in.readInt() == 1;
    }

    public String getAddress() {
        return address;
    }

    public GeoPoint getPosition() {
        Random random1 = new Random();
        double latitude = random1.nextDouble() * (43.61567 - 43.57398) + 43.57398;
        double longitude = random1.nextDouble() * (7.11664 - 7.07177) + 7.07177;
        return new GeoPoint(latitude, longitude);
    }

    public double getDistance(double currentLat, double currentLong) {
        return getPosition().distanceToAsDouble(new GeoPoint(currentLat, currentLong));
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(id);
        dest.writeInt(image);
        dest.writeInt(delivered ? 1 : 0);
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
