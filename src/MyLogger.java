package src;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public abstract class MyLogger {
    public static enum LogLevel {
        ALL, CONFIG, FINE, FINER, FINEST, INFO, OFF, SEVERE, WARNING, ERROR, DEBUG
    }

    private static Logger logger = Logger.getLogger(MyLogger.class.getName());
    private static FileHandler fh;
    private static boolean initialized = false;

    public static void init() {
        if (initialized)
            return;
        initialized = true;
        try {
            fh = new FileHandler("log.txt", true);
            SimpleFormatter formatter = new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord lr) {
                    Date date = new Date(lr.getMillis());
                    String timestamp = String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL", date);
                    return String.format(
                            "%s [%s] %s.%s\t-\t%s\t-\t%s\n",
                            timestamp,
                            lr.getLevel().getLocalizedName(),
                            lr.getSourceClassName(),
                            lr.getSourceMethodName(),
                            lr.getMessage(),
                            lr.getThrown()
                    );
                }
            };
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            logger.log(Level.INFO, "Logger initialized");
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        if (!initialized)
            init();
        logger.log(Level.INFO, message);
    }

    public static void log(Exception e) {
        if (!initialized)
            init();
        logger.log(Level.SEVERE, e.getMessage(), e);
    }

    public static void log(LogLevel level, String message) {
        if (!initialized)
            init();
        switch (level) {
            case ALL:
                logger.log(Level.ALL, message);
                break;
            case CONFIG:
                logger.log(Level.CONFIG, message);
                break;
            case FINE:
                logger.log(Level.FINE, message);
                break;
            case FINER:
                logger.log(Level.FINER, message);
                break;
            case FINEST:
                logger.log(Level.FINEST, message);
                break;
            case INFO:
                logger.log(Level.INFO, message);
                break;
            case OFF:
                logger.log(Level.OFF, message);
                break;
            case SEVERE:
                logger.log(Level.SEVERE, message);
                break;
            case ERROR:
                logger.log(Level.SEVERE, message);
                break;
            case WARNING:
                logger.log(Level.WARNING, message);
                break;
            default:
                break;
        }
    }

    public static void publish(LogRecord record) {
        if (!initialized)
            init();
        fh.publish(record);
        fh.flush();
    }

    public static void log(int index) {
        if (!initialized)
            init();
        logger.log(Level.INFO, String.valueOf(index));
    }

}
