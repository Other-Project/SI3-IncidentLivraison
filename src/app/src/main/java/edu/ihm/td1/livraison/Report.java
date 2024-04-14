package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Report implements Parcelable {
    public static final List<Report> REPORTS = new ArrayList<>(List.of(
            new Report(Order.ORDERS.get(0),"Trop petit","",false ),
            new Report(Order.ORDERS.get(1),"Il ne parle pas, déçu de la marchandise","",false ),
            new Report(Order.ORDERS.get(2),"Pas assez rose","",false ),
            new Report(Order.ORDERS.get(3),"La fermeture ne marche pas","",false ),
            new Report(Order.ORDERS.get(4),"Il fait 2cm de hauteur","",false),
            new Report(Order.ORDERS.get(5),"Il est troué","",true),
            new Report(Order.ORDERS.get(6),"Il ne chante pas 'Sac-à-dos Sac-à-dos' ","",true ),
            new Report(Order.ORDERS.get(7),"Une bretelle est déchirée","",true),
            new Report(Order.ORDERS.get(8),"Il manque la carte","",true)
    ));
    Order order;
    String descriptionProbleme;
    String imgProblem;
    Boolean isTreated;
    public Report(Order order, String desc,String img, Boolean treadted){
        this.order = order;
        this.descriptionProbleme = desc;
        this.imgProblem = img;
        this.isTreated = treadted;
    }

    protected Report(Parcel in) {
        order = in.readParcelable(Order.class.getClassLoader());
        descriptionProbleme = in.readString();
        imgProblem = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(order, flags);
        dest.writeString(descriptionProbleme);
        dest.writeString(imgProblem);
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

    public Boolean isTreated() {
        return isTreated;
    }

    public String getDescriptionProbleme() {
        return descriptionProbleme;
    }

    public Order getOrder() {
        return order;
    }

    public String getImgProblem(){
        return imgProblem;
    }
}
