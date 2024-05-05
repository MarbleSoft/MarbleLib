package com.marble.lib.component.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import androidx.annotation.StyleRes;

import com.marble.lib.component.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据宽高比以及距离屏幕边缘距离自动调整大小的弹窗。<br>
 * 子类需要实现两个和宽高相关的函数：<br>
 *  float getAspectRatio()---------弹窗的宽高比；<br>
 *  int getStartInDP()-------------弹窗开始距离屏幕边缘的距离。
 *          如果是竖屏，这个距离是弹窗左边距离屏幕左边的距离；
 *          如果是横屏，这个距离是弹窗上边距离屏幕顶部的距离
 * <br>指定这两个参数以后弹窗可以在横屏和竖屏根据起始距离和宽高比自动调整大小。
 * Created by malibo on 2018/6/11.
 */
public abstract class AutoResizeDialog extends Dialog implements View.OnClickListener{
    private Context context;
    protected View closeBtn;
    private volatile long lastClickTime;
    protected int wrap_content = ViewGroup.LayoutParams.WRAP_CONTENT;
    protected Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    public AutoResizeDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        this.context = context;
    }

    public AutoResizeDialog(@androidx.annotation.NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getLayoutId()>0) {
            setContentView(getLayoutId());
        }
        initView();
        if(closeBtn!=null){
            closeBtn.setOnClickListener(this);
        }
        setCanceledOnTouchOutside(false);
        setWindowAttri();
    }

    private void setWindowAttri(){
        Window dialogWindow = getWindow();
        if(dialogWindow!=null && dialogWindow.getAttributes()!=null) {
            LayoutParams lp = dialogWindow.getAttributes();
            lp.gravity = Gravity.CENTER;
            lp.type = LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
            adjustWidthAndHeight(lp);
            dialogWindow.setAttributes(lp);
        }
    }

    protected void adjustWidthAndHeight(LayoutParams lp){
        int screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        //根据设计稿给出的宽和高动态调整弹窗的宽高。调整的逻辑是
        //竖屏指定弹窗距离屏幕左右的距离得到宽度然后根据比例计算高度
        //横屏指定弹窗距离屏幕上下的距离得到高度然后根据比例计算宽度
        int start = dip2px(getStartInDP());
        float ratio = getAspectRatio();
        if(start<0){
            start = 0;
        }
        if(screenWidth<screenHeight){//竖屏
            lp.width = screenWidth - start;
            lp.height = (int)((float)lp.width / ratio);
        }else{//横屏
            int barHeight = getStatusBarHeight();
            lp.gravity = Gravity.CENTER_HORIZONTAL|Gravity.TOP;
            if(start/2>barHeight) {
                lp.height = screenHeight - start;
                lp.y = start/2 - barHeight;
            }else{//上边的留白比状态栏还小,调整上部留白高度为状态栏高度
                lp.height = screenHeight - barHeight*2;
                lp.y = 0;
            }
            lp.width = (int)((float)lp.height * ratio);
        }
    }

    /**
     * 防止按钮重复点击处理类
     *
     * @return
     */
    public synchronized boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 <= timeD && timeD < 400) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void onClick(View v) {
        if (isFastDoubleClick()) {
            return;
        }
        if(closeBtn == v){//点击关闭按钮
            whenDone();
        }
    }

    @Override
    public void onBackPressed() {
        if(closeBtn!=null) {
            closeBtn.performClick();
        }
        super.onBackPressed();
    }

    private void whenDone(){
        dismiss();
        context = null;
    }

    @Override
    public void show() {
        if(context==null) {
            return;
        }else if(context instanceof Activity){
            Activity activity = (Activity)context;
            if(activity.isFinishing() || activity.isDestroyed()){
                return;
            }
        }
        try {
            super.show();
        }catch (Exception e){
            logger.warn("显示弹窗出错:",e);
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        }catch (Exception e){
            logger.warn("关闭弹窗出错:",e);
        }
    }

    /**如果某个子视图也需要按照比例调整大小和margin，可以使用此方法*/
    protected void adjustView(View view,int w,int h,int lM,int tM,int rM,int bM) {
        if(getWindow()==null){
            return;
        }
        LayoutParams lp = getWindow().getAttributes();
        if(w>=0) {
            view.getLayoutParams().width = lp.width * w / getWidth();
        }
        if(h>=0) {
            view.getLayoutParams().height = lp.height * h / getHeight();
        }
        if(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            mp.leftMargin = lp.width * lM / getWidth();
            mp.topMargin = lp.height * tM / getHeight();
            mp.rightMargin = lp.width * rM / getWidth();
            mp.bottomMargin = lp.height * bM / getHeight();
        }
    }

    /**在指定的time时间（单位：毫秒）后自动消失*/
    protected void autoDismissInTime(long time){
        if(closeBtn!=null){
            closeBtn.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            },time);
        }
    }

    /**
     * 获得状态栏的高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**由子类实现，返回布局id*/
    protected abstract int getLayoutId();
    /**由子类实现，初始化控件*/
    protected abstract void initView();
    /**弹窗的宽高比*/
    protected float getAspectRatio(){
        return (((float)getWidth())/((float)getHeight()));
    }
    protected abstract int getWidth();
    protected abstract int getHeight();
    /**由子类实现，弹窗距离开始部分的起始距离。
     * 如果是竖屏，这个距离是弹窗左边距离屏幕左边的距离；
     * 如果是横屏，这个距离是弹窗上边距离屏幕顶部的距离*/
    protected abstract int getStartInDP();
}
