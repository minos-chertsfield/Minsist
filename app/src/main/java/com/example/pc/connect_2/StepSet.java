package com.example.pc.connect_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by asus on 2017/12/2.
 */



public class StepSet extends View implements View.OnClickListener{


    private SharedPreferencesUtils sp;
    private OnClickListener mListener;
 //   private onViewClick mViewClick;
    //获取屏幕宽高
    WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);

    float widthwm = wm.getDefaultDisplay().getWidth();
    float heightwm = wm.getDefaultDisplay().getHeight();

    float y = heightwm/10;
    float radius=widthwm/20*5;
    float centerX=widthwm/2;
    public StepSet(Context context) {
        super(context, null);
        init();
    }

    public StepSet(Context context, AttributeSet attrs) {
        super(context, attrs);
        //mContext = context;
        init();
    }

    private void init() {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.argb(255, 158, 205, 223));
        mPaint.setStyle(Paint.Style.STROKE);
        //  mPaint.setStrokeWidth(PaintWidth);

    }

    private void drawTextStepString(Canvas canvas) {
        Paint mPaintStep = new Paint();
        mPaintStep.setTextSize(40);
        mPaintStep.setTextAlign(Paint.Align.CENTER);
        mPaintStep.setAntiAlias(true);//抗锯齿功能
        mPaintStep.setColor(Color.BLACK);
        String stepString = "设置";
        Rect bounds = new Rect();
        mPaintStep.getTextBounds(stepString, 0, stepString.length(), bounds);
        canvas.drawText(stepString,centerX,y+2*radius-bounds.height()/2 , mPaintStep);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTextStepString(canvas);
    }

    @Override
    public void onClick(View v) {
//        AlertDialog.Builder bdr;
//        bdr = new AlertDialog.Builder();

    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//            {mListener.onClick(this);
//                invalidate();
//             //   mViewClick.onClick();
//            }
//
//        }
//        return true;
//    }
//    StepSet.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    });

}
