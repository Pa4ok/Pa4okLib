package ru.pa4ok.library.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class PropertyUtil {
    private static final Logger logger = LogManager.getLogger(PropertyUtil.class);

    public static Properties loadFromJar(String path, String title)
    {
        Properties props = new Properties();

        try {
            props.load(OtherUtils.class.getClassLoader().getResourceAsStream((path == null ? "" : path + "/") + title));
        } catch (Exception e) {
            logger.error("Error while loading properties file", e);
        }

        return props;
    }


    public static Properties loadFromFile(String path, String title, boolean checkJar) {
        Properties props = new Properties();
        Path filePath = Paths.get((path == null ? "" : path + "/") + title);

        try {
            if (!Files.exists(filePath)) {
                if (checkJar) {
                    Files.copy(
                            Paths.get(PropertyUtil.class.getClassLoader().getResource(title).toURI()),
                            filePath,
                            StandardCopyOption.COPY_ATTRIBUTES,
                            StandardCopyOption.REPLACE_EXISTING
                    );
                }
            }
            props.load(Files.newBufferedReader(filePath));
        } catch (Exception e) {
            throw new RuntimeException("Error while loading properties file", e);
        }

        return props;
    }

    public static Properties loadFromFile(String path, String title) {
        return loadFromFile(path, title, false);
    }

    public static Properties loadFromFile(String title) {
        return loadFromFile(null, title, false);
    }

    public static Object get(Properties props, String key, Object defaultValue) {
        if (props != null && props.containsKey(key)) {
            if (props.containsKey(key)) {
                String value = props.getProperty(key);
                Class<?> cls = defaultValue != null ? defaultValue.getClass() : null;
                if (cls == int.class || cls == Integer.class) {
                    return Integer.parseInt(value.replaceAll("_", ""));
                }
                if (cls == float.class || cls == Float.class) {
                    return Float.parseFloat(value.replaceAll("_", ""));
                }
                if (cls == double.class || cls == Double.class) {
                    return Double.parseDouble(value.replaceAll("_", ""));
                }
                if (cls == long.class || cls == Long.class) {
                    return Long.parseLong(value.replaceAll("_", ""));
                }
                if (cls == boolean.class || cls == Boolean.class) {
                    return Boolean.parseBoolean(value);
                }
                return value;
            }
        }
        return defaultValue;
    }
}
