package edu.ihm.td1.livraison;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ReportCommandActivity extends AppCompatActivity implements IPictureActivity{

    private Bitmap picture;
    private PictureFragment picturefragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_report_command);
        picturefragment = (PictureFragment) getSupportFragmentManager().findFragmentById( R.id.fragmentPicture) ;
        if(picturefragment == null){
            picturefragment = new PictureFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace( R.id.phototaken, picturefragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast toast = makeText(getApplicationContext(), " CAMERA authorisation granted", Toast.LENGTH_LONG);
                    toast.show();
                    picturefragment.takePicture();
                } else {
                    Toast toast = makeText(getApplicationContext(), " CAMERA authorisation NOT granted", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult( requestCode , resultCode , data);
        if(requestCode == REQUEST_CAMERA){
            if (resultCode == RESULT_OK) {
                picture = (Bitmap) data.getExtras().get("data");
                picturefragment.setImage(picture);              // .setImage(picture selon Rallo
            }
            else if(resultCode ==RESULT_CANCELED){
                Toast toast = Toast.makeText(getApplicationContext(), "Picrture canceled", Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(), "Action failed", Toast.LENGTH_LONG);
                toast.show();
            }

        }

    }
}
