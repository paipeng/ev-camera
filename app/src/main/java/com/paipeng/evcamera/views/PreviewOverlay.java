package com.paipeng.evcamera.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
public class PreviewOverlay extends View {
    private static final String TAG = PreviewOverlay.class.getSimpleName();

    private Paint paint;
    private Size screenSize;

    public PreviewOverlay(Context context) {
        super(context);
    }

    public PreviewOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreviewOverlay(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PreviewOverlay(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        paint.setAlpha(0);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setAntiAlias(true);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        //canvas.drawCircle(300, 300, 40, paint);


        //paint.setStrokeWidth(0);
        //paint.setColor(Color.CYAN);
        int offset = (screenSize.getHeight()-screenSize.getWidth())/2;
        canvas.drawRect(0, 0, screenSize.getWidth(), 200, paint);
        canvas.drawRect(0, offset+screenSize.getWidth(), screenSize.getWidth(), screenSize.getHeight(), paint);

        //paint.setColor(Color.YELLOW);
        //canvas.drawRect(33, 33, 77, 60, paint);
    }
}
