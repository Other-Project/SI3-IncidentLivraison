package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Report implements Parcelable {
    public static List<Report> REPORTS = new ArrayList<>(List.of(
            new Report(OrderMap.getOrder(0), "Trop petit", "", false, 1),
            new Report(OrderMap.getOrder(1), "Il ne parle pas, déçu de la marchandise", "", false, 2),
            new Report(OrderMap.getOrder(2), "Pas assez rose", "", false, 3),
            new Report(OrderMap.getOrder(3), "La fermeture ne marche pas", "", false, 4),
            new Report(OrderMap.getOrder(4), "Il fait 2cm de haut", "", false, 5),
            new Report(OrderMap.getOrder(5), "Il est troué", "", true, 6),
            new Report(OrderMap.getOrder(6), "Il ne chante pas 'Sac-à-dos Sac-à-dos'", "", true, 7),
            new Report(OrderMap.getOrder(7), "Une bretelle est déchirée", "", true, 8),
            new Report(OrderMap.getOrder(8), "Il manque la carte", "", true, 9)
    ));
    Order order;
    String descriptionProbleme;
    String imgProblem;
    Boolean isTreated;
    int id ;
    public Report(Order order, String desc,String img, Boolean treadted, int id){
        this.order = order;
        this.descriptionProbleme = desc;
        this.imgProblem = img;
        this.isTreated = treadted;
        this.id = id;
    }

    protected Report(Parcel in) {
        order = in.readParcelable(Order.class.getClassLoader());
        descriptionProbleme = in.readString();
        imgProblem = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(order, flags);
        dest.writeString(descriptionProbleme);
        dest.writeString(imgProblem);
        dest.writeInt(id);
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

    @NonNull
    @Override
    public String toString() {
        return "Report : "+descriptionProbleme+","+imgProblem+","+order;
    }

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

    public static void setIsTreated(Report report, boolean b) {
        boolean found = false;
        for(Report r : REPORTS){
            if(r.id == report.getId()){
                r.isTreated=b;
                found = true;
                Log.d("report : ", r.toString());
            }
        }
        if(!found) {
            Log.d("not find", report.toString());
        }
    }

    private int getId() {
        return id;
    }

}
