package com.marble.lib.component.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseFragment extends Fragment implements Handler.Callback,View.OnClickListener {
    protected Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    protected int id = hashCode();
    protected Handler updateUIHandler;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        logger.debug(id+":onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logger.debug(id+":onCreate");
        releaseHandler();
        updateUIHandler = new Handler(this);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        logger.debug(id+":onActivityCreated");
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        logger.debug(id+":onSaveInstanceState");
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
        releaseHandler();
        super.onDestroy();
        logger.debug(id+":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        logger.debug(id+":onDetach");
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        return false;
    }

    private void releaseHandler(){
        if(updateUIHandler!=null){
            updateUIHandler.removeCallbacksAndMessages(null);
            updateUIHandler = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        logger.debug(id+":销毁");
        super.finalize();
    }

    @Override
    public void onClick(View v) {
        clickView(v.getId());
    }

    protected void clickView(int viewId){

    }
}