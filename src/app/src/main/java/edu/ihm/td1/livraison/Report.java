package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Report implements Parcelable {
    public static List<Report> REPORTS = new ArrayList<>(List.of(
            new Report(Order.ORDERS.get(0),"Trop petit",R.drawable.sac_report6,false ,1),
            new Report(Order.ORDERS.get(1),"Il ne parle pas, déçu de la marchandise",R.drawable.sac_report1,false ,2),
            new Report(Order.ORDERS.get(2),"Pas assez rose",R.drawable.sac_report2,false ,3),
            new Report(Order.ORDERS.get(3),"La fermeture ne marche pas",R.drawable.sac_report3,false ,4),
            new Report(Order.ORDERS.get(4), "Il fait 2cm de haut", R.drawable.sac_report4, false, 5),
            new Report(Order.ORDERS.get(5),"Il est troué",R.drawable.sac_report5,true,6),
            new Report(Order.ORDERS.get(6), "Il ne chante pas 'Sac-à-dos Sac-à-dos'", R.drawable.sac_report6, true, 7),
            new Report(Order.ORDERS.get(7),"Une bretelle est déchirée",R.drawable.sac_report1,true,8),
            new Report(Order.ORDERS.get(8),"Il manque la carte",R.drawable.sac_report2,true,9)
    ));
    Order order;
    String descriptionProbleme;
    int imgProblem;
    Boolean isTreated;
    int id ;
    public Report(Order order, String desc,int img, Boolean treadted, int id){
        this.order = order;
        this.descriptionProbleme = desc;
        this.imgProblem = img;
        this.isTreated = treadted;
        this.id = id;
    }

    protected Report(Parcel in) {
        order = in.readParcelable(Order.class.getClassLoader());
        descriptionProbleme = in.readString();
        imgProblem = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(order, flags);
        dest.writeString(descriptionProbleme);
        dest.writeInt(imgProblem);
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

    public int getImgProblem(){
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
