package com.marble.lib.widget.pairview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marble.lib.widget.pairview.R;

/** 主要用于显示一行图片+文字+箭头的场景<br>
 * itaTextValue--------------------要显示的文本<br>
   itaTextSize----------------要显示文本的大小<br>
   itaTextColor---------------要显示的文本颜色<br>
   itaMyIcon--------------------要显示的图片资源id<br>
   itaIconSize----------------图片大小<br>
   itaArrowSrc----------------箭头资源图片<br>
   itaTextIconMargin------------文本和图片之间的间距<br>
   itaTextCenter------------文本是否居中，默认不居中<br>
 * @author marblema
 * @time 2021-01-07
 */
public class IconTextArrowView extends RelativeLayout {
	private static final int IconSizeDefault = LayoutParams.WRAP_CONTENT;
	private static final int TextIconMarginDefault = 5;
	private TextView textView;
	private ImageView iconView;
	private ImageView arrowView;
//	private int iconPosition = IconPositionDefault;
	private int iconSize = IconSizeDefault;
	private int textIconMargin = TextIconMarginDefault;
	private boolean textCenter = false;
	private int iconResId = -1;
	private int arrowResId = -1;
    private int textColor = -1;

	public IconTextArrowView(Context context) {
		super(context);
		initView(context, null, 0);
	}

	public IconTextArrowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs, 0);
	}

	public IconTextArrowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs, defStyle);
	}

	private void initView(Context context, AttributeSet attrs, int defStyle){
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.IconTextArrowView);
		if(a!=null){
			readValues(a,context);
			a.recycle();
		}

		if(iconView!=null) {
			LayoutParams iParams = new LayoutParams(
					iconSize, iconSize);
			iParams.alignWithParent = true;
			iParams.addRule(CENTER_VERTICAL);
			iParams.addRule(ALIGN_PARENT_START);
			iconView.setId(R.id.iconTextArrow_iconRes);
			addView(iconView, iParams);
		}

		if(textView!=null) {
			LayoutParams tParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			if(textCenter) {
				tParams.alignWithParent = true;
				tParams.addRule(CENTER_IN_PARENT);
			}else {
				tParams.addRule(CENTER_VERTICAL);
				tParams.leftMargin = textIconMargin;
				if(iconView!=null)
					tParams.addRule(END_OF, R.id.iconTextArrow_iconRes);
			}
			addView(textView, tParams);
		}

		if(arrowView!=null) {
			LayoutParams aParams = new LayoutParams(
					LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			aParams.alignWithParent = true;
			aParams.addRule(CENTER_VERTICAL);
			aParams.addRule(ALIGN_PARENT_END);
			addView(arrowView, aParams);
		}
	}

    private void readValues(TypedArray a,Context context){
        iconResId = a.getResourceId(
                R.styleable.IconTextArrowView_itaIconSrc,0);
        if(iconResId>0) {
			iconView = new ImageView(context);
			iconView.setImageResource(iconResId);
			iconSize = a.getDimensionPixelSize(
					R.styleable.IconTextArrowView_itaIconSize,IconSizeDefault);
			int iconPadding = a.getDimensionPixelSize(
					R.styleable.IconTextArrowView_itaIconPadding,-1);
			if(iconPadding > 0){
				iconView.setPadding(iconPadding,iconPadding,iconPadding,iconPadding);
			}
		}
        String text = a.getString(
                R.styleable.IconTextArrowView_itaText);

		if(text!=null) {
			textCenter = a.getBoolean(
					R.styleable.IconTextArrowView_itaTextCenter, false);
			if (!textCenter){
				textIconMargin = a.getDimensionPixelSize(
						R.styleable.IconTextArrowView_itaTextIconMargin, TextIconMarginDefault);
			}
			textView = new TextView(context);
			textView.setText(text);
			int textSize = a.getDimensionPixelSize(
					R.styleable.IconTextArrowView_itaTextSize, -1);
			if (textSize > 0)
				textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
			textColor = a.getColor(
					R.styleable.IconTextArrowView_itaTextColor, -1);
			textView.setTextColor(textColor);
		}

		arrowResId = a.getResourceId(
				R.styleable.IconTextArrowView_itaArrowSrc,0);
		if(arrowResId>0) {
			arrowView = new ImageView(context);
			arrowView.setImageResource(arrowResId);
		}
    }

	public void setText(CharSequence text){
		textView.setText(text);
	}

	public void setText(int strRes){
		textView.setText(strRes);
	}

	public void setIcon(Bitmap icon){
		iconView.setImageBitmap(icon);
	}

	public void setIcon(int resId){
		iconResId = resId;
		iconView.setImageResource(resId);
	}

	public TextView getTextView(){
		return textView;
	}

	public ImageView getIconView(){
		return iconView;
	}
}
