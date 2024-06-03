package edu.ihm.td1.livraison;

import static android.widget.Toast.makeText;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReportCommandActivity extends AppCompatActivity implements IPictureActivity, ISavePictureActivity {

    private Bitmap picture;
    private PictureFragment picturefragment;
    private SavePictureFragment savepicturefragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_command);

        picturefragment = (PictureFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPicture);
        assert picturefragment != null;
        picturefragment.setOnPictureChange(picture -> {
            this.picture = picture;
            if (picture == null) savepicturefragment.setDisableButtonSave();
            else savepicturefragment.setEnableButtonSave();
        });

        savepicturefragment = (SavePictureFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSavePicture);
        assert savepicturefragment != null;
        savepicturefragment.setActivity(this);

        Order order = getIntent().getParcelableExtra("order");
        ObjectDisplayFragment objectFrag = (ObjectDisplayFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentObjectDisplay);
        assert objectFrag != null;
        Bundle bundle = new Bundle();
        bundle.putParcelable(ObjectDisplayFragment.ARG_ITEM, order);
        objectFrag.setArguments(bundle);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = makeText(getApplicationContext(), " CAMERA authorisation granted", Toast.LENGTH_LONG);
                    toast.show();
                    picturefragment.takePicture();
                } else {
                    Toast toast = makeText(getApplicationContext(), " CAMERA authorisation NOT granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
            case REQUEST_MEDIA_WRITE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savepicturefragment.saveToInternalStorage(picture);
                    Toast toast = makeText(getApplicationContext(), " Write permission granted", Toast.LENGTH_LONG);
                    toast.show();

                } else {
                    Toast toast = makeText(getApplicationContext(), " Write permission NOT granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
            case REQUEST_MEDIA_READ: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = makeText(getApplicationContext(), " Read permission granted", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Toast toast = makeText(getApplicationContext(), " Read permission NOT granted", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            break;
        }
    }

    @Override
    public void onPictureLoad(Bitmap bitmap) {
        picturefragment.setImage(bitmap);
    }

    @Override
    public Bitmap getPictureToSave() {
        return picture;
    }
}
