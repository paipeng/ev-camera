package com.paipeng.evcamera;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.paipeng.evcamera.helper.CameraHelper;
import com.paipeng.evcamera.view.CameraPreviewOverlay;

public class MainActivity extends Activity {

    private final static String TAG = "SimpleCamera";


    private CameraHelper cameraHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //same as set-up android:screenOrientation="portrait" in <activity>, AndroidManifest.xml
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        TextureView mTextureView = (TextureView) findViewById(R.id.cameraPreviewTextureView);

        CameraPreviewOverlay previewOverlay = (CameraPreviewOverlay) findViewById(R.id.cameraPreviewOverlay);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Size screenSize = new Size(width, height);

        cameraHelper = new CameraHelper(this, mTextureView, screenSize);

        previewOverlay.setScreenSize(screenSize);

        Button captueButton = (Button) findViewById(R.id.captueButton);

        if (captueButton != null) {
            captueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "capture");
                    //cameraHelper.setExposureCompensation();
                    cameraHelper.takePicture();
                }
            });

        } else {
            Log.e(TAG, "captureButton invalid");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (cameraHelper != null) {
            cameraHelper.startBackgroundThread();
            cameraHelper.restartCamera();
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        if (cameraHelper != null) {
            cameraHelper.stopCamera();
            cameraHelper.stopBackgroundThread();
        }
    }
}
