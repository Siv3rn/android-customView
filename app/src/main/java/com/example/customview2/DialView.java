package com.example.customview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DialView extends View {

    private static int SELECTION_COUNT = 4;
    private float mWidht;
    private float mHeight;
    private Paint mTextPaint;
    private Paint mDialpaint;
    private float mRadius;
    private int mActiveSelection;
    private final StringBuffer mTempLabel = new StringBuffer(8);
    private final float[] mTempResult = new float[2];


    private void init(){
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(40f);

        mDialpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDialpaint.setColor(Color.GRAY);
        mActiveSelection = 0;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT;
                if (mActiveSelection >=1){
                    mDialpaint.setColor(Color.GREEN);
                }
                else {
                    mDialpaint.setColor(Color.GRAY);
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidht = w;
        mHeight = h;
        mRadius = (float) (Math.min(mWidht,mHeight/2*0.8));


    }
    private float [] computeXY(final int pos, final float radius){
        float[] result = mTempResult;
        double startAngle = Math.PI * (9/8d);
        double angle = startAngle +(pos* (Math.PI/4));
        result[0] = (float) (radius * Math.cos(angle) + (mWidht/2));
        result[1] = (float) (radius * Math.sin(angle) + (mHeight/2));
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidht/2, mHeight/2, mRadius, mDialpaint);


        //draw text
        final float labelRadius = mRadius + 20;
        StringBuffer label =mTempLabel;

        for(int i = 0; i < SELECTION_COUNT;i++){
            float[] xyData = computeXY(i,labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label,0,label.length(),x ,y,mTextPaint);
        }

        //draw mark circle
        final float markerRadius = mRadius -35;
        float[] xyData = computeXY(mActiveSelection,markerRadius);
        float x = xyData[0];
        float y = xyData[1];

        canvas.drawCircle(x,y,20, mTextPaint);


    }

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
