package com.mar.lib.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;

/**
 * 存放View相关的工具
 */
public class ViewUtils {
    public static final long DEFAULT_INTERVAL = 400;

    private static volatile long lastClickTime;

    /**
     * 防止按钮重复点击处理类
     *
     * @return
     */
    public static synchronized boolean isFastDoubleClick() {
        return isFastDoubleClick(DEFAULT_INTERVAL);
    }

    /**
     * 防止按钮重复点击处理类
     * @param interval: 连续点击的间隔不超过interval毫秒
     * @return
     */
    public static synchronized boolean isFastDoubleClick(long interval) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 <= timeD && timeD < interval) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    //(x,y)是否在view的区域内
    public static boolean isTouchInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left
                && x <= right;
    }

    /**
     * 计算指定View的显示百分比
     * @param view View
     * @return 百分比，如80%，返回值为80
     */
    public static int getViewLocalVisiblePercent(View view) {
        if (view == null) {
            return 0;
        }
        Rect rect = new Rect();
        boolean visible = view.getLocalVisibleRect(rect);
        if (!visible) {
            return 0;
        }
        int percents = 100;
        int viewHeight = view.getHeight();
        if (rect.top > 0) {
            percents = (viewHeight - rect.top) * 100 / viewHeight;
        } else if (rect.bottom > 0 && rect.bottom < viewHeight) {
            percents = rect.bottom * 100 / viewHeight;
        }
        return percents;
    }

    /**
     * 从view获取activity
     * @param view view
     * @return activity
     */
    public static Activity getActivityFromView(View view) {
        if(view == null) {
            return null;
        }

        return ContextUtils.getActivityFromContext(view.getContext());
    }

    /*
    public static ViewGroup.LayoutParams generateLayoutParams(ViewGroup view,int w,int h){
        try {
            Method method = MethodUtil.getMethod(view.getClass(),
                    "generateLayoutParams", ViewGroup.LayoutParams.class);
            method.setAccessible(true);
            return (ViewGroup.LayoutParams) method.invoke(view,new ViewGroup.LayoutParams(w,h));
        } catch (Exception e) {
            e.printStackTrace();
            return getLayoutParams(view, w, h);
        }
    }

    public static ViewGroup.LayoutParams getLayoutParams(ViewGroup view,int w,int h){
        if(view instanceof RelativeLayout){
            return new RelativeLayout.LayoutParams(w,h);
        }else if(view instanceof FrameLayout){
            return new FrameLayout.LayoutParams(w,h);
        }else if(view instanceof TableLayout){
            return new TableLayout.LayoutParams(w,h);
        }else if(view instanceof LinearLayout){
            return new LinearLayout.LayoutParams(w,h);
        }else if(view instanceof GridLayout){
            return new GridLayout.LayoutParams();
        }else if(view instanceof AbsListView){
            return new AbsListView.LayoutParams(w,h);
        }else if(view instanceof ConstraintLayout){
            return new ConstraintLayout.LayoutParams(w,h);
        }else if(view instanceof androidx.viewpager.widget.ViewPager){
            return new androidx.viewpager.widget.ViewPager.LayoutParams();
        }else{
            return null;
        }
    }*/
}
