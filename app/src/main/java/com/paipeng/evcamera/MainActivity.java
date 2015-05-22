package com.paipeng.evcamera;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.paipeng.evcamera.helper.CameraHelper;
import com.paipeng.evcamera.views.AutoFitTextureView;

import java.util.Arrays;


public class MainActivity extends Activity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private CameraHelper cameraHelper;
    private AutoFitTextureView textureView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //same as set-up android:screenOrientation="portrait" in <activity>, AndroidManifest.xml
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        textureView = (AutoFitTextureView) findViewById(R.id.cameraPreviewTextureView);
        cameraHelper = new CameraHelper(this, textureView);
        textureView.setSurfaceTextureListener(textureView.getSurfaceTextureListener());
        //mTextureView.setRotation(-90.0f);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        cameraHelper.pause();;
    }
/*
    private void updateTextureMatrix(int width, int height)
    {
        boolean isPortrait = false;

        Display display = getWindowManager().getDefaultDisplay();
        if (display.getRotation() == Surface.ROTATION_0 || display.getRotation() == Surface.ROTATION_180) isPortrait = true;
        else if (display.getRotation() == Surface.ROTATION_90 || display.getRotation() == Surface.ROTATION_270) isPortrait = false;


        Size oldPreviewSize = mPreviewSize;

        if (isPortrait)
        {
            mPreviewSize = new Size(oldPreviewSize.getHeight(), oldPreviewSize.getWidth());
        }

        float ratioSurface = (float) width / height;
        float ratioPreview = (float) oldPreviewSize.getWidth() / oldPreviewSize.getHeight();

        float scaleX;
        float scaleY;

        if (ratioSurface > ratioPreview)
        {
            scaleX = (float) height / oldPreviewSize.getHeight();
            scaleY = 1;
        }
        else
        {
            scaleX = 1;
            scaleY = (float) width / oldPreviewSize.getWidth();
        }

        Matrix matrix = new Matrix();

        matrix.setScale(scaleX, scaleY);
        mTextureView.setTransform(matrix);

        float scaledWidth = width * scaleX;
        float scaledHeight = height * scaleY;

        float dx = (width - scaledWidth) / 2;
        float dy = (height - scaledHeight) / 2;
        mTextureView.setTranslationX(dx);
        mTextureView.setTranslationY(dy);
    }

    private void adjustAspectRatio(int videoWidth, int videoHeight) {
        int viewWidth = videoWidth;
        int viewHeight = videoHeight;
        double aspectRatio = (double) videoHeight / videoWidth;

        int newWidth, newHeight;
        if (viewHeight > (int) (viewWidth * aspectRatio)) {
            // limited by narrow width; restrict height
            newWidth = viewWidth;
            newHeight = (int) (viewWidth * aspectRatio);
        } else {
            // limited by short height; restrict width
            newWidth = (int) (viewHeight / aspectRatio);
            newHeight = viewHeight;
        }
        int xoff = (viewWidth - newWidth) / 2;
        int yoff = (viewHeight - newHeight) / 2;
        Log.v(TAG, "video=" + videoWidth + "x" + videoHeight +
                " view=" + viewWidth + "x" + viewHeight +
                " newView=" + newWidth + "x" + newHeight +
                " off=" + xoff + "," + yoff);

        Matrix txform = new Matrix();
        mTextureView.getTransform(txform);
        //txform.setScale(2, (float) newHeight / viewHeight);
        //txform.setRotate(45f, 10, 10);

        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        //lp1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        //mTextureView.setPaddingRelative(0, 0, 0, 0);
        mTextureView.setLayoutParams(
                new FrameLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        //txform.postRotate(10);          // just for fun
        txform.postTranslate(xoff, yoff);
        mTextureView.setPadding(0, 0, 0, 0);
        mTextureView.setLayoutParams(new FrameLayout.LayoutParams(viewWidth, viewHeight));

        mTextureView.setAlpha(0.4f);
        //mTextureView.setTransform(txform);
    }
    */
}
