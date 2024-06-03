package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.osmdroid.util.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Random;

import edu.ihm.td1.livraison.userFactory.Client;
import edu.ihm.td1.livraison.userFactory.ClientFactory;

/**
 * This is the Model
 * View : {@link DeliveryActivity}
 * ViewModel : {@link DeliveryViewModel}
 */
public class Order implements Parcelable {

    private final int id;
    private final String description;
    private final int image;
    private boolean delivered;
    private boolean mustBeDisplayed = true;
    private String address;
    private long date;
    private GeoPoint position;

    private final Client destinatedTo;

    public Order(int id, String desc, int img, boolean del, String a, long d, int clientId) {
        this.id = id;
        description = desc;
        image = img;
        delivered = del;
        this.address = a;
        this.date = d;
        this.destinatedTo = new ClientFactory().build();
        position = createPosition();
    }

    protected Order(Parcel in) {
        description = in.readString();
        address = in.readString();
        id = in.readInt();
        image = in.readInt();
        delivered = in.readInt() == 1;
        date = in.readLong();
        this.destinatedTo = in.readParcelable(Client.class.getClassLoader());
        position = createPosition();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public Client getDestinatedTo(){ return destinatedTo; }

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
        dest.writeParcelable(getDestinatedTo(), flags);
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

    @NonNull
    @Override
    public String toString() {
        return "(#" + id + ") " + description + " ; " + address;
    }
}
