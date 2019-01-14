package com.example.pc252.expendabletextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by pc252 on 2019/1/7.
 */

public class ExpendableTextView extends TextView implements View.OnTouchListener{
    Paint paint;
    Path path;
    private int defaultWidth =0;
    private int defaultHeight =0;
    private int defaultLineWordsNum =11;//WRAP_CONTENT模式下一行显示多少个字符
    private String mText = "测试文字，自定义view";
    private boolean isExpend = true;//是否折叠
    public ExpendableTextView(Context context) {
        super(context);
        init();
    }

    public ExpendableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExpendableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        path = new Path();
        this.setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=getWidth();
        int height=getHeight();
        Log.d("zhangrui","height="+height+"///width="+width);
        if(width != 0 && height != 0){
            paint.setColor(Color.BLACK);
            if(isExpend) {
//                canvas.drawCircle(width - 13, 13, 13, paint);
                path.reset();
                path.moveTo(width - 18, 0);
                path.lineTo(width - 9, 9);
                path.lineTo(width,0);
                canvas.drawPath(path, paint);
            }else{
//                canvas.drawCircle(width - 13, height-13, 13, paint);
                path.reset();
                path.moveTo(width - 18, height);
                path.lineTo(width - 9, height-9);
                path.lineTo(width,height);
                canvas.drawPath(path, paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        defaultWidth = measureKuanDu((int)(defaultLineWordsNum*getTextSize()),widthMeasureSpec);
        defaultHeight = measureChangDu((int)(getTextSize()+5)*3,heightMeasureSpec);
        Log.e("zhangrui", "---defaultHeight = " + defaultHeight + "");
        Log.e("zhangrui", "---(int)(getTextSize()+5)*3 = " + (int)(getTextSize()+5)*3 + "");
        setMeasuredDimension(defaultWidth,defaultHeight);
    }

    private int measureKuanDu(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = Math.min(defaultWidth, specSize);
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.min(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureChangDu(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

       Log.d("zhangrui","line Num="+getLineCount());


        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = Math.min(defaultWidth, specSize);
                Log.e("zhangrui", "---changdu = AT_MOST"+specSize);
                break;
            case MeasureSpec.EXACTLY:
                if(isExpend) defaultWidth = (int)(getTextSize()+5)*3;
                else defaultWidth = (int)(getTextSize()+5)*getLineCount();
                Log.e("zhangrui", "---changdu = EXACTLY"+specSize);
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e("zhangrui", "---changdu = UNSPECIFIED");
                defaultWidth = Math.min(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        boolean iseable=false;
        if(isExpend) {
            if ((x - v.getTranslationX()) > (getWidth() - 20) && (x - v.getTranslationX()) < getWidth() && (y - v.getTranslationY()) > 0 && (y - v.getTranslationY()) < 20) {
                iseable = true;
            }
        }else {
            if ((x - v.getTranslationX()) > (getWidth() - 20) && (x - v.getTranslationX()) < getWidth() && (y - v.getTranslationY()) > getHeight()-20 && (y - v.getTranslationY()) < getHeight()) {
                iseable = true;
            }
        }

        if(event.getAction() == MotionEvent.ACTION_DOWN && iseable){
            if(isExpend) {
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(defaultWidth, (int)(getTextSize()+5) * getLineCount());
                v.setLayoutParams(params);
                isExpend = false;
            }else {
                ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(defaultWidth, defaultHeight *3 / getLineCount());
                v.setLayoutParams(params1);
                isExpend = true;
            }
            postInvalidate();
            return true;
        }else{
            return false;
        }
    }
}
