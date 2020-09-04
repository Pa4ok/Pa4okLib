package ru.pa4ok.library.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.RootLoggerComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class Log4jUtil
{
    public static void configureLogging()
    {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        RootLoggerComponentBuilder rootLogger = builder.newRootLogger(Level.ALL);
        rootLogger.add(builder.newAppenderRef("stdout"));
        //rootLogger.add(builder.newAppenderRef("log"));
        rootLogger.add(builder.newAppenderRef("rolling"));
        builder.add(rootLogger);

        ComponentBuilder policy = builder.newComponent("Policies");
        policy.addComponent(builder.newComponent("CronTriggeringPolicy").addAttribute("schedule", "0 0 0 * * ?"));
        policy.addComponent(builder.newComponent("SizeBasedTriggeringPolicy").addAttribute("size", "100M"));

        LayoutComponentBuilder layout = builder.newLayout("PatternLayout");
        layout.addAttribute("pattern", "%d{[dd.MM.yy HH:mm:ss]} [%-5p] [%c{1}:%L]: %m%n");

        AppenderComponentBuilder console = builder.newAppender("stdout", "Console");
        console.add(layout);
        builder.add(console);

        /*AppenderComponentBuilder file = builder.newAppender("log", "File");
        file.addAttribute("fileName", "logs/application.log");
        file.add(layout);
        builder.add(file);*/

        AppenderComponentBuilder rolling = builder.newAppender("rolling", "RollingFile");
        rolling.addAttribute("fileName", "logs/application.log");
        rolling.addAttribute("filePattern", "logs/%d{MM-dd-yy}.log.gz");
        rolling.addComponent(policy);
        rolling.add(layout);
        builder.add(rolling);

        Configurator.initialize(builder.build());
    }
}
