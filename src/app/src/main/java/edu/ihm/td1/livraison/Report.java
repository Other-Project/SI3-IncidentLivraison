package edu.ihm.td1.livraison;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Report implements Parcelable {
    public static Map<Integer, Report> REPORTS = new HashMap<>(Stream.of(
            new Report(0, "Trop petit", R.drawable.sac_report5, false, 1),
            new Report(1, "Il ne parle pas, déçu de la marchandise", R.drawable.sac_report1, false, 2),
            new Report(2, "Pas assez rose", R.drawable.sac_report2, false, 3),
            new Report(3, "La fermeture ne marche pas", R.drawable.sac_report3, false, 4),
            new Report(4, "Il fait 2cm de haut", R.drawable.sac_report4, false, 5),
            new Report(5, "Il est troué", R.drawable.sac_report5, true, 6),
            new Report(6, "Il ne chante pas 'Sac-à-dos Sac-à-dos'", R.drawable.sac_report6, true, 7),
            new Report(7, "Une bretelle est déchirée", R.drawable.sac_report1, true, 8),
            new Report(8, "Il manque la carte", R.drawable.sac_report2, true, 9)
    ).collect(Collectors.toMap(Report::getId, r -> r)));
    private final int order;
    private final String descriptionProbleme;
    private final int imgProblem;
    private Boolean isTreated;
    private final int id;

    public Report(int order, String desc, int img, Boolean treadted, int id) {
        this.order = order;
        this.descriptionProbleme = desc;
        this.imgProblem = img;
        this.isTreated = treadted;
        this.id = id;
    }

    protected Report(Parcel in) {
        order = in.readInt();
        descriptionProbleme = in.readString();
        imgProblem = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(order);
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
        return "Report : " + descriptionProbleme + "," + imgProblem + "," + order;
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
        return OrderMap.getOrder(order);
    }

    public int getImgProblem() {
        return imgProblem;
    }

    public static void setIsTreated(int report, boolean b) {
        Report r = REPORTS.get(report);
        if (r == null) {
            Log.d("not found", String.valueOf(report));
            return;
        }
        r.isTreated = b;

    }

    public int getId() {
        return id;
    }

}
