package com.mar.lib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中国电信号段 133、149、153、173、177、180、181、189、199
 * 中国联通号段 130、131、132、145、155、156、166、175、176、185、186
 * 中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
 * 其他号段
 * 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
 * 虚拟运营商
 * 电信：1700、1701、1702
 * 移动：1703、1705、1706
 * 联通：1704、1707、1708、1709、171
 * 卫星通信：1349
 */
public class PhoneNumUtil {
    /**
     * 手机号验证
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    /**
     * 电话号码验证
     * @param str
     * @return 验证通过返回true
     */
    public static boolean isPhone(final String str) {
        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");     // 验证没有区号的
        if (str.length() > 9) {
            m = p1.matcher(str);
            b = m.matches();
        } else {
            m = p2.matcher(str);
            b = m.matches();
        }
        return b;
    }
}