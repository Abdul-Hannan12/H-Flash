package com.rockykhan.hflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;
    ImageView torchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        torchImage = findViewById(R.id.torchImage);

        boolean hasFlashLight = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (hasFlashLight){

                    if (button.getText().toString().equals("TURN ON")){
                        button.setText("TURN OFF");
                        torchImage.setImageResource(R.drawable.torch_on);
                        flashlight(true);
                    }else if (button.getText().toString().equals("TURN OFF")){
                        button.setText("TURN ON");
                        torchImage.setImageResource(R.drawable.torch_off);
                        flashlight(false);
                    }

                }else{
                    Toast.makeText(MainActivity.this, "No Flashlight detected", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void flashlight(boolean on) {

        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, on);
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }
}
