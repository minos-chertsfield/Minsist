package com.example.pc.connect_2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.Calendar;

/**
 * Created by asus on 2017/12/2.
 */
public class StepHistory extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;

    Calendar c = Calendar.getInstance();
    Calendar c0 = Calendar.getInstance();
    //private
    private int[] mSteps;
    private float temp;
    private int mMaxStep;
    private Paint mBarPaint;//竖条的画笔

    //获取屏幕宽高
    WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);

    float widthwm = wm.getDefaultDisplay().getWidth();
    float heightwm = wm.getDefaultDisplay().getHeight();
    private int mTotalSteps;

    public StepHistory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

    }

    //初始化
    private void initView(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        //TypedArray ta = context.obtainStyledAttributes(attrs,
//                R.styleable.StepHistory);
//        if (ta != null) {
//            mCount = ta.getInt(R.styleable.StepHistory_count, 6);
//            ta.recycle();
 // }
       // mSteps = new int[]{9050, 12280, 8900, 9200, 6500, 5660, 9450};

//      calculateSteps();
    }

    public StepHistory(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StepHistory(Context context) {
        this(context, null);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //历史记录画布大致大小：宽度、高度
        mWidth = (int)(widthwm*0.88);
        mHeight=(int)(heightwm*0.4);
    }


    public void setSteps(int[] steps) {
        if (steps == null || steps.length == 0) throw new IllegalArgumentException("非法参数");
        mSteps = steps;
        calculateSteps();
        invalidate();
    }
    //计算步数，总步数与最大步数
    private void calculateSteps() {
        mTotalSteps = 0;
        mMaxStep = 0;
        for (int i = 0; i < mSteps.length; i++) {
            mTotalSteps += mSteps[i];
           if (mMaxStep < mSteps[i]) mMaxStep = mSteps[i];
        }

    }

    private void drawHistory(Canvas canvas)
    {
        float startX;
        float startY;
        float stopX;
        float stopY;
        //获取当前日期
        int day;
        mBarPaint = new Paint();
        mBarPaint.setAntiAlias(true);
        mBarPaint.setStrokeCap(Paint.Cap.ROUND);
        mBarPaint.setStrokeWidth(20f);
        mBarPaint.setColor(Color.argb(255,115,175,237));

        Paint mTextPaint = new Paint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(0.03f * mWidth);

        startY = (int)heightwm *0.75f;

        mSteps=CountActivity.getCountActivity().gethistory();
        calculateSteps();

        int distance;
        //绘制历史记录竖条
        for (int i = 0; i < mSteps.length; i++) {
            //作为百分比比值对历史记录进行处理，防止某一日数据过于突出导致绘图超出范围
            temp=mSteps[i]*1.f/mMaxStep;


            distance=6-i;
            c0.set(Calendar.DATE,c.get(Calendar.DATE));
            c0.set(Calendar.DATE, c0.get(Calendar.DATE) - distance);
            //c.add(Calendar.DAY_OF_MONTH,-i); // -1 前一天
            day=c0.get(Calendar.DAY_OF_MONTH);
           // 绘制线时所需数据
            float barHeight = temp * mHeight*0.5f;
            startX = widthwm / 2 - mWidth*0.4f+i *mWidth*0.13f;
            stopX = startX;
            stopY = startY - barHeight;

           // 绘制线
            canvas.drawLine(startX, startY, stopX, stopY, mBarPaint);
          //  绘制数据
            canvas.drawText(String.valueOf(mSteps[i]), startX, startY + 0.03f * heightwm, mTextPaint);
          //  绘制日期
           canvas.drawText(day + "日", startX, startY + 0.05f * heightwm, mTextPaint);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHistory(canvas);
    }
}