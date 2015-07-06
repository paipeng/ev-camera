package com.paipeng.evcamera.helper;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;

/**
 * Created by paipeng on 06.07.15.
 */
public interface CameraHelperInterface {
    public void onSurfaceTextureSizeChanged(int width, int height);
    public void handleMessage(Message message);
    public void addPictureToGallery(Uri fileUrl);
}
