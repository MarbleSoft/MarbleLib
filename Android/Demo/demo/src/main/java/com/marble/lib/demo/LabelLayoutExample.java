package com.marble.lib.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import com.marble.lib.widget.labelview.LabelLayout;
import com.marble.lib.widget.labelview.LabelAttri;
import com.mar.lib.util.AssetsUtil;
import com.mar.lib.util.DisPlayUtil;
import com.alibaba.fastjson2.JSON;

import java.util.ArrayList;
import java.util.List;

public class LabelLayoutExample extends AppCompatActivity {
    private LabelLayout ll1,ll2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_label_layout);
         ll1 = findViewById(R.id.test_label_layout1);
         ll2 = findViewById(R.id.test_label_layout2);
        LabelLayout ll3 = findViewById(R.id.test_label_layout3);
        LabelLayout ll4 = findViewById(R.id.test_label_layout4);
        final float scale = getResources().getDisplayMetrics().density;
         List<LabelAttri> las3 = new ArrayList<>(4);
         las3.add(new LabelAttri("android", Color.WHITE,scale*20,
                 (int)(scale*20),Color.RED,(int)(scale*4),0xffffa800));
        las3.add(new LabelAttri("iOS", Color.WHITE,scale*20,
                (int)(scale*20),Color.RED,(int)(scale*4),0xffdd8600));
        las3.add(new LabelAttri("Java", Color.WHITE,scale*20,
                (int)(scale*20),Color.RED,(int)(scale*4),0xffbb6400));
        las3.add(new LabelAttri("Go", Color.WHITE,scale*20,
                (int)(scale*20),Color.RED,(int)(scale*4),0xff994200));


        List<LabelAttri> las4 = new ArrayList<>(4);
        las4.add(new LabelAttri("android", Color.WHITE,scale*20,
                (int)(scale*20),Color.RED,(int)(scale*4),0xdd888888));
        las4.add(new LabelAttri("iOS", Color.WHITE,scale*20,
                (int)(scale*20),Color.RED,(int)(scale*4),0xbbaaaaaa));
        las4.add(new LabelAttri("Java", Color.WHITE,scale*20,
                (int)(scale*20),Color.RED,(int)(scale*4),0x99666666));
        las4.add(new LabelAttri("Go", Color.WHITE,scale*20,
                (int)(scale*20),Color.RED,(int)(scale*4),0x77444444));
        ll3.addLabels(las3,las4);
        ll4.addLabels(las3,las4);
        System.out.println(0xee222222);
        System.out.println(0xdd333333);
        System.out.println(0xcc444444);
        System.out.println(0xbb555555);
        System.out.println(0xaa666666);
        System.out.println(0x99777777);
        System.out.println(0x88888888);
        System.out.println(0x77999999);
        System.out.println(0x66aaaaaa);

        System.out.println("--------------------------------");
        System.out.println(0xffff0000);
        System.out.println(0xeeff1111);
        System.out.println(0xbbff2222);
        System.out.println(0xaaff3333);
        System.out.println(0x99ff4444);
        System.out.println(0x88ff5555);
        System.out.println(0x77ff4444);
        System.out.println(0x66ff5555);
         new Thread(()->{
             try {
                 String labelsStr = AssetsUtil.getFromAssets(
                         getApplicationContext(), "make_friends_labells.json");
//                 Labels las = JSON.parseObject(labelsStr.replace(" ",""), Labels.class);
                 List<LabelAttri> las = JSON.parseArray(labelsStr,LabelAttri.class);
                 for (LabelAttri la : las) {
                     la.paddingHorizontal = DisPlayUtil.dip2px(getApplicationContext(), la.paddingHorizontal);
                     la.paddingVertical = DisPlayUtil.dip2px(getApplicationContext(), la.paddingVertical);
                     la.roundRadius = DisPlayUtil.dip2px(getApplicationContext(), la.roundRadius);
                     la.roundStrokeWidth = DisPlayUtil.dip2px(getApplicationContext(), la.roundStrokeWidth);
                     la.textSize = DisPlayUtil.dip2px(getApplicationContext(), la.textSize);
                 }

                 String labelsStr2 = AssetsUtil.getFromAssets(
                         getApplicationContext(), "make_friends_labells_selected.json");
                 List<LabelAttri> las2 = JSON.parseArray(labelsStr2,LabelAttri.class);
                 for (LabelAttri la : las2) {
                     la.paddingHorizontal = DisPlayUtil.dip2px(getApplicationContext(), la.paddingHorizontal);
                     la.paddingVertical = DisPlayUtil.dip2px(getApplicationContext(), la.paddingVertical);
                     la.roundRadius = DisPlayUtil.dip2px(getApplicationContext(), la.roundRadius);
                     la.roundStrokeWidth = DisPlayUtil.dip2px(getApplicationContext(), la.roundStrokeWidth);
                     la.textSize = DisPlayUtil.dip2px(getApplicationContext(), la.textSize);
                 }
                 LabelLayoutExample.this.runOnUiThread(() -> {
                     ll1.addLabels(las, las2);
                     ll2.addLabels(las, las2);
                 });
             }catch (Exception e){
                 e.printStackTrace();
             }
         }).start();
    }

//    static class MyThread extends Thread{
//        @Override
//        public void run() {
//
//        }
//    }
}