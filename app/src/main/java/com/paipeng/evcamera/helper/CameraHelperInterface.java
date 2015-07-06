package com.paipeng.evcamera.helper;

import android.os.Message;

/**
 * Created by paipeng on 06.07.15.
 */
public interface CameraHelperInterface {
    public void onSurfaceTextureSizeChanged(int width, int height);
    public void handleMessage(Message message);
}
