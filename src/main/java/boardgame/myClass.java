package boardgame;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class myClass {
    private static final Logger logger = LogManager.getLogger();

    public void myMethod() {
        logger.info("This is an info log message");
        logger.error("This is an error log message");
    }
}