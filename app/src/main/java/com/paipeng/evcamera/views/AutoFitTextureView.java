/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paipeng.evcamera.views;

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
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

import java.util.Arrays;

/**
 * A {@link TextureView} that can be adjusted to a specified aspect ratio.
 */
public class AutoFitTextureView implements TextureView.SurfaceTextureListener{
    private static final String TAG = AutoFitTextureView.class.getSimpleName();
    private TextureView textureView;

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;
    private Size mPreviewSize = null;

    private AutoFitTextureViewInterface autoFitTextureViewInterface;

    public AutoFitTextureView(TextureView textureView) {
        this.textureView = textureView;
        textureView.setSurfaceTextureListener(this);


    }

    public SurfaceTexture getSurfaceTexture() {
        return textureView.getSurfaceTexture();
    }

    public void setAutoFitTextureViewInterface(AutoFitTextureViewInterface autoFitTextureViewInterface) {
        this.autoFitTextureViewInterface = autoFitTextureViewInterface;
    }



    /**
     * Sets the aspect ratio for this view. The size of the view will be measured based on the ratio
     * calculated from the parameters. Note that the actual sizes of parameters don't matter, that
     * is, calling setAspectRatio(2, 3) and setAspectRatio(4, 6) make the same result.
     *
     * @param width  Relative horizontal size
     * @param height Relative vertical size
     */
    public void setAspectRatio(int width, int height) {
        Log.d(TAG, "setAspectRatio " + width + "-" + height);
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        //textureView.requestLayout();

        textureView.setLayoutParams(new FrameLayout.LayoutParams(width, height));


    }


    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        //Log.i(TAG, "onSurfaceTextureUpdated()");

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
                                            int height) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onSurfaceTextureSizeChanged()");
        //updateTextureMatrix(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        Log.i(TAG, "onSurfaceTextureDestroyed()");
        return false;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
                                          int height) {
        // TODO Auto-generated method stub

        if (autoFitTextureViewInterface != null) {
            autoFitTextureViewInterface.onSurfaceTextureAvailable(surface, width, height);
        }
    }
}
