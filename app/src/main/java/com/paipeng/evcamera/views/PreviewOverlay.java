package com.paipeng.evcamera.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by paipeng on 22/05/15.
 */
public class PreviewOverlay extends View {
    Paint paint;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
        }
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint);
    }
}
