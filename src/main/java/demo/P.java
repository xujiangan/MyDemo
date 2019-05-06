package demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class P {
    public static void main(String[] args) {
       /* System.out.println(Md5Util.encodeBySalt("123123","xujanss"));

        System.out.println(Md5Util.encodeByMD5("123123"));*/

        //4297f44b13955235245b2497399d7a93

        // http://localhost:8080/nss-cloud2-auth2/auth/access_token_user

        //System.out.println(getCurrMonthFirstDay());
        if (isNotBlankOrNull("")) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

    }

    public static String getLastMonthFirstDay() {
        // 获取上个月 第一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(c.getTime());
    }

    public static String getCurrMonthLastDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 0);
        return sdf.format(c.getTime());
    }

    public static String getCurrMonthFirstDay() {
        // 获取本月 第一天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return sdf.format(c.getTime());
    }

    public static boolean isNotBlankOrNull(String str) {
        return "".equals(requote(str)) ? false : true;
    }

    public static String requote(String str) {
        if (str == null)
            str = "";
        if ("null".equalsIgnoreCase(str))
            str = "";
        return str;
    }


}