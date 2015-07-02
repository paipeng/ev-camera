package com.paipeng.evcamera;

import android.app.Activity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;

import com.paipeng.evcamera.helper.CameraHelper;


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

        cameraHelper = new CameraHelper(this, mTextureView);

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        cameraHelper.stopCamera();
    }
}
