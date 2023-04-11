package net.propvp.webbybot.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * Created on April 11, 2023 | 22:14:12
 * (●'◡'●)
 */
public enum Logger {

    //LogTypes
    DEBUG,
    INFO,
    //Error is special! Don't change it if you want error logging features. It will check if the Type is "ERROR" and prints a special red message!
    ERROR;



    public static void log(Logger type, String message) {
        Logger error = Logger.valueOf("ERROR");
        if (type.equals(error)) {
            System.err.println(message);
        } else normalLog(type, message);


    }

    private static void normalLog(Logger type, String message) {
        System.out.println(type.name() + " -> " + message);
    }
}
