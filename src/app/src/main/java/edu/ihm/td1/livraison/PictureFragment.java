package edu.ihm.td1.livraison;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.Manifest;

import java.util.Objects;

public class PictureFragment extends Fragment {
    public PictureFragment(){}
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate( R.layout.fragment_picture, container, false);

        imageView = rootView.findViewById(R.id.phototaken);

        rootView.findViewById( R.id.buttonImage).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.CAMERA}, IPictureActivity.REQUEST_CAMERA );
                }
                else{
                    takePicture();
                }
            }
        });

        rootView.findViewById( R.id.phototaken).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(ContextCompat.checkSelfPermission( getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions( getActivity(), new String[] {Manifest.permission.CAMERA}, IPictureActivity.REQUEST_CAMERA );
                }
                else{
                    takePicture();
                }
            }
        });

        return rootView;

    }

    public void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult( intent, IPictureActivity.REQUEST_CAMERA);
    }

    public void setImage(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

}
