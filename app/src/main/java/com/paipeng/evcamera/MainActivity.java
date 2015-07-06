package com.paipeng.evcamera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.paipeng.evcamera.helper.CameraHelper;
import com.paipeng.evcamera.helper.CameraHelperInterface;
import com.paipeng.evcamera.view.AutoFitTextureView;
import com.paipeng.evcamera.view.CameraPreviewOverlay;

public class MainActivity extends Activity implements CameraHelperInterface {

    private final static String TAG = "SimpleCamera";

    private CameraHelper cameraHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        Log.d(TAG, "onSystemUiVisibilityChange " + visibility);
                        // Note that system bars will only be "visible" if none of the
                        // LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            // TODO: The system bars are visible. Make any desired
                            // adjustments to your UI, such as showing the action bar or
                            // other navigational controls.
                            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                        } else {
                            // TODO: The system bars are NOT visible. Make any desired
                            // adjustments to your UI, such as hiding the action bar or
                            // other navigational controls.
                        }
                    }
                });
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        //same as set-up android:screenOrientation="portrait" in <activity>, AndroidManifest.xml
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        AutoFitTextureView mTextureView = (AutoFitTextureView) findViewById(R.id.cameraPreviewTextureView);

        CameraPreviewOverlay previewOverlay = (CameraPreviewOverlay) findViewById(R.id.cameraPreviewOverlay);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Size screenSize = new Size(width, height);

        cameraHelper = new CameraHelper(this, manager, mTextureView, screenSize);

        previewOverlay.setScreenSize(screenSize);

        Button captueButton = (Button) findViewById(R.id.captueButton);

        if (captueButton != null) {
            captueButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "capture");
                    //cameraHelper.setExposureCompensation();
                    //cameraHelper.takePicture();
                    cameraHelper.functionTakePicture(0);
                }
            });

        } else {
            Log.e(TAG, "captureButton invalid");
        }

        Button captueButton2 = (Button) findViewById(R.id.captueButton2);

        if (captueButton2 != null) {
            captueButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "capture2");
                    //cameraHelper.setExposureCompensation();
                    //cameraHelper.takePicture();
                    cameraHelper.functionTakePicture(1);
                }
            });

        } else {
            Log.e(TAG, "captueButton2 invalid");
        }
        Button captueButton3 = (Button) findViewById(R.id.captueButton3);

        if (captueButton3 != null) {
            captueButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "capture3");
                    cameraHelper.functionTakePicture(2);
                }
            });

        } else {
            Log.e(TAG, "captueButton3 invalid");
        }

        Button captueButton4 = (Button) findViewById(R.id.captueButton4);

        if (captueButton4 != null) {
            captueButton4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "capture4");
                    cameraHelper.functionTakePicture(3);
                }
            });

        } else {
            Log.e(TAG, "captureButton4 invalid");
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

    public void onSurfaceTextureSizeChanged(int width, int height) {
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


    }

    public void handleMessage(Message message) {
        Toast.makeText(this, (String) message.obj, Toast.LENGTH_SHORT).show();
    }
}
