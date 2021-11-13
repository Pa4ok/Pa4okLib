package ru.pa4ok.library.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.*;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class Log4jUtil
{
    public static void configureLogging(String path, String logName)
    {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.ALL);
        rootLogger.add(builder.newAppenderRef("stdout"));
        //rootLogger.add(builder.newAppenderRef("log"));
        rootLogger.add(builder.newAppenderRef("rolling"));
        builder.add(rootLogger);

        ComponentBuilder policy = builder.newComponent("Policies");
        policy.addComponent(builder.newComponent("TimeBasedTriggeringPolicy").addAttribute("interval", "1").addAttribute("modulate", true));
        policy.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));

        LayoutComponentBuilder layout = builder.newLayout("PatternLayout");
        layout.addAttribute("pattern", "%d{[dd.MM.yy HH:mm:ss]} [%-5p] [%c{1}:%L]: %m%n");

        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        console.add(layout);
        builder.add(console);

        /*AppenderComponentBuilder file = builder.newAppender("log", "File");
        file.addAttribute("fileName", "logs/" + logName + ".log");
        file.add(layout);
        builder.add(file);*/

        AppenderComponentBuilder rolling = builder.newAppender("rolling", "RollingFile");
        rolling.addAttribute("fileName", path + "logs/" + logName + ".log");
        rolling.addAttribute("filePattern", path + "logs/%d{dd-MM-yy}-" + logName + ".log.gz");
        rolling.addComponent(policy);
        rolling.add(layout);
        builder.add(rolling);

        Configurator.initialize(builder.build());
    }

    public static void configureLogging() {
        configureLogging("", "application");
    }
}
