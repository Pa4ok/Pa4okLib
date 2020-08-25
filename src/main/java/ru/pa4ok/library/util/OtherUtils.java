package ru.pa4ok.library.util;

import java.io.IOException;
import java.util.Properties;

public class OtherUtils
{
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
