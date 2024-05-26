package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.ihm.td1.livraison.userFactory.Client;
import edu.ihm.td1.livraison.userFactory.User;
import edu.ihm.td1.livraison.userFactory.UserFactory;

/**
 * This is the Model
 * View : {@link DeliveryActivity}
 * ViewModel : {@link DeliveryViewModel}
 */
public class Order implements Parcelable {

    public static final HashMap<Integer, Order> ORDERS = new HashMap<>(Stream.of(
            new Order(0, "Sac à dos Dora", R.drawable.sac, false, "435 Rue du Chemin, Valbonne 06560", new GregorianCalendar(2024,5,18,9,0).getTimeInMillis()),
            new Order(1, "Sac Dora Taille Enfant", R.drawable.sac, false, "25 avenue de l’église, Biot 06510", new GregorianCalendar(2024,5,25,9,0).getTimeInMillis()),
            new Order(2, "Sac Dora Taille Enfant", R.drawable.sac, false, "1 bis rue de l’étoile, Antibes 06600", new GregorianCalendar(2024,12,18,9,0).getTimeInMillis()),
            new Order(3, "Sac Dora Taille Enfant", R.drawable.sac, false, "435 Rue du Chemin, Valbonne 06560", new GregorianCalendar(2024,7,14,9,0).getTimeInMillis()),
            new Order(4, "Sac Dora Taille Enfant", R.drawable.sac, false, "25 avenue de l’église, Biot 06510", new GregorianCalendar(2024,9,28,9,0).getTimeInMillis()),
            new Order(5, "Sac à dos Dora Taille Adulte", R.drawable.sac, true, "1 bis rue de l’étoile, Antibes 06600", new GregorianCalendar(2024,8,1,9,0).getTimeInMillis()),
            new Order(6, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac, true, "435 Rue du Chemin, Valbonne 06560", new GregorianCalendar(2024,8,25,9,0).getTimeInMillis()),
            new Order(7, "Sac Dora Edition limitée Taille Adulte", R.drawable.sac, true, "25 avenue de l’église, Biot 06510", new GregorianCalendar(2024,8,18,9,0).getTimeInMillis()),
            new Order(8, "Sac Dora Edition limitée Taille Enfant", R.drawable.sac, true, "1 bis rue de l’étoile, Antibes 06600", new GregorianCalendar(2024,8,8,9,0).getTimeInMillis())
    ).collect(Collectors.toMap(Order::getId, Function.identity())));


    private int id;
    private String description;
    private int image;
    private boolean delivered;
    private String address;
    private long date;
    private GeoPoint position;

    private User destinatedTo;

    public Order(int id, String desc, int img, boolean del, String a, long d) {
        this.id = id;
        description = desc;
        image = img;
        delivered = del;
        this.address = a;
        this.date = d;
        UserFactory factory = new UserFactory();
        this.destinatedTo = factory.getUser("CLIENT");
        position = createPosition();
    }

    protected Order(Parcel in) {
        description = in.readString();
        address = in.readString();
        id = in.readInt();
        image = in.readInt();
        delivered = in.readInt() == 1;
        date = in.readLong();
        UserFactory factory = new UserFactory();
        this.destinatedTo = factory.getUser("CLIENT");
        position = createPosition();
    }

    public String getAddress() {
        return address;
    }

    private GeoPoint createPosition() {
        Random random1 = new Random();
        double latitude = random1.nextDouble() * (43.61567 - 43.57398) + 43.57398;
        double longitude = random1.nextDouble() * (7.11664 - 7.07177) + 7.07177;
        return new GeoPoint(latitude, longitude);
    }

    public GeoPoint getPosition() {
        return position;
    }

    public double getDistance(double currentLat, double currentLong) {
        return getPosition().distanceToAsDouble(new GeoPoint(currentLat, currentLong)) / 1000;
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

    public long getDate() {
        return date;
    }

    public User getDestinatedTo(){ return destinatedTo; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeString(address);
        dest.writeInt(id);
        dest.writeInt(image);
        dest.writeInt(delivered ? 1 : 0);
        dest.writeLong(date);
    }

    public String fullDesc(){
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        String res = (this.getDelivered() ? getDescription()+"\n"
                                            +"est arrivé au "+this.address+"\n"
                                            +"le "+d.format(this.getDate())
                                            :
                                            getDescription()+"\n"
                                            +"arrivera au "+this.address+"\n"
                                            +"le "+d.format(this.getDate())
        );
        return res;
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
