package com.example.pc.connect_2;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

public class ViewStep extends View {

    private  int circleColor;
    private  int arcColor;
    // private Paint mPaint;
    private Context mContext;
    private int radial = 80;
    //圆弧起始角度
    private float StartAngle=135;
    //圆弧结束角度
    private float EndAngle=270;
    //当前角度，通过计算获得
    private float currentAngle;
    //画笔宽度
    private float PaintWidth=80;
    private int animationLength = 3000;
    //步数显示
    private int stepnum;
    private String stepNumber;
    private int total;
    private float percent;

    private int[] mSteps;
    //步数显示大小
    private float numberTextSize = 150;
    private ValueAnimator mAnimator;


    //获取屏幕宽高
    WindowManager wm = (WindowManager) getContext()
            .getSystemService(Context.WINDOW_SERVICE);

    float widthwm = wm.getDefaultDisplay().getWidth();
    float heightwm = wm.getDefaultDisplay().getHeight();

    float x = widthwm/10;
    float y = heightwm/10;
    float radius=widthwm*0.25f;
    //屏幕宽中心
    float centerX=widthwm/2;


    public ViewStep(Context context) {
        super(context, null);
        init();
    }

    public ViewStep(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
//        TypedArray ta = context.obtainStyledAttributes(attrs,
//                R.styleable.ViewStep);
//        if (ta != null) {
//            circleColor = ta.getColor(R.styleable.ViewStep_circleColor, 0);
//            arcColor = ta.getColor(R.styleable.ViewStep_arcColor, 0);
//            ta.recycle();
//        }
        init();

    }


    //初始化
    private void init() {
        //初始化
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.argb(255,158,205,223));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(PaintWidth);


        //初始化底部圆弧的画笔
        Paint mPaintBo = new Paint();
        mPaintBo.setAntiAlias(true);
        mPaintBo.setColor(Color.argb(180,192,192,192));
        mPaintBo.setStyle(Paint.Style.STROKE);
        mPaintBo.setStrokeWidth(PaintWidth);
        mPaintBo.setStrokeCap(Paint.Cap.ROUND);

        //初始化上部圆弧的画笔
        Paint mPaintTo = new Paint();
        mPaintTo.setAntiAlias(true);
        mPaintTo.setColor(Color.argb(154,97,186,234));
        mPaintTo.setStyle(Paint.Style.STROKE);
        mPaintTo.setStrokeWidth(PaintWidth);
        mPaintTo.setStrokeCap(Paint.Cap.ROUND);


        //初始化“步”单字字符的画笔
        Paint mPaintStepS = new Paint();
        mPaintStepS.setTextSize(40);
        mPaintStepS.setTextAlign(Paint.Align.CENTER);
        mPaintStepS.setAntiAlias(true);//抗锯齿功能
        mPaintStepS.setColor(getResources().getColor(R.color.grey));


        //startAnimator(StartAngle,currentAngle,3000);
        //加入动画
//        AnimatorSet animatorSet = new AnimatorSet();
////步数的动画
//        ValueAnimator stepAnimator = ValueAnimator.ofInt(0, mSteps[mSteps.length - 1]);
//        stepAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                step = (int) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
        //圆环动画
//        ValueAnimator percentAnimator = ValueAnimator.ofFloat(0,1);
//        percentAnimator.setTarget(percent);
//        percentAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                percent = (float) animation.getAnimatedValue();
//                invalidate();
//            }
//        });
//         percentAnimator.setDuration(3000);
//         percentAnimator.start();
//        animatorSet.setDuration(1000);
      //  animatorSet.playTogether(stepAnimator, percentAnimator);
//        animatorSet.start();

    }

//
public void setCurrentCount(int totalStepNum, int currentCounts) {
/**如果当前走的步数超过总步数则圆弧还是270度，不能成为园*/
    if (currentCounts > totalStepNum) {
        currentCounts = totalStepNum;
    }
/**所走步数占用总共步数的百分比*/
    float scale = (float) currentCounts / totalStepNum;
/**换算成弧度最后要到达的角度的长度-->弧长*/
    float currentAngleLength = scale * EndAngle;
/**开始执行动画*/
    setAnimation(StartAngle, currentAngleLength);
    //startAnimator(StartAngle,currentAngleLength,3000);
}
    /**
     * 为进度设置动画
     * ValueAnimator是整个属性动画机制当中最核心的一个类，属性动画的运行机制是通过不断地对值进行操作来实现的，
     * 而初始值和结束值之间的动画过渡就是由ValueAnimator这个类来负责计算的。
     * 它的内部使用一种时间循环的机制来计算值与值之间的动画过渡，
     * 我们只需要将初始值和结束值提供给ValueAnimator，并且告诉它动画所需运行的时长，
     * 那么ValueAnimator就会自动帮我们完成从初始值平滑地过渡到结束值这样的效果。
     *
     * @param last
     * @param current
     */
    private void setAnimation(float last, float current) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last,current);

       // progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        progressAnimator.setInterpolator(new OvershootInterpolator());
        progressAnimator.setDuration(3000);
        progressAnimator.start();
    }





//    private void startAnimator(float start, float end, long animTime) {
//        mAnimator = ValueAnimator.ofFloat(start, end);
//        mAnimator.setDuration(animTime);
//        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                currentAngle = (float) animation.getAnimatedValue();
//              //  mValue = mPercent * mMaxValue;
////                if (BuildConfig.DEBUG) {
////                    Log.d(TAG, "onAnimationUpdate: percent = " + mPercent
////                            + ";currentAngle = " + (mSweepAngle * mPercent)
////                            + ";value = " + mValue);
////                }
//                invalidate();
//            }
//        });
//        mAnimator.start();
//    }



    public float getangle()
    {
        //调用getdata获取相关数据
        getdata();
        //获取当前步数下的角度，超过设置步数则角度为最大角度
        currentAngle=stepnum*EndAngle/(float)total;
        if (currentAngle>=EndAngle)
        {currentAngle=EndAngle;}
        //startAnimator(StartAngle,EndAngle,3000);
        return currentAngle;
    }
    public void getdata()
    {
        //获取设置的总步数
        //stepNumber，步数，string
        //stepnum，步数，int
        total= Integer.parseInt(CountActivity.getCountActivity().getedit());
        stepNumber=CountActivity.getCountActivity().getstep();
        stepnum= Integer.parseInt(stepNumber);
        percent=stepnum/total;

    }



    /*
    * 以下为绘制方法
    * */
    // 绘制底部圆弧,起始角度，结束角度
    private void drawArcBottom(Canvas canvas, RectF oval)
    {
        Paint mPaintBo = new Paint();
        mPaintBo.setAntiAlias(true);
        //画笔颜色，（透明度，RGB）
        mPaintBo.setColor(Color.argb(180,192,192,192));
        //画笔空心，用以绘制圆弧
        mPaintBo.setStyle(Paint.Style.STROKE);
        //画笔宽度
        mPaintBo.setStrokeWidth(PaintWidth);
        //弧线接口处圆形
        mPaintBo.setStrokeCap(Paint.Cap.ROUND);
        //画圆弧，这个时候，绘制没有经过圆心
        canvas.drawArc(oval,StartAngle,EndAngle,false,mPaintBo);
    }
    //绘制顶部圆弧，起始角度，当前角度
    private void drawArcTop(Canvas canvas, RectF oval)
    {
        Paint mPaintTo = new Paint();
        mPaintTo.setAntiAlias(true);
        //画笔颜色，（透明度，RGB）
        mPaintTo.setColor(Color.argb(154,51,153,255));
        //mPaintTo.setColor(Color.argb(154,97,186,234));
        //画笔空心，用以绘制圆弧
        mPaintTo.setStyle(Paint.Style.STROKE);
        //画笔宽度
        mPaintTo.setStrokeWidth(PaintWidth);
        //弧线接口处圆形
        mPaintTo.setStrokeCap(Paint.Cap.ROUND);
        //调用getstep获取当前角度currentAngle
        getangle();
        //绘制步数百分比的圆弧
        canvas.drawArc(oval,StartAngle,currentAngle,false,mPaintTo);
    }



    //绘制“步”字符串显示【步】
    private void drawTextStepString(Canvas canvas, float x) {
        Paint mPaintStepS = new Paint();
        //设置字大小
        mPaintStepS.setTextSize(40);
        mPaintStepS.setTextAlign(Paint.Align.CENTER);
        mPaintStepS.setAntiAlias(true);//抗锯齿功能
        mPaintStepS.setColor(getResources().getColor(R.color.grey));
        String stepString = "步";
        Rect bounds = new Rect();
        mPaintStepS.getTextBounds(stepString, 0, stepString.length(), bounds);
        canvas.drawText(stepString, centerX, y+2*radius-bounds.height()*3, mPaintStepS);
    }

    //绘制目标提示字符串显示【今日目标： x步】
    private void drawTex(Canvas canvas, float x) {
        Paint mPaintStep = new Paint();
        mPaintStep.setTextSize(60);
        mPaintStep.setTextAlign(Paint.Align.CENTER);
        mPaintStep.setAntiAlias(true);//抗锯齿功能
        mPaintStep.setColor(getResources().getColor(R.color.black));

        String stepString = "今日目标: "+ CountActivity.getCountActivity().getedit()+" 步";
        Rect bounds = new Rect();
        mPaintStep.getTextBounds(stepString, 0, stepString.length(), bounds);
        canvas.drawText(stepString, centerX, heightwm*(float)0.47, mPaintStep);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF oval = new RectF(centerX-radius,y,centerX+radius,y+radius*2);
        // 绘制底部圆弧
        drawArcBottom(canvas,oval);
        //绘制当前圆弧
        drawArcTop(canvas,oval);
        //绘制"步数"的字符串
        drawTextStepString(canvas, x);
        //绘制目标步数字符串
        drawTex(canvas, x);
//        if (mCurrentPercent < mTargetPercent) {
//            //当前百分比+1
//            mCurrentPercent += 1;
//            //当前角度+360
//            mCurrentAngle += 3.6;
//            //每100ms重画一次
//            postInvalidateDelayed(100);
//        }


    }

}
