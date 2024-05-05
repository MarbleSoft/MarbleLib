package com.marble.lib.component.dialog;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class BaseFulllScreenDialogFrag extends BaseDialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Dialog);
    }
}
