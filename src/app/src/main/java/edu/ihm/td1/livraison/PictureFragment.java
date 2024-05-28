package edu.ihm.td1.livraison;

import android.Manifest;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PictureFragment extends Fragment {
    private final ActivityResultLauncher<Intent> someActivityResultLauncher;

    private ImageView imageView;

    public PictureFragment() {
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) return;

                    // There are no request codes
                    Intent intent = result.getData();
                    if (intent == null) return;
                    Bundle bundle = intent.getExtras();
                    if (bundle == null) return;
                    setImage((Bitmap) bundle.get("data"));
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_picture, container, false);

        imageView = rootView.findViewById(R.id.phototaken);
        imageView.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, IPictureActivity.REQUEST_CAMERA);
            else takePicture();
        });

        return rootView;
    }

    public void takePicture() {
        someActivityResultLauncher.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

}
