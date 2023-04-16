package de.tiiita.webbybot.util;

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
        sendLog(type.name().equals("ERROR"), type, message);
    }
    public static void logInfo(String message) {
        log(Logger.INFO, message);
    }

    private static void sendLog(boolean error, Logger type, String message) {
        String layout = type.name() + " -> " + message;
        if (error) {
            System.err.println(layout);
        } else System.out.println(layout);
    }
}
