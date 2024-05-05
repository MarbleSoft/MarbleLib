package com.marble.lib.component.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseDialogFragment extends DialogFragment implements View.OnClickListener{
    protected Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    protected int id = hashCode();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logger.debug(id+":onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.debug(id+":onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        logger.debug(id+":onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logger.debug(id+":onViewCreated");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        logger.debug(id+":onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        logger.debug(id+":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        logger.debug(id+":onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        logger.debug(id+":onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        logger.debug(id+":onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logger.debug(id+":onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logger.debug(id+":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logger.debug(id+":onDetach");
    }

    @Override
    protected void finalize() throws Throwable {
        logger.debug(id+":销毁");
        super.finalize();
    }

    @Override
    public void onClick(View v) {

    }
}