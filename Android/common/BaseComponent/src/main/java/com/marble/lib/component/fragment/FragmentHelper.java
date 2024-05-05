package com.marble.lib.component.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mar.lib.util.ReflectUtil;
import com.mar.lib.util.StrUtils;
import com.marble.lib.component.dialog.BaseDialogFragment;

public class FragmentHelper {
    public static <T extends BaseDialogFragment> T showDialogFragment(
            Class<T> cls,FragmentActivity a, Bundle data){
        return showDialogFragment(cls,a,data,cls.getSimpleName());
    }

    public static <T extends BaseDialogFragment> T showDialogFragment(
            Class<T> cls,FragmentActivity a, Bundle data,String tag){
        T newFragment = ReflectUtil.newInstanse(cls,data);
        showDialogFragment(a,newFragment,tag);
        return newFragment;
    }

    public static void showDialogFragment(FragmentActivity a,DialogFragment df){
        if(df==null)
            return;
        showDialogFragment(a,df,df.getClass().getSimpleName());
    }

    public static void showDialogFragment(FragmentActivity a,
                              DialogFragment df, String tag){
        if(a==null || df==null)
            return;
        FragmentManager fm = a.getSupportFragmentManager();
        showNewFragment(fm,df,tag);
    }

    public static void showFragmentNoneAnchorID(FragmentManager fm,
            FragmentBuilder<? extends FragmentBuilder<?,?>,
            ? extends Fragment> builder, String tag){
        showFragment(fm,builder,android.R.id.content,tag);
    }

    public static void showNewFragment(FragmentManager fragmentManager,
                Fragment f, String tag){
        showNewFragment(fragmentManager,0,f,tag);
    }

    public static void showNewFragment(FragmentManager fragmentManager,
                int containerViewId,Fragment f, String tag){
        if(fragmentManager==null || f==null)
            return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null) {
            if(prev instanceof DialogFragment){
                ((DialogFragment)prev).dismiss();
            }else {
                ft.remove(prev);
            }
        }
        if(f instanceof DialogFragment){
            ((DialogFragment)f).show(fragmentManager,tag);
        }else {
            ft.add(containerViewId,f,tag);
            ft.commit();
            ft.show(f);
        }
    }

    public static void showFragment(FragmentManager fragmentManager,
                FragmentBuilder<? extends FragmentBuilder<?,?>,
                 ? extends Fragment> builder, String tag){
        showFragment(fragmentManager,builder,0,tag);
    }

    public static void showFragment(FragmentManager fragmentManager,
                FragmentBuilder<? extends FragmentBuilder<?,?>,
                        ? extends Fragment> builder,
                int containViewId,String tag){
        if(fragmentManager==null)
            return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev == null) {
            if(builder==null)
                return;
            prev = builder.build();
            if(prev instanceof DialogFragment){
                DialogFragment dialogFragment = (DialogFragment) prev;
                dialogFragment.show(fragmentManager,tag);
            }else {
                ft.add(containViewId,prev, tag);
                ft.commit();
                ft.show(prev);
            }
        }else{
            if(prev instanceof DialogFragment){
                DialogFragment dialogFragment = (DialogFragment) prev;
                dialogFragment.dismiss();
                dialogFragment = (DialogFragment)builder.build();
                dialogFragment.show(fragmentManager,tag);
            }else {
                ft.commit();
                ft.show(prev);
            }
        }
    }

    public static boolean removeFragment(FragmentActivity a,String tag){
        if(a==null || StrUtils.isEmpty(tag))
            return false;
        return removeFragment(a.getSupportFragmentManager(),tag);
    }

    public static boolean removeFragment(FragmentActivity a,Fragment fragment){
        if(a==null ||  fragment==null)
            return false;
        return removeFragment(a.getSupportFragmentManager(),fragment);
    }


    public static boolean removeFragment(FragmentManager fm,String tag){
        if(fm==null || StrUtils.isEmpty(tag))
            return false;
        return removeFragment(fm,fm.findFragmentByTag(tag));
    }

    public static boolean removeFragment(FragmentManager fm,Fragment fragment){
        if(fm==null ||  fragment==null)
            return false;
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(fragment);
        ft.commit();
        return true;
    }
}