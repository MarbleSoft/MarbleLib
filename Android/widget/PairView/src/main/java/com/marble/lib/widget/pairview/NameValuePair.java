package com.marble.lib.widget.pairview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.marble.lib.widget.pairview.R;

/**
 * 名称、值控件
 * @author marblema
 * @time 2021-04-30
 */
public class NameValuePair extends RelativeLayout{
	private static final int DefaultTextSize = 18;
	private static final int DefaultTextColor = Color.WHITE;
	private static final int IconSizeDefault = LayoutParams.WRAP_CONTENT;
	private ImageView leftIcon;
	private ImageView rightIcon;
	private TextView nameView;
	private TextView valueView;

	public NameValuePair(Context context) {
		super(context);
		initView(context, null);
	}

	public NameValuePair(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	public NameValuePair(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public NameValuePair(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs){
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.NameValuePair);
		if(a!=null){
			int defaultSize = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpDefaultTextSize,DefaultTextSize);
			int defaultColor = a.getColor(
					R.styleable.NameValuePair_nvpDefaultTextColor,DefaultTextColor);
			setLeftIconAttri(context,a);

			setNameViewAttri(context,a,defaultSize,defaultColor);

			setRightIconAttri(context,a);

			setValueViewAttri(context,a,defaultSize,defaultColor);

			setDividerAttri(context,a);

			a.recycle();
		}
	}

	private void setLeftIconAttri(Context context,TypedArray a){
		int leftIconRes = a.getResourceId(
				R.styleable.NameValuePair_nvpLeftIcon,-1);
		if(leftIconRes>0) {
			leftIcon = new ImageView(context);
			leftIcon.setImageResource(leftIconRes);
			int leftIconSize = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpLeftIconSize,IconSizeDefault);
			LayoutParams lIParams = new LayoutParams(leftIconSize, leftIconSize);
			lIParams.alignWithParent = true;
			lIParams.addRule(CENTER_VERTICAL);
			lIParams.addRule(ALIGN_PARENT_START);
			int leftIconMargin = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpLeftIconMargin,-1);
			if(leftIconMargin>0)
				lIParams.setMarginEnd(leftIconMargin);
			int marginStart = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpMarginStart,-1);
			if(marginStart>0)
				lIParams.setMarginStart(marginStart);
			leftIcon.setId(R.id.NameValuePair_leftIcon);
			addView(leftIcon, lIParams);
		}
	}

	private void setNameViewAttri(Context context,TypedArray a,int defaultSize,int defaultColor){
		CharSequence nameText = a.getText(
				R.styleable.NameValuePair_nvpNameText);
		if(nameText!=null) {
			nameView = new TextView(context);
			int nameSize = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpNameSize,defaultSize);
			nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX,nameSize);
			int nameColor = a.getColor(
					R.styleable.NameValuePair_nvpNameColor,defaultColor);
			nameView.setTextColor(nameColor);
			nameView.setText(nameText);
			nameView.setIncludeFontPadding(false);
			LayoutParams nParams = new LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			nParams.addRule(CENTER_VERTICAL);
			boolean nameCenter = a.getBoolean(
					R.styleable.NameValuePair_nvpNameCenter,false);
			if(nameCenter) {
				nParams.alignWithParent = true;
				nParams.addRule(CENTER_IN_PARENT);
			}else if(leftIcon==null) {
				nParams.alignWithParent = true;
				nParams.addRule(ALIGN_PARENT_START);
				int marginStart = a.getDimensionPixelSize(
						R.styleable.NameValuePair_nvpMarginStart,-1);
				if(marginStart>0)
					nParams.setMarginStart(marginStart);
			}else{
				nParams.addRule(END_OF,R.id.NameValuePair_leftIcon);
			}
			nameView.setId(R.id.NameValuePair_nameView);
			addView(nameView, nParams);
		}
	}

	private void setRightIconAttri(Context context,TypedArray a){
		int rightIconRes = a.getResourceId(
				R.styleable.NameValuePair_nvpRightIcon,-1);
		if(rightIconRes>0) {
			rightIcon = new ImageView(context);
			rightIcon.setImageResource(rightIconRes);
			int rightIconSize = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpRightIconSize,IconSizeDefault);
			LayoutParams rIParams = new LayoutParams(rightIconSize, rightIconSize);
			rIParams.alignWithParent = true;
			rIParams.addRule(CENTER_VERTICAL);
			rIParams.addRule(ALIGN_PARENT_END);
			int rightIconMargin = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpRightIconMargin,-1);
			if(rightIconMargin>0)
				rIParams.setMarginStart(rightIconMargin);
			int marginEnd = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpMarginEnd,-1);
			if(marginEnd>0)
				rIParams.setMarginEnd(marginEnd);
			rightIcon.setId(R.id.NameValuePair_rightIcon);
			addView(rightIcon, rIParams);
		}
	}

	private void setValueViewAttri(Context context,TypedArray a,int defaultSize,int defaultColor){
		CharSequence valueText = a.getText(
				R.styleable.NameValuePair_nvpValueText);
		CharSequence valueHint = a.getText(
				R.styleable.NameValuePair_nvpValueHint);
		if(valueText!=null || valueHint!=null) {
			int style = a.getInt(R.styleable.NameValuePair_nvpStyle,0);
			valueView = (style==0)? new TextView(context) : new EditText(context);
			int valueSize = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpValueSize,defaultSize);
			valueView.setTextSize(TypedValue.COMPLEX_UNIT_PX,valueSize);

			int valueColor = a.getColor(
					R.styleable.NameValuePair_nvpValueColor,defaultColor);
			valueView.setTextColor(valueColor);
			if(valueText!=null)
				valueView.setText(valueText);
			valueView.setIncludeFontPadding(false);
			valueView.setBackground(null);
			if(valueHint!=null)
				valueView.setHint(valueHint);
			int hintColor = a.getColor(
					R.styleable.NameValuePair_nvpHintColor,defaultColor);
			valueView.setHintTextColor(hintColor);

			boolean valueCenter = a.getBoolean(
					R.styleable.NameValuePair_nvpValueCenter,false);

			LayoutParams vParams = new LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			if(valueCenter){
				vParams.alignWithParent = true;
				vParams.addRule(CENTER_IN_PARENT);
			}else {
				vParams.addRule(CENTER_VERTICAL);
				if (rightIcon == null) {
					vParams.alignWithParent = true;
					vParams.addRule(ALIGN_PARENT_END);
					int marginEnd = a.getDimensionPixelSize(
							R.styleable.NameValuePair_nvpMarginEnd, -1);
					if (marginEnd > 0)
						vParams.setMarginEnd(marginEnd);
				} else {
					vParams.addRule(START_OF, R.id.NameValuePair_rightIcon);
				}
				boolean takePlaceLeft = a.getBoolean(
						R.styleable.NameValuePair_nvpValueTakePlaceLeft, true);
				if(takePlaceLeft) {
					vParams.addRule(END_OF, R.id.NameValuePair_nameView);
				}
				int nameValueMargin = a.getDimensionPixelSize(
						R.styleable.NameValuePair_nvpNameValueMargin, -1);
				if (nameValueMargin > 0)
					vParams.setMarginStart(nameValueMargin);
				int gravity = a.getInt(R.styleable.NameValuePair_nvpValueGravity,
						Gravity.START | Gravity.CENTER_VERTICAL);
				valueView.setGravity(gravity);
			}
			int inputType = a.getInt(R.styleable.NameValuePair_nvpValueInputType,-1);
			if(inputType >= 0)
				valueView.setInputType(inputType);
			valueView.setId(R.id.NameValuePair_valueView);
			addView(valueView, vParams);
		}
	}


	private void setDividerAttri(Context context,TypedArray a){
		int dividerLen = a.getDimensionPixelSize(
				R.styleable.NameValuePair_nvpDividerLen,-1);
		if(dividerLen>0) {
			View divider = new View(context);
			int dividerRes = a.getResourceId(
					R.styleable.NameValuePair_nvpDividerBackground,-1);
			if(dividerRes>0){
				divider.setBackgroundResource(dividerRes);
			}else{
				int dividerColor = a.getColor(
						R.styleable.NameValuePair_nvpDividerColor,DefaultTextColor);
				divider.setBackgroundColor(dividerColor);
			}
			int dividerPosition = a.getInt(
					R.styleable.NameValuePair_nvpDividerPosition,0);
			int dividerMargin = a.getDimensionPixelSize(
					R.styleable.NameValuePair_nvpDividerMargin,0);
			LayoutParams dParams;
			switch (dividerPosition){
				case 1:
					dParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerLen);
					dParams.alignWithParent = true;
					dParams.addRule(ALIGN_BOTTOM);
					dParams.topMargin = dividerMargin;
					break;
				default:
					dParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dividerLen);
					dParams.alignWithParent = true;
					dParams.addRule(ALIGN_TOP);
					dParams.bottomMargin = dividerMargin;
					break;
				case 2:
					dParams = new LayoutParams(dividerLen,ViewGroup.LayoutParams.MATCH_PARENT);
					dParams.alignWithParent = true;
					dParams.addRule(ALIGN_START);
					dParams.setMarginEnd(dividerMargin);
					break;
				case 3:
					dParams = new LayoutParams(dividerLen,ViewGroup.LayoutParams.MATCH_PARENT);
					dParams.alignWithParent = true;
					dParams.addRule(ALIGN_END);
					dParams.setMarginStart(dividerMargin);
					break;
			}

			addView(divider, dParams);
		}
	}

	public void setLeftIconClickListener(OnClickListener listener){
		if(leftIcon!=null) {
			leftIcon.setOnClickListener(listener);
		}
	}

	public void setLeftClickListener(OnClickListener listener){
		if(leftIcon!=null) {
			leftIcon.setOnClickListener(listener);
		}
		if(nameView!=null) {
			nameView.setOnClickListener(listener);
		}
	}

	public ImageView getLeftIcon(){
		return leftIcon;
	}

	public String getName(){
		return nameView!=null? nameView.getText().toString() : "";
	}

	public void setName(CharSequence name){
		if(nameView!=null && name!=null)
			nameView.setText(name);
	}

	public void setRightIconClickListener(OnClickListener listener){
		if(rightIcon!=null) {
			rightIcon.setOnClickListener(listener);
		}
	}

	public void setRightClickListener(OnClickListener listener){
		if(rightIcon!=null) {
			rightIcon.setOnClickListener(listener);
		}
		if(valueView!=null) {
			valueView.setOnClickListener(listener);
		}
	}

	public ImageView getRightIcon(){
		return rightIcon;
	}

	public TextView getValueView(){
		return valueView;
	}

	public String getValue(){
		return valueView!=null? valueView.getText().toString() : "";
	}

	public void setValue(CharSequence value){
		if(valueView!=null && value!=null)
			valueView.setText(value);
	}
}
