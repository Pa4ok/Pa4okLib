package ru.pa4ok.library.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.List;

public class Logger
{
    private static final Logger instance = new Logger();
    private java.util.logging.Logger logger;

    public Logger()
    {
        this.logger = java.util.logging.Logger.getLogger(Logger.class.getName());

        String dir = "/logs/";
        File f = new File(dir);
        if(!f.exists()) {
            f.mkdirs();
        }

        try {
            FileHandler handler = new FileHandler(dir + (new SimpleDateFormat("dd-MM-yyyy")).format(new Date()) + ".log", true);
            handler.setFormatter(new LogsFormatter());
            this.logger.addHandler(handler);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }

        this.logger.setLevel(Level.INFO);
    }

    //write methods
    private void write(String message, Level logLevel)
    {
        this.logger.log(logLevel, message);
    }

    private void write(Exception e, Level logLevel)
    {
        StringBuilder s = new StringBuilder();
        s.append(e.getMessage())
                .append("\n");
        for(int i=e.getStackTrace().length-1; i>=0; i--)
        {
            s.append("    ")
                    .append(e.getStackTrace()[i])
                    .append("\n");
        }
        this.logger.log(logLevel, s.toString());
    }

    private void write(List<String> list, Level logLevel)
    {
        StringBuilder s = new StringBuilder();
        for(String m : list)
        {
            s.append(m)
                    .append("\n");
        }
        this.logger.log(logLevel, s.toString());
    }

    private void write(String[] arr, Level logLevel)
    {
        StringBuilder s = new StringBuilder();
        for(String m : arr)
        {
            s.append(m)
                    .append("\n");
        }
        this.logger.log(logLevel, s.toString());
    }
    //

    //log
    public void log(String message)
    {
        this.write(message, Level.INFO);
    }
    public void log(Exception e)
    {
        this.write(e, Level.INFO);
    }

    public void log(List<String> list)
    {
        this.write(list, Level.INFO);
    }

    public void log(String[] arr)
    {
        this.write(arr, Level.INFO);
    }
    //

    //warn
    public void warn(String message)
    {
        this.write(message, Level.WARNING);
    }

    public void warn(Exception e)
    {
        this.write(e, Level.WARNING);
    }

    public void warn(List<String> list)
    {
        this.write(list, Level.WARNING);
    }

    public void warn(String[] arr)
    {
        this.write(arr, Level.WARNING);
    }
    //

    //error
    public void error(String message)
    {
        this.write(message, Level.SEVERE);
    }

    public void error(Exception e)
    {
        this.write(e, Level.SEVERE);
    }

    public void error(List<String> list)
    {
        this.write(list, Level.SEVERE);
    }

    public void error(String[] arr)
    {
        this.write(arr, Level.SEVERE);
    }
    //

    public static Logger getInstance()
    {
        return instance;
    }
}

class LogsFormatter extends Formatter
{
    @Override
    public String format(LogRecord record)
    {
        StringBuilder s = new StringBuilder();
        s.append("[")
                .append((new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss")).format(new Date(record.getMillis())))
                .append("][")
                .append(record.getLevel().getName())
                .append("] ")
                .append(record.getMessage())
                .append("\n");

        return s.toString();
    }
}