package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Report implements Parcelable {
    public static final List<Report> REPORTS = new ArrayList<>(List.of(
            new Report(Order.ORDERS.get(0),"Trop petit","" ),
            new Report(Order.ORDERS.get(1),"Il ne parle pas, déçu de la marchandise","" ),
            new Report(Order.ORDERS.get(2),"Pas assez rose","" ),
            new Report(Order.ORDERS.get(3),"La fermeture ne marche pas","" ),
            new Report(Order.ORDERS.get(4),"Il fait 2cm de hauteur","" ),
            new Report(Order.ORDERS.get(5),"Il est troué","" ),
            new Report(Order.ORDERS.get(6),"Il ne chante pas 'Sac-à-dos Sac-à-dos' ","" ),
            new Report(Order.ORDERS.get(7),"Une bretelle est déchirée","" ),
            new Report(Order.ORDERS.get(8),"Il manque la carte","" )
    ));
    Order order;
    String descriptionProbleme;
    String srcImage;

    public Report(Order order, String desc, String src){
        this.order = order;
        this.descriptionProbleme = desc;
        this.srcImage = src;
    }

    protected Report(Parcel in) {
        order = in.readParcelable(Order.class.getClassLoader());
        descriptionProbleme = in.readString();
        srcImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(order, flags);
        dest.writeString(descriptionProbleme);
        dest.writeString(srcImage);
    }

    public static final Creator<Report> CREATOR = new Creator<Report>() {
        @Override
        public Report createFromParcel(Parcel in) {
            return new Report(in);
        }

        @Override
        public Report[] newArray(int size) {
            return new Report[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

}
