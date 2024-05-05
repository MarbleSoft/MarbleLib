package com.marble.lib.component.dialog;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mar.lib.util.DisPlayUtil;
import com.mar.lib.util.StrUtils;
import com.marble.lib.component.R;

public class MultButtonsDialogFrag extends BaseDialogFragment implements View.OnClickListener {
    private TextView titleTv;
    private TextView contentTv;
    private Button[] btns;

    private boolean noneWindowAnim = false;
    private int windowAnim = 0;
    private String title;
    private String content;
    private String[] btnNames;
    private View.OnClickListener[] btnListeners;

    public MultButtonsDialogFrag(String title, String content, String... btnNames) {
        this.title = title;
        this.content = content;
        this.btnNames = btnNames;
        if(btnNames!=null && btnNames.length>0){
            btnListeners = new View.OnClickListener[btnNames.length];
        }
    }

    public MultButtonsDialogFrag addButtonListener(int which,
            View.OnClickListener buttonClickListener) {
        btnListeners[which] = buttonClickListener;
        return this;
    }

    public MultButtonsDialogFrag noneWindowAnim() {
        this.noneWindowAnim = true;
        return this;
    }

    public MultButtonsDialogFrag setWindowAnim(int windowAnim) {
        this.windowAnim = windowAnim;
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
        View root = inflater.inflate(R.layout.multi_buttons_dialog_frag,null);
        if(!StrUtils.isEmpty(title)){
            titleTv = root.findViewById(R.id.title);
            titleTv.setText(title);
        }
        if(!StrUtils.isEmpty(content)){
            contentTv = root.findViewById(R.id.content);
            contentTv.setText(content);
        }
        LinearLayout contentContainer = root.findViewById(R.id.contentContainer);
        if(btnNames != null) {
            int length = btnNames.length;
            if (length >0) {
                btns = new Button[length];
                for (int i = 0; i < length; i++) {
                    btns[i] = getButton(btnNames[i]);
                    btns[i].setOnClickListener(this);
                    contentContainer.addView(btns[i]);
                }
            }
        }

        return root;
    }

    protected Button getButton(String text){
        int dp10 = DisPlayUtil.dip2px(getContext(),10);
        Button btn = new Button(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                DisPlayUtil.sp2px(getContext(),60)
        );
        lp.setMarginStart(2*dp10);
        lp.setMarginEnd(2*dp10);
        lp.setMargins(2*dp10,dp10,2*dp10,dp10);
        btn.setLayoutParams(lp);
        btn.setMinWidth(DisPlayUtil.dip2px(getContext(),11*dp10));
        btn.setText(text);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
//        btn.setTextColor();
//        btn.setCornerRadius(2*dp10);
//        btn.setStrokeWidth(dp10/2);
//        btn.setStrokeColorResource(R.color.teal_200);
        btn.setBackground(getBtnBackground());
        return btn;
    }

    protected int getThemeStyle(){
        return R.style.MultButtonsDialogFragStyle;
    }

    protected int getDialogWindowAnim(){
        return noneWindowAnim? 0:(windowAnim>0? windowAnim:R.style.RightInLeftOutAnim);
    }

    @Override
    public void onClick(View v) {
        if(btns!=null && btns.length>0){
            for (int i=0;i<btns.length;i++){
                if(v==btns[i]){
                    dismiss();
                    if(btnListeners[i]!=null)
                        btnListeners[i].onClick(v);
                    break;
                }
            }
        }
    }

    private Drawable btnBackground;
    public MultButtonsDialogFrag setBtnBackground(Drawable btnBackground){
        this.btnBackground = btnBackground;
        return this;
    }

    protected Drawable  getBtnBackground(){
        if(btnBackground!=null)
            return btnBackground;
        return getContext().getDrawable(R.drawable.bg_shape_red_radius_10);
    }
}
