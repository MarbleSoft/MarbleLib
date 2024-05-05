package com.marble.lib.component.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import com.mar.lib.util.DisPlayUtil;
import com.marble.lib.component.R;

public class ProgressDialog extends BaseDialogFragment {
    public static final String PROGRESS_TEXT = "progress_text";
    public static final String CANCEL_ABLE = "cancel_able";
    private String mProgressText;
    protected View mContentView;
    protected boolean cancelOnTouchOutside = false;
    protected boolean cancelAble = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mProgressText = arguments.getString(PROGRESS_TEXT);
        cancelAble = arguments.getBoolean(CANCEL_ABLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.lib_dialog_progress_layout,
                container, false);
        initView();
        //监听返回键
        monitorKeyBoard();
        return mContentView;
    }

    /**
     * dialogFragment默认是点击返回键消失
     * 如果要点击返回键dialog不消失，可以监听下按键。
     */
    protected void monitorKeyBoard() {
        if (!cancelAble) {
            this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    // pass on to be processed as normal
                    return keyCode == KeyEvent.KEYCODE_BACK;
                }
            });
        }
    }

    protected void initView() {
        ((TextView) mContentView.findViewById(R.id.progress_textview))
                .setText(mProgressText == null ? "" : mProgressText);
        Activity activity = getActivity();
        if (activity != null) {
            ProgressHelper mProgressHelper = new ProgressHelper(activity);
            mProgressHelper.setProgressWheel((ProgressWheel) mContentView
                    .findViewById(R.id.progressWheel));
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.lib_alert_dialog2);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置宽度
        getDialog().getWindow().setLayout(DisPlayUtil.dip2px(getContext(),
                240f), WindowManager.LayoutParams.WRAP_CONTENT);
    }

}
