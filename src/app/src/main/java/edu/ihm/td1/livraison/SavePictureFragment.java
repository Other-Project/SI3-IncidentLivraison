package edu.ihm.td1.livraison;

import android.content.ContentValues;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import android.Manifest;


public class SavePictureFragment extends Fragment {
    private ISavePictureActivity activity;
    private Button buttonSave;
    //private Button buttonLoad;

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    private String pictureName;
    private String directoryName;

    public SavePictureFragment() {

    }

    public SavePictureFragment(ISavePictureActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_save_picture, container, false);
        pictureName = "test.jpg";
        ContextWrapper contextWrapper = new ContextWrapper(getContext());
        directoryName = contextWrapper.getDir("imagedir", ContextWrapper.MODE_PRIVATE).getPath(); // : /data/user


        buttonSave = rootView.findViewById(R.id.button_save);
        setDisableButtonSave();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap picture = activity.getPictureToSave();
                if(picture != null){
                    if(ContextCompat.checkSelfPermission( getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},  // selon le tuto
                                ISavePictureActivity.REQUEST_MEDIA_WRITE);
                    }
                    else{ //permission is still granted
                        saveToInternalStorage(picture);
                        setDisableButtonSave();
                    }
                }
            }
        });
        /*
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(ContextCompat.checkSelfPermission( getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                            ISavePictureActivity.REQUEST_MEDIA_READ);
                }
                else{ //permission is still granted
                    activity.onPictureLoad( loadImageFromStorage() );
                }
            }
        });
         */
        return rootView;

    }

    public Bitmap loadImageFromStorage(){
        try{
            File file = new File(directoryName, pictureName);
            Toast.makeText(getContext(), "Picture load", Toast.LENGTH_LONG).show();
            return BitmapFactory.decodeStream( new FileInputStream(file));
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    public void saveToInternalStorage(Bitmap picture){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, pictureName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, directoryName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        File file = new File(directoryName, pictureName);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            picture.compress(Bitmap.CompressFormat.PNG, 90, fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void setDisableButtonSave() {
        buttonSave.setEnabled(false);
    }


    public void setEnableButtonSave() {
        buttonSave.setEnabled(true);
    }

}
