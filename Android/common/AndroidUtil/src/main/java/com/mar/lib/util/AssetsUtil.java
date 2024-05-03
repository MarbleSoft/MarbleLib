package com.mar.lib.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetsUtil {
    public static String getFromAssets(Context ctx,String fileName){
        InputStream inputStream = null;
        try {
            AssetManager assetManager = ctx.getResources().getAssets();
            inputStream = assetManager.open(fileName);
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
//            inputStream.close();
//            inputReader.close();
//            bufReader.close();
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                    inputStream=null;
                }
            }
        }
    }
}
