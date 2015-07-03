package com.paipeng.evcamera.view;

/**
 * Created by paipeng on 02/07/15.
 */


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.View;

/**
 * Created by paipeng on 22/05/15.
 */
public class CameraPreviewOverlay extends View {
    private static final String TAG = CameraPreviewOverlay.class.getSimpleName();
    private Paint paint;
    private Size screenSize;

    public CameraPreviewOverlay(Context context) {
        super(context);
    }

    public CameraPreviewOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraPreviewOverlay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CameraPreviewOverlay(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setScreenSize(Size screenSize) {
        Log.d(TAG, "setScreenSize " + screenSize.getWidth() + "-" + screenSize.getHeight());
        this.screenSize = screenSize;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (screenSize == null) {
            return;
        }
        if (paint == null) {
            paint = new Paint();
        }
        /*
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint);
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint);
        */

        paint.setAlpha(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setAntiAlias(true);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //canvas.drawCircle(300, 300, 40, paint);


        //paint.setStrokeWidth(0);
        //paint.setColor(Color.CYAN);
        float offset = (screenSize.getHeight()-screenSize.getWidth())/2.0f;
        Log.i(TAG, "offset " + offset);

        canvas.drawRect(0, 0, screenSize.getWidth(), offset, paint);



        canvas.drawRect(0, (screenSize.getWidth() + offset), screenSize.getWidth(), screenSize.getHeight(), paint);

        //paint.setColor(Color.YELLOW);
        //canvas.drawRect(33, 33, 77, 60, paint);

    }
}
