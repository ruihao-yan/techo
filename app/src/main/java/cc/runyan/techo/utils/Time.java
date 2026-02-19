package cc.runyan.techo.utils;

import java.util.Calendar;

public class Time {
    public static int getCurYear() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.YEAR);
    }

    public static int getCurMonth() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.MONTH) + 1;
    }

    public static int getMonthDay() {
        Calendar instance = Calendar.getInstance();
        return instance.get(Calendar.DAY_OF_MONTH);
    }
}
