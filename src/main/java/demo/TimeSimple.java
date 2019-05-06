package demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSimple {

    public final static String YYYY_MM_DD = "yyyy-MM-dd";
    public static void main(String[] args) throws ParseException {
        String hitEndDateTime = TimeSimple.parse("2019-04-26", YYYY_MM_DD) + " 23:59:59";
        System.out.println(hitEndDateTime);
    }

    /**
     * 日期解析－将<code>String<	类型的日期解析为<code>Date</code>型
     *
     * @param strDate 日期字串
     * @param pattern 格式化字串
     * @return 返回类型 Date 一个被格式化了的<code>Date</code>日期
     * @throws ParseException
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        return getFormatter(pattern).parse(strDate);
    }

    /**
     * 将日期类型转换为simpleDateFormat类型
     *
     * @param parttern 要转换的日期类型
     * @return 返回类型 SimpleDateFormat 返回一个SimpleDateFormat类型的日期字符串
     */
    private static SimpleDateFormat getFormatter(String parttern) {
        return new SimpleDateFormat(parttern);
    }
}
