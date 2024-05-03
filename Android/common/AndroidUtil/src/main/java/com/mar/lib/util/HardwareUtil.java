package com.mar.lib.util;

import java.io.File;
import java.util.regex.Pattern;

public class HardwareUtil {
    /**
     * 获取CPU核心数目，不需要root权限。
     * 通过读取下面路径以"cpu*"开始的文件数目，如
     *      /sys/devices/system/cpu/cpu0 ----------Cpu1
     *      /sys/devices/system/cpu/cpu1 ----------Cpu2
     * 则有CPU是两核
     */
    public static int getCPUCoresNum() {
        //api必须大于10 android.os.Build.VERSION_CODES.GINGERBREAD_MR1
        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles((pathname -> Pattern.matches(
                    "cpu[0-9]+", pathname.getName())));
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch(Exception e) {
            //Print exception
            e.printStackTrace();
            //Default to return 1 core
            return 1;
        }
    }


}
