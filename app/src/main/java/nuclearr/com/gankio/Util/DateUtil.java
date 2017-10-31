package nuclearr.com.gankio.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by torri on 2017/10/26.
 */

public final class DateUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    public static boolean isToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTime(date);
        return day == calendar.get(Calendar.DAY_OF_MONTH) &&
                month == calendar.get(Calendar.MONTH) &&
                year == calendar.get(Calendar.YEAR);
    }

    public static String timeStamp2DateTime(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDateTime(date);
    }

    public static String timeStamp2Date(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        Date date = calendar.getTime();
        return formatDate(date);
    }

    /**
     * @param date yyyy-MM-dd
     * @return Date
     */
    public static Date parseDate(String date) {
        Date mDate = null;
        try {
            mDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    /**
     * @param dateTime yyyy-MM-dd HH-mm-ss
     * @return
     */
    public static Date parseDateTime(String dateTime) {
        Date mDate = null;
        try {
            mDate = dateTimeFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    /**
     * @param dateTime yyyy-MM-dd HH-mm-ss
     * @return
     */
    public static String getFriendlyTime(String dateTime) {
        Date time = parseDateTime(dateTime.replace(":", "-"));
        if (time == null)
            return "Parse Error.";
        String result;
        Calendar calendar = Calendar.getInstance();
        String today = formatDate(calendar.getTime());

        if (today.equals(formatDate(time))) {
            long hourDiff = (calendar.getTimeInMillis() - time.getTime()) / 3600000;
            if (hourDiff == 0) {
                result = Math.max((calendar.getTimeInMillis() - time.getTime()) / 60000, 1) + " min(s) ago";
            } else {
                result = hourDiff + " hour(s) ago";
            }
            return result;
        }

        long dayDiff = (calendar.getTimeInMillis() - time.getTime()) / 86400000;
        if (dayDiff == 0) {
            long hourDiff = (calendar.getTimeInMillis() - time.getTime()) / 3600000;
            if (hourDiff == 0) {
                result = Math.max((calendar.getTimeInMillis() - time.getTime()) / 60000, 1) + " min(s) ago";
            } else {
                result = hourDiff + " hour(s) ago";
            }
        } else if (dayDiff <= 7) {
            result = dayDiff + " days ago";
        } else {
            result = formatDate(time);
        }
        return result;
    }
}
