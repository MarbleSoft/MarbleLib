package com.mar.lib.util;

import android.app.Activity;
import android.content.Context;
import android.text.ClipboardManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;


/**
 * 描述：键盘相关工具类
 * 例如：显示隐藏软键盘等
 * @author luyao
 * @date 2017/12/19 17:22
 */
public class KeyboardUtils {

    /**
     * 如果输入法在窗口上已经显示，则隐藏，如果隐藏，则显示输入法到窗口上
     */
    public static void showKeyboard(Context context) {
        try {
            Activity activity = (Activity) context;
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐式显示输入窗口，非用户直接要求。窗口可能不显示。
     */
    public static void showKeyboard(Context context, EditText text) {
        if (text != null) {
            text.requestFocus();
            try {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Context context) {
        try {
            Activity activity = (Activity) context;
            hideKeyboard(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Activity activity) {
        try {
            if (activity != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive() && activity.getCurrentFocus() != null) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Context context, EditText text) {
        if (context != null && text != null) {
            try {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            text.clearFocus();
        }
    }

    /**
     * 显示软键盘
     */
    public static void showInputMethod(@NonNull View view) {
        try {
            ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideInputMethod(@NonNull View view) {
        try {
            ((InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * 复制文字到剪切板
    * */
    public static void copyText(Context context, String text) {
        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
        myClipboard.setText(text);
    }
}
