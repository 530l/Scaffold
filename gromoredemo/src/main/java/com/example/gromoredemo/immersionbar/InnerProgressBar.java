package com.example.gromoredemo.immersionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class InnerProgressBar extends ProgressBar {
    private Paint mPaint;
    private Rect mRect;

    public InnerProgressBar (Context context) {
        super (context);
        init ();
    }

    public InnerProgressBar (Context context, AttributeSet attrs) {
        super (context, attrs);
        init ();
    }

    private void init () {
        mPaint = new Paint (Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor (Color.WHITE);
        mPaint.setTextAlign (Paint.Align.CENTER);
        mPaint.setTextSize (35);
        mRect = new Rect ();
    }

    @Override
    protected synchronized void onDraw (Canvas canvas) {
        super.onDraw (canvas);
//        String text = String.valueOf (getProgress ()) + "%";
//        mPaint.getTextBounds (text, 0, text.length (), mRect);
//        canvas.drawText (text, getWidth () / 2, getHeight () / 2 + mRect.height () / 2, mPaint);
    }
}