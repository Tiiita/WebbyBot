package de.tiiita.webbybot.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created on März 19, 2023 | 18:39:44
 * (●'◡'●)
 */
public class TimeUtil {

    /**
     * Get A String with a nice looking time pattern.
     * @param pattern the pattern you want to get back! (HH:mm: a = 00:00 AM / PM)
     * @return System.currentTimeMillis() converted in the time pattern you have given.
     */
    public static String getTimeInPattern(String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(System.currentTimeMillis());
        return dateFormat.format(date);
    }
}
