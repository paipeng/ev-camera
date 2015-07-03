package com.paipeng.evcamera.imageprocess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.Image;
import android.opengl.Matrix;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by paipeng on 03.07.15.
 */
public class ImageProcess {
    private static final String TAG = ImageProcess.class.getSimpleName();
    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

    public static Bitmap convertImageToBitmap(Image image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];

        InputStream inputStream = new ByteArrayInputStream(bytes);
        BitmapFactory.Options o = new BitmapFactory.Options();

        return  BitmapFactory.decodeStream(inputStream, null, o);
    }

    public static Bitmap convertImageToBitmap2(Image image) {
        Image.Plane[] planes = image.getPlanes();
        ByteBuffer buffer = planes[0].getBuffer();
        //buffer.rewind();
        byte[] data = new byte[buffer.capacity()];
        buffer.get(data);
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        if (bitmap==null) {
            Log.e(TAG, "bitmap is null");
        } else {
            Log.i(TAG, "bitmap valid!!");
        }
        return  bitmap;
    }


    public static byte[] convertBitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageBytes = stream.toByteArray();
        return imageBytes;
    }

    public static Bitmap cropSquareBitmap(Bitmap bitmap) {
        Log.i(TAG, "cropSquareBitmap " + bitmap.getWidth() + " - " + bitmap.getHeight());
        int length = (bitmap.getHeight() > bitmap.getWidth() )? bitmap.getWidth():bitmap.getHeight();
        Log.i(TAG, "length " + length);
        int offset_x = 0;
        int offset_y = 0;

        if (length == bitmap.getWidth()) {
            offset_y = ( bitmap.getHeight() - bitmap.getWidth());
        } else {
            offset_x = (bitmap.getWidth() - bitmap.getHeight());
        }

        Log.i(TAG, "offset " + offset_x + " - " + offset_y);

        return Bitmap.createBitmap(bitmap, offset_x, offset_y, length, length);
    }

    public static byte[] doFilter(Image image) {
        Bitmap bitmap = convertImageToBitmap2(image);
        //Bitmap filtedBitmap = changeBitmapContrastBrightness(bitmap, 2.0f, 1.0f);

        Bitmap croppedBitmap = cropSquareBitmap(bitmap);
        return convertBitmapToBytes(croppedBitmap);
    }
}
