package ru.bigtows.util;

/**
 * Created by bigtows on 19/03/2017.
 */
public class Debug {
    private static final boolean DEBUG_MODE = true;
    
    public static void log(String message) {
        if (DEBUG_MODE) {
            System.out.println(message);
        }
    }

}
