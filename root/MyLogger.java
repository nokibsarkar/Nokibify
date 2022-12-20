package root;
import java.io.IOException;
import java.util.logging.*;
public class MyLogger extends FileHandler {
    public static enum LogLevel {
        ALL, CONFIG, FINE, FINER, FINEST, INFO, OFF, SEVERE, WARNING, ERROR
    }
    private static Logger logger = Logger.getLogger(MyLogger.class.getName());
    public MyLogger() throws SecurityException, IOException{
        super("log.txt", true);
        logger.addHandler(this);
        logger.setLevel(Level.ALL);
        System.out.print(this);
    }
    public void log(LogLevel level, String message) {
        switch(level) {
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
        }
    }
    public void publish(LogRecord record) {
        super.publish(record);
        flush();
    }
    
}
