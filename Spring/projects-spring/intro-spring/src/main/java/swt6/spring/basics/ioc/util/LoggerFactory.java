package swt6.spring.basics.ioc.util;

public class LoggerFactory {
    public static Logger getLogger(String type) {
        if (type.equals("file"))
            return new FileLogger("log.txt");
        else if (type.equals("console")) {
            return new ConsoleLogger();
        } else
            throw new IllegalArgumentException("invalid logger type " + type);
    }
}
