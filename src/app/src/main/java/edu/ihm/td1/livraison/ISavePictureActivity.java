package edu.ihm.td1.livraison;

import android.graphics.Bitmap;

public interface ISavePictureActivity {
    int REQUEST_MEDIA_READ = 1000;
    int REQUEST_MEDIA_WRITE = 1001;
    void onPictureLoad(Bitmap bimap);
    Bitmap getPictureToSave();

}
