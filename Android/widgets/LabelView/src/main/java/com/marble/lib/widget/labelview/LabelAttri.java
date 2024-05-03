package com.marble.lib.widget.labelview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.marble.lib.widget.labelview.R;

public class LabelAttri implements Comparable<LabelAttri>{
    public String text;
    public int textColor = Color.WHITE;
    public float textSize;
    public int roundRadius;
    public int roundStrokeColor = Color.TRANSPARENT;
    public int roundStrokeWidth;
    public int paddingHorizontal;
    public int paddingVertical;
    public int backgroundColor = Color.BLACK;
    public int index;

    public LabelAttri(String text, int textColor,float textSize,
                      int roundRadius,int roundStrokeColor, 
                      int roundStrokeWidth,int backgroundColor) {
        this.text = text;
        this.textColor = textColor;
        this.textSize = textSize;
        this.roundRadius = roundRadius;
        this.roundStrokeColor = roundStrokeColor;
        this.roundStrokeWidth = roundStrokeWidth;
        this.paddingHorizontal = roundStrokeWidth;
        this.paddingVertical = roundStrokeWidth;
        this.backgroundColor = backgroundColor;
    }

    public LabelAttri(int textColor, float textSize,int roundRadius, 
                      int roundStrokeColor, int roundStrokeWidth,int backgroundColor) {
        this(null,textColor, textSize,roundRadius,
                roundStrokeColor,roundStrokeWidth,backgroundColor);
    }

    public LabelAttri(){

    }

    public static LabelAttri copy(LabelAttri bean){
        LabelAttri labelAttri = new LabelAttri(bean.text,bean.textColor,
                        bean.textSize,bean.roundRadius,bean.roundStrokeColor,
                        bean.roundStrokeWidth,bean.backgroundColor);
        labelAttri.paddingHorizontal = bean.paddingHorizontal;
        labelAttri.paddingVertical = bean.paddingVertical;
        return labelAttri;
    }

    public static LabelAttri createLabelAttriFromStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.RoundLabelView);

            int roundRadius = a.getDimensionPixelSize(
                    R.styleable.RoundLabelView_rlvRoundRadius,0);
            int strokeColor = a.getColor(
                    R.styleable.RoundLabelView_rlvRoundStrokeColor,Color.TRANSPARENT);
            int strokeWidth = a.getDimensionPixelSize(
                    R.styleable.RoundLabelView_rlvRoundStrokeWidth,0);
            int textColor = a.getColor(
                    R.styleable.RoundLabelView_rlvTextColor,Color.WHITE);
            int textSize = a.getDimensionPixelSize(
                    R.styleable.RoundLabelView_rlvTextSize,0);
            String text = a.getString(
                    R.styleable.RoundLabelView_rlvText);
            int backgroundColor = a.getColor(
                    R.styleable.RoundLabelView_rlvBackgroundColor,Color.BLACK);
            LabelAttri defaultLabelConfig = new LabelAttri(text,textColor,textSize,
                    roundRadius,strokeColor,strokeWidth,backgroundColor);
            
            a.recycle();
            return defaultLabelConfig;
        }
        return new LabelAttri();
    }

    @Override
    public int compareTo(LabelAttri o) {
        return index-o.index;
    }
}
