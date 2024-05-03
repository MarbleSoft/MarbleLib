package com.mar.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {
    /**
     * 请求一组权限.
     * @param permissionsAndTips 该组参数必须为偶数且一一对应。
     *                           比如，请求读和写权限，参数应为：
     *                           [读权限,写权限,读权限无法获取的提示,写权限无法获取的提示]
     * @return 0-----已具有所有权限<br>
     *         -1-----正常，执行权限申请<br>
     *         -2-----参数permissionsAndTips不符合规则<br>
     *         -3-----Activity状态异常直接返回<br>
     *         >0-----需要申请的第i项权限无法申请。会进行对应第(2i+1)个toast提示
     */
    public static int requestPermissions(Activity main,int requestCode,
                                          String... permissionsAndTips) {
        if (main == null || main.isFinishing() || main.isDestroyed())
            return -3;
        if (permissionsAndTips == null || permissionsAndTips.length < 2
                || permissionsAndTips.length % 2 != 0)
            return -2;
        int permissionNum = permissionsAndTips.length / 2;
        List<String> requestPermissions = new ArrayList<>(permissionNum);
        for (int i = 0; i < permissionNum; i++) {
            String permission = permissionsAndTips[i];
            if (ContextCompat.checkSelfPermission(
                    main.getApplicationContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        main, permission)) {
//                    // 不容许请求该权限，直接返回
//                    Toast.makeText(main.getApplicationContext(),
//                            permissionsAndTips[i  + permissionNum], Toast.LENGTH_LONG).show();
//                    return i+1;
//                } else {
                    //需申请该权限
                    requestPermissions.add(permission);
//                }
            }
        }
        if (requestPermissions.size() > 0){
            ActivityCompat.requestPermissions(main, requestPermissions.toArray(
                    new String[0]), requestCode);
            return -1;
        }else
            return 0;
    }

    /**
     * 检查一组权限.
     * @param permissions 待检查的权限名称数组
     * @return 0------已具有所有权限<br>
     *         -1-----调用错误<br>
     *         >0-----权限名称数组中第i项权限确实。注意，i之后的权限为检测
     */
    public static int checkPermissions(Context ctx,String... permissions) {
        if(permissions==null || permissions.length<1)
            return -1;
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(ctx, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                return i+1;
            }
        }
        return 0;
    }
}
