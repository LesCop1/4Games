package fr.bcecb.util;

import javax.annotation.Nonnull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import java.util.logging.Formatter;

public enum Log {
    SYSTEM("System"),
    GAME("Game"),
    UI("UI"),
    RENDER("Render Engine");
    
    private final Logger logger;
    
    Log(String name) {
        logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        logger.addHandler(new ConsoleHandler() {{
            setFormatter(new LogFormatter());
            setLevel(Level.CONFIG);
        }});
        logger.setLevel(Level.CONFIG);
    }

    public Logger getLogger() {
        return logger;
    }

    public void info(@Nonnull Object... message) {
        log(Level.INFO, this, message);
    }

    public void config(@Nonnull Object... message) {
        log(Level.CONFIG, this, message);
    }

    public void warning(@Nonnull Object... message) {
        log(Level.WARNING, this, message);
    }

    public void severe(@Nonnull Object... message) {
        log(Level.SEVERE, this, message);
    }

    public void log(@Nonnull Object... message) {
        log(Level.INFO, message);
    }

    public static void info(Log log, @Nonnull Object... message) {
        log(Level.INFO, log, message);
    }

    public static void config(Log log, @Nonnull Object... message) {
        log(Level.CONFIG, log, message);
    }

    public static void warning(Log log, @Nonnull Object... message) {
        log(Level.WARNING, log, message);
    }

    public static void severe(Log log, @Nonnull Object... message) {
        log(Level.SEVERE, log, message);
    }

    public static void log(Level level, @Nonnull Object... message) {
        log(level, Log.SYSTEM, message);
    }

    public static void log(Level level, Log log, @Nonnull Object... message) {
        if (message.length >= 1) {
            log.getLogger().log(level, String.valueOf(message[0]), Arrays.copyOfRange(message, 1, message.length));
        }
    }

    private static class LogFormatter extends Formatter {
        private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        private static final Map<Level, String> levelColors = new HashMap<>() {{
            put(Level.INFO, "\u001B[37m");
            put(Level.CONFIG, "\u001B[34m");
            put(Level.WARNING, "\u001B[33m");
            put(Level.SEVERE, "\u001B[31m");

        }};

        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(1000);
            builder.append(levelColors.get(record.getLevel()));
            builder.append("[").append(df.format(new Date(record.getMillis()))).append("] ");
            builder.append("[").append(record.getLevel()).append("] ");
            builder.append("[").append(record.getLoggerName()).append("] ");
            builder.append(formatMessage(record));
            builder.append("\u001B[0m\n");
            return builder.toString();
        }
    }
}
