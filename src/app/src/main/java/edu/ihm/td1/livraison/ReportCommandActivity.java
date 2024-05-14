package edu.ihm.td1.livraison;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ReportCommandActivity extends AppCompatActivity implements IPictureActivity, ISavePictureActivity {

    private Bitmap picture;
    private PictureFragment picturefragment;
    private SavePictureFragment savepicturefragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_command);

        picturefragment = (PictureFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentPicture);
        if (picturefragment == null) {
            picturefragment = new PictureFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.phototaken, picturefragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        savepicturefragment = (SavePictureFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentSavePicture);
        if (savepicturefragment == null) {
            savepicturefragment = new SavePictureFragment(this);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.phototaken, savepicturefragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
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
                    savepicturefragment.setEnableButtonSave();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                picture = (Bitmap) data.getExtras().get("data");
                picturefragment.setImage(picture);      // .setImage(picture selon Rallo
                savepicturefragment.setEnableButtonSave();
            } else if (resultCode == RESULT_CANCELED) {
                Toast toast = Toast.makeText(getApplicationContext(), "Picture canceled", Toast.LENGTH_LONG);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Action failed", Toast.LENGTH_LONG);
                toast.show();
            }

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
