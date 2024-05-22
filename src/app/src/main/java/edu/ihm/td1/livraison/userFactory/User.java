package edu.ihm.td1.livraison.userFactory;

import android.os.Parcelable;

public interface User extends Parcelable {
    String getPrenom();
    String getNom();
    String getNumeroTelephone();
    String getAdresse();

}
