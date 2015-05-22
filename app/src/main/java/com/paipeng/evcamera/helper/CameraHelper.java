package com.paipeng.evcamera.helper;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;

import com.paipeng.evcamera.views.AutoFitTextureView;
import com.paipeng.evcamera.views.AutoFitTextureViewInterface;

import java.util.Arrays;

/**
 * Created by paipeng on 22/05/15.
 */
public class CameraHelper implements AutoFitTextureViewInterface {
    private static final String TAG = CameraHelper.class.getSimpleName();

    private AutoFitTextureView textureView = null;

    private Size mPreviewSize = null;
    private CameraDevice mCameraDevice = null;
    private CaptureRequest.Builder mPreviewBuilder = null;
    private CameraCaptureSession mPreviewSession = null;
    private Context context;

    public void pause() {
        if (mCameraDevice != null)
        {
            mCameraDevice.close();
            mCameraDevice = null;
        }
    }

    public CameraHelper(Context context, AutoFitTextureView textureView) {
        this.context = context;
        this.textureView = textureView;
        textureView.setAutoFitTextureViewInterface(this);
    }


    private CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {

        @Override
        public void onOpened(CameraDevice camera) {
            // TODO Auto-generated method stub
            Log.i(TAG, "onOpened");
            mCameraDevice = camera;

            //mTextureView.setRotation(-90.0f);
            SurfaceTexture texture = textureView.getSurfaceTexture();
            if (texture == null) {
                Log.e(TAG, "texture is null");
                return;
            }

            int s = mPreviewSize.getWidth() >= mPreviewSize.getHeight() ? mPreviewSize.getHeight(): mPreviewSize.getWidth();


            texture.setDefaultBufferSize(mPreviewSize.getHeight(), mPreviewSize.getWidth());

            Surface surface = new Surface(texture);

            try {
                mPreviewBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
            } catch (CameraAccessException e){
                e.printStackTrace();
            }

            mPreviewBuilder.addTarget(surface);

            try {
                mCameraDevice.createCaptureSession(Arrays.asList(surface), mPreviewStateCallback, null);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onError");

        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            // TODO Auto-generated method stub
            Log.e(TAG, "onDisconnected");

        }
    };
    private CameraCaptureSession.StateCallback mPreviewStateCallback = new CameraCaptureSession.StateCallback() {

        @Override
        public void onConfigured(CameraCaptureSession session) {
            // TODO Auto-generated method stub
            Log.i(TAG, "onConfigured");
            mPreviewSession = session;

            mPreviewBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);

            HandlerThread backgroundThread = new HandlerThread("CameraPreview");
            backgroundThread.start();
            Handler backgroundHandler = new Handler(backgroundThread.getLooper());

            try {
                mPreviewSession.setRepeatingRequest(mPreviewBuilder.build(), null, backgroundHandler);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onConfigureFailed(CameraCaptureSession session) {
            // TODO Auto-generated method stub
            Log.e(TAG, "CameraCaptureSession Configure failed");
        }
    };

    public CameraDevice.StateCallback getmStateCallback() {
        return mStateCallback;
    }

    public CameraCaptureSession.StateCallback getmPreviewStateCallback() {
        return mPreviewStateCallback;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.i(TAG, "onSurfaceTextureAvailable()" + width + "-" + height);

        CameraManager manager = (CameraManager) context.getSystemService(context.CAMERA_SERVICE);
        try{
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            Size[] sizes = map.getOutputSizes(SurfaceTexture.class);
            for (Size size : sizes) {
                Log.d(TAG, "size " + size.getWidth() + " - " + size.getHeight());
            }
            mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
            //updateTextureMatrix(width, height);
            //adjustAspectRatio(width, height);

            manager.openCamera(cameraId, mStateCallback, null);
        }
        catch(CameraAccessException e)
        {
            e.printStackTrace();
        }

    }
}
