package com.marble.lib.widget.labelview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

//import com.google.android.material.internal.FlowLayout;
import com.google.android.material.internal.FlowLayout;
import com.mar.lib.util.StrUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("RestrictedApi")
public class LabelLayout extends FlowLayout implements View.OnClickListener {
    public static final int ChooseModeNone = 0;
    public static final int ChooseModeSingle = 1;
    public static final int ChooseModeMulti = 2;

    private int chooseMode;
    private int maxChoiceNum;
    private List<LabelAttri> labels;
    private List<LabelAttri> selectLabels;
    private LabelAttri defaultLabelConfig;

    public LabelLayout(@NonNull Context context) {
        super(context);
        setAttribe(context,null);
    }

    public LabelLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setAttribe(context,attrs);
    }

    public LabelLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttribe(context,attrs);
    }

    public LabelLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttribe(context,attrs);
    }

    private void setAttribe(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.LabelLayout);

            int chooseMode = a.getInt(
                    R.styleable.LabelLayout_llChooseMode,0);
            if(ChooseModeMulti == chooseMode){
                maxChoiceNum = a.getInt(
                        R.styleable.LabelLayout_llMaxChoiceNum,2);
            }
            setChooseMode(chooseMode,maxChoiceNum);
            defaultLabelConfig = LabelAttri.createLabelAttriFromStyle(context, attrs);

            int paddingHorizontal = a.getDimensionPixelSize(
                    R.styleable.LabelLayout_llDefaultPaddingHorizontal,0);
            int paddingVertical = a.getDimensionPixelSize(
                    R.styleable.LabelLayout_llDefaultPaddingVertical,0);
            if(paddingHorizontal>0)
                defaultLabelConfig.paddingHorizontal = paddingHorizontal;
            if(paddingVertical>0)
                defaultLabelConfig.paddingVertical = paddingVertical;

            String values1 = a.getString(
                    R.styleable.LabelLayout_llLabels);
            CharSequence[] labels;
            if(!StrUtils.isEmpty(values1)){
                labels = values1.split("\\|");
            }else {
                labels = a.getTextArray(
                        R.styleable.LabelLayout_llLabelsArray);
            }
            a.recycle();
            addLabels(labels);
        }
    }

    /*
    private void setAttribe2(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.LabelLayout);

            chooseMode = a.getInt(
                    R.styleable.LabelLayout_llChooseMode,0);
            if(ChooseModeMulti == chooseMode){
                maxChoiceNum = a.getInt(
                        R.styleable.LabelLayout_llMaxChoiceNum,2);
            }

            int roundRadius = a.getDimensionPixelSize(
                    R.styleable.LabelLayout_llDefaultRound,0);
            int drawRoundColor = a.getColor(
                    R.styleable.LabelLayout_llDefaultDrawRoundColor, Color.TRANSPARENT);
            int strokeColor = a.getColor(
                    R.styleable.LabelLayout_llDefaultRoundStrokeColor,Color.TRANSPARENT);
            int strokeWidth = a.getDimensionPixelSize(
                    R.styleable.LabelLayout_llDefaultRoundStrokeWidth,0);
            float textSize = a.getDimensionPixelSize(
                    R.styleable.LabelLayout_llDefaultTextSize,0);
            int textColor = a.getColor(
                    R.styleable.LabelLayout_llDefaultTextColor, Color.TRANSPARENT);
            int backgroundColor = a.getColor(
                    R.styleable.LabelLayout_llDefaultBackgroundColor, Color.TRANSPARENT);
            int paddingHorizontal = a.getDimensionPixelSize(
                    R.styleable.LabelLayout_llDefaultPaddingHorizontal,0);
            int paddingVertical = a.getDimensionPixelSize(
                    R.styleable.LabelLayout_llDefaultPaddingVertical,0);
            defaultLabelConfig = new LabelAttri(textColor,textSize,
                    roundRadius,drawRoundColor,strokeColor,strokeWidth);
            defaultLabelConfig.backgroundColor = backgroundColor;
            if(paddingHorizontal>0)
                defaultLabelConfig.paddingHorizontal = paddingHorizontal;
            if(paddingVertical>0)
                defaultLabelConfig.paddingVertical = paddingVertical;

            String values1 = a.getString(
                    R.styleable.LabelLayout_llLabels);
            CharSequence[] labels;
            if(!StrUtils.isEmpty(values1)){
                labels = values1.split("\\|");
            }else {
                labels = a.getTextArray(
                        R.styleable.LabelLayout_llLabelsArray);
            }
            a.recycle();
            addLabels(labels);
        }
    }*/

    public void addLabels(CharSequence[] strLabels){
        if(strLabels!=null && strLabels.length>0){
            List<LabelAttri> labels = new ArrayList<>(strLabels.length);
            for(CharSequence s:strLabels){
                LabelAttri bean = LabelAttri.copy(defaultLabelConfig);
                bean.text = s.toString();
                labels.add(bean);
            }
            addLabels(labels,null);
        }
    }

    public void addLabels(List<LabelAttri> labels,List<LabelAttri> selectLabels){
        this.labels = labels;
        this.selectLabels = selectLabels;
        if(labels!=null && labels.size()>0){
            removeAllViews();
            for(int i=0;i<labels.size();i++){
                LabelAttri bean = labels.get(i);
                RoundLabelView rlv = new RoundLabelView(getContext());
                rlv.setLabelConfig(bean);
                rlv.setIndex(i);
                addView(rlv);
            }
        }
        checkSelectState();
    }

    public void setChooseMode(int chooseMode,int maxChoiceNum) {
        if(this.chooseMode != chooseMode) {
            this.chooseMode = chooseMode;
        }
        if(this.maxChoiceNum != maxChoiceNum) {
            this.maxChoiceNum = maxChoiceNum;
        }
        checkSelectState();
    }

    private boolean canSelect;
    private void checkSelectState(){
        canSelect = (chooseMode==ChooseModeSingle
                || chooseMode==ChooseModeMulti);
        int childNum = getChildCount();
        for(int i=0;i<childNum;i++){
            View v = getChildAt(i);
            if(v instanceof RoundLabelView){
                v.setOnClickListener(canSelect?this:null);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v instanceof RoundLabelView){
            RoundLabelView selectedView = (RoundLabelView) v;
            int index = selectedView.getIndex();
            if(index<0)
                return;
            if(chooseMode == ChooseModeSingle){
                if(labels.size()!=selectLabels.size()){
                    if(selectLabels.size()!=1){
                        logE(getClass().getSimpleName(),"选中style设置不正确");
                        return;
                    }
                    setSingleSelectedSytle(index,selectedView,0);
                }else {
                    setSingleSelectedSytle(index,selectedView,index);
                }
            }else if(chooseMode == ChooseModeMulti){
                if(labels.size()!=selectLabels.size()){
                    if(selectLabels.size()!=1){
                        logE(getClass().getSimpleName(),"选中style设置不正确");
                        return;
                    }
                    setMultiSelectedSytle(index,selectedView,0);
                }else {
                    setMultiSelectedSytle(index,selectedView,index);
                }
            }
            if(onTabLabelListener !=null){
                onTabLabelListener.onTabLabel(selectedView,
                        index,selectedView.getLabelConfig(),
                        selectedView.isLabelSelected());
            }
        }
    }

    private void setSingleSelectedSytle(int selectIndex, RoundLabelView selectedView,
                                        int whichSelectedStyle){
        boolean shouldSetSelected = true;
        if(selectLabels==null || selectLabels.size()<=0) {
            shouldSetSelected = false;
        }
        if(shouldSetSelected){
            LabelAttri selected = selectLabels.get(whichSelectedStyle);
            selectedView.setLabelConfig(selected);
        }
        selectedView.setLabelSelected(true);
        //其它视图恢复原状
        for(int i=0;i<labels.size();i++){
            View ov = getChildAt(i);
            if(ov instanceof RoundLabelView){
                RoundLabelView labelView = (RoundLabelView) ov;
                if(labelView.getIndex() == selectIndex)
                    continue;
                labelView.setLabelConfig(labels.get(i));
                labelView.setLabelSelected(false);
            }
        }
    }

    private int selectedNum = 0;
    private void setMultiSelectedSytle(int selectIndex,RoundLabelView selectedView,
                                  int whichSelectedStyle){
        boolean shouldSetSelected = true;
        if(selectLabels==null || selectLabels.size()<=0) {
            shouldSetSelected = false;
        }
        LabelAttri selected = shouldSetSelected?
                selectLabels.get(whichSelectedStyle):
                labels.get(selectIndex);
        if(selectedView.isLabelSelected()){//已选中，去选
            selectedView.setLabelConfig(labels.get(selectIndex));
            selectedView.setLabelSelected(false);
            --selectedNum;
        }else{//未选中，设置选中状态
            if(selectedNum<maxChoiceNum) {
                selectedView.setLabelConfig(selected);
                selectedView.setLabelSelected(true);
                ++selectedNum;
            }else if(showMaxChoicExceedTips){
                Toast.makeText(getContext(),StrUtils.isEmpty(maxChoicExceedTips)?
                        String.format("最多选择%d个",maxChoiceNum):maxChoicExceedTips,
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private OnTabLabelListener onTabLabelListener;
    public void setOnTabLabelListener(OnTabLabelListener onTabLabelListener) {
        this.onTabLabelListener = onTabLabelListener;
    }

    public interface OnTabLabelListener {
        public void onTabLabel(RoundLabelView v,int index,
                               LabelAttri labelAttri,boolean selected);
    }

    private String maxChoicExceedTips;
    private boolean showMaxChoicExceedTips = true;
    public void setShowMaxChoicExceedTips(boolean show,String tips){
        this.showMaxChoicExceedTips = show;
        if(show && !StrUtils.isEmpty(tips)){
            maxChoicExceedTips = tips;
        }
    }
    public void setShowMaxChoicExceedTips(boolean show){
        setShowMaxChoicExceedTips(show,null);
    }

    public List<Integer> getSelectedIndex(){
        if(chooseMode==ChooseModeNone)
            return null;
        List<Integer> result = new ArrayList<>();
        int childNum = getChildCount();
        for(int i=0;i<childNum;i++){
            View v = getChildAt(i);
            if(v instanceof RoundLabelView){
                if(((RoundLabelView) v).isLabelSelected()){
                    result.add(((RoundLabelView) v).getIndex());
                }
            }
        }
        return result;
    }

    @Override
    public void setLineSpacing(int lineSpacing) {
        super.setLineSpacing(lineSpacing);
    }

    @Override
    public void setItemSpacing(int itemSpacing) {
        super.setItemSpacing(itemSpacing);
    }

    private void logE(String tag,String msg){
//        LoggerFactory.getLogger(getClass().getSimpleName())
//                .error("选中style设置不正确");
        Log.e(tag,msg);
    }
}
