package ru.pa4ok.library.util;

import java.io.IOException;
import java.util.Properties;

public class OtherUtils
{
    public static final long MILL_SECOND = 1000L;
    public static final long MILL_MINUTE = MILL_SECOND * 60;
    public static final long MILL_HOUR = MILL_MINUTE * 60;
    public static final long MILL_DAY = MILL_HOUR * 24;
    public static final long MILL_WEEK = MILL_DAY * 7;

    public static void safeSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Properties loadProperties(String name)
    {
        Properties props = new Properties();
        try {
            props.load(OtherUtils.class.getClassLoader().getResourceAsStream(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;

    }

    /**
     * Add some checks later
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProperty(Properties props, String property, T base)
    {
        if(props != null && props.contains(property)) {
            System.out.println(property + ":" + props.getProperty(property));
            return (T)props.getProperty(property);
        }
        System.out.println("base:" + property + ":" + base);
        return base;
    }
}
