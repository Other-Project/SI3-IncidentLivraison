package edu.ihm.td1.livraison;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Command implements Parcelable{

    private String description;
    private int id;
    private int image;
    private Boolean delivered;

    public Command(int id, String desc, int img, boolean del){
        this.id = id;
        description = desc;
        image = img;
        delivered = del;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return id;
    }

    public int getImage(){
        return image;
    }

    public Boolean getDelivered() {
        return delivered;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(delivered);
        }
    }
}
