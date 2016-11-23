package gg.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GG on 2016/11/21.
 * Email:gu.yuepeng@foxmail.com
 */

public class DateUtils {

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getMMdd_Now() {
        return getMMdd(0, 0, 0);
    }

    public static String getMMdd(int year, int month, int day) {
        return getMMdd(year, month, day, 0);
    }

    /**
     * 获取以指定年月日为基础向前或向后推几天的日期
     *
     * @param year
     * @param month
     * @param day
     * @param daysLater
     * @return
     */
    public static String getMMdd(int year, int month, int day, int daysLater) {
        Calendar calendar = Calendar.getInstance();
        if (year != 0 && month != 0 && day != 0)
            calendar.set(year, month - 1, day);
        //向前或向后推daysLater天数
        calendar.add(Calendar.DAY_OF_MONTH, daysLater);

        Date mDate = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        String str = dateFormat.format(mDate);
        return str;
    }
}
