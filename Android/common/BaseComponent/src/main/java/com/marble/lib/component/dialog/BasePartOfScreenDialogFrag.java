package com.marble.lib.component.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.mar.lib.util.DisPlayUtil;
import com.mar.lib.util.StrUtils;
import com.marble.lib.component.R;

public class BasePartOfScreenDialogFrag extends BaseDialogFragment implements View.OnClickListener {
    private TextView titleTv;
    private TextView contentTv;
    private MaterialButton cancelBtn;
    private MaterialButton confirmBtn;

    private boolean noneWindowAnim = false;
    private String title;
    private String content;
    private String cancelBtnText;
    private String confirmBtnText;

    public BasePartOfScreenDialogFrag(String title, String content,
                                      String cancelBtnText, String confirmBtnText) {
        this.title = title;
        this.content = content;
        this.cancelBtnText = cancelBtnText;
        this.confirmBtnText = confirmBtnText;
    }

    private View.OnClickListener confirmButtonClickListener;
    public BasePartOfScreenDialogFrag setConfirmButtonClickListener(
            View.OnClickListener confirmButtonClickListener) {
        this.confirmButtonClickListener = confirmButtonClickListener;
        return this;
    }

    private View.OnClickListener cancelButtonClickListener;
    public BasePartOfScreenDialogFrag setCancelButtonClickListener(
            View.OnClickListener cancelButtonClickListener) {
        this.cancelButtonClickListener = cancelButtonClickListener;
        return this;
    }

    public BasePartOfScreenDialogFrag noneWindowAnim() {
        this.noneWindowAnim = true;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, getThemeStyle());

        setCancelable(false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = getDialogWindowAnim();
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.base_part_of_screen_dialog_frag,null);
        titleTv = root.findViewById(R.id.title);
        if(!StrUtils.isEmpty(title)){
            titleTv.setText(title);
        }
        contentTv = root.findViewById(R.id.content);
        if(!StrUtils.isEmpty(content)){
            contentTv.setText(content);
        }
        cancelBtn = root.findViewById(R.id.cancel_button);
        boolean hasCancel = false;
        if(!StrUtils.isEmpty(cancelBtnText)){
            cancelBtn.setText(cancelBtnText);
            cancelBtn.setOnClickListener(this);
            hasCancel = true;
        }
        cancelBtn.setVisibility(hasCancel?View.VISIBLE:View.GONE);
        confirmBtn = root.findViewById(R.id.confirm_button);
        boolean hasConfirm = false;
        if(!StrUtils.isEmpty(confirmBtnText)){
            confirmBtn.setText(confirmBtnText);
            confirmBtn.setOnClickListener(this);
            hasConfirm = true;
        }
        confirmBtn.setVisibility(hasConfirm?View.VISIBLE:View.GONE);
        if(hasCancel && !hasConfirm){
            ConstraintLayout.LayoutParams lp =
                    (ConstraintLayout.LayoutParams) cancelBtn.getLayoutParams();
            lp.width = 0;
            lp.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.setMarginEnd(DisPlayUtil.dip2px(getContext(),20));
        }else if(!hasCancel && hasConfirm){
            ConstraintLayout.LayoutParams lp =
                    (ConstraintLayout.LayoutParams) confirmBtn.getLayoutParams();
            lp.width = 0;
            lp.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            lp.setMarginStart(DisPlayUtil.dip2px(getContext(),20));
        }
        return root;
    }

    protected int getThemeStyle(){
        return R.style.BasePartOfScreenDialogFragStyle;
    }

    protected int getDialogWindowAnim(){
        return noneWindowAnim? 0:R.style.CenterInOut;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(cancelBtn.getId() == id){
            if(cancelButtonClickListener!=null)
                cancelButtonClickListener.onClick(v);
            dismiss();
        }else if(confirmBtn.getId() == id){
            if(confirmButtonClickListener!=null)
                confirmButtonClickListener.onClick(v);
            dismiss();
        }
    }
}
