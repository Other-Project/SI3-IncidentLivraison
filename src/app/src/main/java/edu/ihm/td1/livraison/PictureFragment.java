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
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.function.Consumer;

public class PictureFragment extends Fragment {
    private final ActivityResultLauncher<Intent> cameraActivityResult;

    private ImageView imageView;
    private Button addPicture;
    private Button retakePicture;
    private Button removePicture;

    private Consumer<Bitmap> onPictureChange;

    public PictureFragment() {
        cameraActivityResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() != Activity.RESULT_OK) return;

                    Intent intent = result.getData();
                    if (intent == null) return;
                    Bundle bundle = intent.getExtras();
                    if (bundle == null) return;
                    setImage((Bitmap) bundle.get("data"));
                });
    }

    public void setOnPictureChange(Consumer<Bitmap> onPictureChange) {
        this.onPictureChange = onPictureChange;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_picture, container, false);

        imageView = rootView.findViewById(R.id.image_preview);
        addPicture = rootView.findViewById(R.id.add_picture);
        retakePicture = rootView.findViewById(R.id.retake_picture);
        removePicture = rootView.findViewById(R.id.remove_picture);

        addPicture.setOnClickListener(v -> takePicture());
        retakePicture.setOnClickListener(v -> takePicture());
        removePicture.setOnClickListener(v -> setImage(null));

        setImage(null);

        return rootView;
    }

    public void takePicture() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, IPictureActivity.REQUEST_CAMERA);
        else cameraActivityResult.launch(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        addPicture.setVisibility(bitmap == null ? View.VISIBLE : View.GONE);
        retakePicture.setVisibility(bitmap == null ? View.GONE : View.VISIBLE);
        removePicture.setVisibility(bitmap == null ? View.GONE : View.VISIBLE);
        if (onPictureChange != null) onPictureChange.accept(bitmap);
    }
}
