package com.marble.lib.widget.labelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class RoundLabelView extends View {
    private LabelAttri labelConfig;

    public RoundLabelView(Context context) {
        super(context);
        setAttribe(context,null);
    }

    public RoundLabelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttribe(context,attrs);
    }

    public RoundLabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttribe(context,attrs);
    }

    private void setAttribe(Context context, AttributeSet attrs) {
        labelConfig = LabelAttri.createLabelAttriFromStyle(context, attrs);
        initPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else{
            width += labelConfig.roundRadius;
            width += getPaddingLeft() + getPaddingRight();
            if(labelConfig.text!=null)
                width += paint.measureText(labelConfig.text.toString());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height += getPaddingTop()+getPaddingBottom();
            height += paint.descent() - paint.ascent();
        }
        setMeasuredDimension(width, height);
    }

    private Paint paint;
    private void initPaint(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(
                PorterDuff.Mode.SRC_OVER));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setLinearText(true);
        paint.setTextSize(labelConfig.textSize);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        paint.setColor(labelConfig.roundStrokeColor);
        paint.setStrokeWidth(labelConfig.roundStrokeWidth);
        paint.setStyle(Paint.Style.STROKE);//设置样式：FILL表示颜色填充整个；STROKE表示空心
        float w1 = labelConfig.roundStrokeWidth / 2.0f;
        w1 = Math.max(w1, 0);
        RectF roundRectF1 = new RectF(w1, w1,
                getWidth()-w1, getHeight()-w1);
        canvas.drawRoundRect(roundRectF1, labelConfig.roundRadius, 
                labelConfig.roundRadius, paint);

        paint.setColor(labelConfig.backgroundColor);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);//设置样式：FILL表示颜色填充整个；STROKE表示空心
        int w2 = labelConfig.roundStrokeWidth - 
                dip2px(1);
        w2 = Math.max(w2, 0);
        RectF roundRectF2 = new RectF(w2, w2,
                getWidth()- w2, getHeight()- w2);
        canvas.drawRoundRect(roundRectF2, labelConfig.roundRadius, 
                labelConfig.roundRadius, paint);

        paint.setColor(labelConfig.textColor);
        int xPos = getWidth()/2;
        int yPos = (int) ((getHeight()- paint.descent()-paint.ascent())/2);
        canvas.drawText(labelConfig.text.toString(),
                        xPos,yPos,paint);
        canvas.save();
    }

    public void setLabelConfig(LabelAttri labelConfig) {
        if(labelConfig==null || this.labelConfig == labelConfig)
            return;

        this.labelConfig = labelConfig;
        paint.setTextSize(labelConfig.textSize);
        setPadding(labelConfig.paddingHorizontal,labelConfig.paddingVertical,
                labelConfig.paddingHorizontal,labelConfig.paddingVertical);
        requestLayout();
        postInvalidate();
    }

    public void setRoundRadius(int roundRadius) {
        labelConfig.roundRadius = roundRadius;
        postInvalidate();
    }

    public void setRoundStrokeColor(int roundStrokeColor) {
        labelConfig.roundStrokeColor = roundStrokeColor;
        postInvalidate();
    }

    public void setRoundStrokeWidth(int roundStrokeWidth) {
        labelConfig.roundStrokeWidth = roundStrokeWidth;
        postInvalidate();
    }

    public void setText(String text) {
        labelConfig.text = text;
        requestLayout();
        postInvalidate();
    }

    public void setTextColor(int textColor) {
        labelConfig.textColor = textColor;
        postInvalidate();
    }

    public void setTextSize(float textSize) {
        labelConfig.textSize = textSize;
        paint.setTextSize(textSize);
        postInvalidate();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        labelConfig.backgroundColor = backgroundColor;
        postInvalidate();
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int index=-1;//用于记录索引
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public LabelAttri getLabelConfig() {
        return labelConfig;
    }

    private boolean isLabelSelected = false;
    public boolean isLabelSelected() {
        return isLabelSelected;
    }
    public void setLabelSelected(boolean labelSelected) {
        isLabelSelected = labelSelected;
    }
}
