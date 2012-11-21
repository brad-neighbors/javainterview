package liquibase.logging.core;

import org.apache.log4j.Logger;

/**
 * Extends Liquibase's logger to log using log4j, so that log statements are correctly appended to logs, consoles, etc.
 */
public class Log4jLiquibaseLogger extends AbstractLogger {

    private static final Logger logger = Logger.getLogger("Liquibase");

    public void setName(final String s) {
    }

    public void setLogLevel(final String logLevel, final String logFile) {
    }

    public void severe(final String message) {
        logger.fatal(message);
    }

    public void severe(final String message, final Throwable throwable) {
        logger.fatal(message, throwable);
    }

    public void warning(final String message) {
        logger.warn(message);
    }

    public void warning(final String message, final Throwable throwable) {
        logger.warn(message, throwable);
    }

    public void info(final String message) {
        logger.info(message);
    }

    public void info(final String message, final Throwable throwable) {
        logger.info(message, throwable);
    }

    public void debug(final String message) {
        logger.debug(message);
    }

    public void debug(final String message, final Throwable throwable) {
        logger.debug(message, throwable);
    }

    public int getPriority() {
        return 2;
    }
}
