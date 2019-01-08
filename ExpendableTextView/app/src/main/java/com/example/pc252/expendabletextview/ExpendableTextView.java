package com.example.pc252.expendabletextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private int defaultWidth =0;
    private int defaultHeight =0;
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
            canvas.drawCircle(width-13,13,13,paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        defaultWidth = measureChangDu(300,widthMeasureSpec);
        defaultHeight = measureChangDu(100,heightMeasureSpec);
        setMeasuredDimension(defaultWidth,defaultHeight);
    }
    private int measureChangDu(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        Log.e("zhangrui", "---speSize = " + specSize + "");
       Log.d("zhangrui","line Num="+getLineCount());


        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = Math.min(defaultWidth, specSize);
                Log.e("zhangrui", "---speMode = AT_MOST");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("zhangrui", "---speMode = EXACTLY");
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.e("zhangrui", "---speMode = UNSPECIFIED");
                defaultWidth = Math.min(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        boolean iseable=false;
        if((x-v.getTranslationX()) > (getWidth()-20) && (x-v.getTranslationX()) < getWidth() && (y-v.getTranslationY()) >0 && (y-v.getTranslationY()) < 20){
            iseable = true;
        }
        Log.d("zhangrui","iseable="+iseable);
        if(event.getAction() == MotionEvent.ACTION_DOWN && iseable){
            if(isExpend) {
                Log.d("zhangrui", "nonono" + defaultHeight);
                ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(defaultWidth, defaultHeight * 3);
                this.setLayoutParams(params);
                Log.d("zhangrui", "nonono" + defaultHeight);
                isExpend = false;
            }else {
                Log.d("zhangrui", "nonono" + defaultHeight);
                ConstraintLayout.LayoutParams params1 = new ConstraintLayout.LayoutParams(defaultWidth, defaultHeight / 3);
                this.setLayoutParams(params1);
                Log.d("zhangrui", "nonono" + defaultHeight);
                isExpend = true;
            }
            postInvalidate();
            return true;
        }else{
            return false;
        }
    }
}