package com.marble.lib.component.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.marble.lib.component.fragment.FragmentBuilder;
import com.marble.lib.component.fragment.FragmentHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WithHandlerActivity extends AppCompatActivity implements Handler.Callback {
    protected int id = hashCode();
    protected Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());
    protected Handler updateUIHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(drawStatusBar()){
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        logger.debug(id+":onCreate");
        releaseHandler();
        updateUIHandler = new Handler(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logger.debug(id+":onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logger.debug(id+":onStart");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        logger.debug(id+":onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.debug(id+":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.debug(id+":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logger.debug(id+":onStop");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        logger.debug(id+":onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logger.debug(id+":onDestroy");
        releaseHandler();
    }

    @Override
    protected void finalize() throws Throwable {
        logger.debug(id+":销毁");
        super.finalize();
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
//        if(OnBackPressedOneTime == msg.what){
//            backPressedLastTime = -1;
//            return true;
//        }
        return false;
    }

    public void sendMsg(Message msg){
        updateUIHandler.sendMessage(msg);
    }

    public void sendMsg(int what,int arg1,int arg2,Object obj){
        Message msg = updateUIHandler.obtainMessage(what);
        msg.arg1 = arg1;
        msg.arg2 = arg2;
        msg.obj = obj;
        msg.sendToTarget();
    }

    public void sendMsg(int what,int arg1){
        Message msg = updateUIHandler.obtainMessage(what);
        msg.arg1 = arg1;
        msg.sendToTarget();
    }

    public void sendMsg(int what,Object obj){
        Message msg = updateUIHandler.obtainMessage(what);
        msg.obj = obj;
        msg.sendToTarget();
    }

    public void sendMsg(int what){
        updateUIHandler.sendEmptyMessage(what);
    }

    private void releaseHandler(){
        if(updateUIHandler!=null){
            updateUIHandler.removeCallbacksAndMessages(null);
            updateUIHandler = null;
        }
    }

    public Handler getHandler() {
        return updateUIHandler;
    }


    public void showFragmentNoneAnchorID(FragmentBuilder<? extends FragmentBuilder<?,?>,
            ? extends Fragment> builder, String tag){
        FragmentHelper.showFragmentNoneAnchorID(getSupportFragmentManager(),
                builder,tag);
    }

    public void showDialogFragment(DialogFragment df, String key){
        FragmentHelper.showDialogFragment(this,df,key);
    }

    public boolean removeFragment(String key){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(key);
        if(fragment!=null) {
            ft.remove(fragment);
            ft.commit();
            return true;
        }
        return false;
    }

    public boolean removeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(fragment!=null) {
            ft.remove(fragment);
            ft.commit();
            return true;
        }
        return false;
    }

    public boolean drawStatusBar(){
        return false;
    }

    protected long backPressedLastTime = -1L;
    @Override
    public void onBackPressed() {
        if(shouldConfirmExit()){
            long pressedTime = System.currentTimeMillis();
            if(pressedTime - backPressedLastTime>3000){
                Toast.makeText(this,"3秒内再次点击返回按键退出",Toast.LENGTH_LONG).show();
                backPressedLastTime = pressedTime;
//            updateUIHandler.sendEmptyMessageDelayed(OnBackPressedOneTime,3000);
            }else{
                finish();
            }
        }else
            super.onBackPressed();
    }

    protected boolean shouldConfirmExit(){
        return true;
    }
}