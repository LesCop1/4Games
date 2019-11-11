package fr.bcecb.util;

import org.lwjgl.glfw.GLFWErrorCallback;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public enum Log {
    SYSTEM("System"),
    EVENT("Event"),
    GAME("Game"),
    UI("UI"),
    RENDER("Render Engine");
    
    private final Logger logger;
    
    Log(String name) {
        logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        logger.addHandler(new ConsoleHandler() {{
            setFormatter(new LogFormatter());
            setLevel(Level.FINE);
        }});
        logger.setLevel(Level.FINE);
    }

    public Logger getLogger() {
        return logger;
    }

    public void config(@Nonnull Object... message) {
        log(Level.FINE, this, message);
    }

    public void debug(@Nonnull Object... message) {
        log(Level.CONFIG, this, message);
    }

    public void info(@Nonnull Object... message) {
        log(Level.INFO, this, message);
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

    public static void log(Level level, @Nonnull Object... message) {
        log(level, Log.SYSTEM, message);
    }

    public static void log(Level level, Log log, @Nonnull Object... message) {
        if (message.length >= 1) {
            log.getLogger().log(level, String.valueOf(message[0]).replaceAll("'", "''"), Arrays.stream(message, 1, message.length).map(String::valueOf).toArray());
        }
    }

    public static GLFWErrorCallback createErrorCallback() {
        return new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                Log.SYSTEM.severe("GLFW error ({0}) : {1}", error, GLFWErrorCallback.getDescription(description));
            }
        };
    }

    private static class LogFormatter extends Formatter {
        private static final DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
        private static final Map<Level, String> levelNames = new HashMap<>() {{
            put(Level.FINE, "CONFIG");
            put(Level.CONFIG, "DEBUG");
            put(Level.SEVERE, "ERROR");
        }};

        private static final Map<Level, String> levelColors = new HashMap<>() {{
            put(Level.FINE, "\u001B[34m");
            put(Level.CONFIG, "\u001B[32m");
            put(Level.INFO, "\u001B[37m");
            put(Level.WARNING, "\u001B[33m");
            put(Level.SEVERE, "\u001B[31m");
        }};

        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(1000);
            builder.append(levelColors.get(record.getLevel()));
            builder.append("[").append(df.format(new Date(record.getMillis()))).append("] ");
            builder.append("[").append(getLevelName(record.getLevel())).append("] ");
            builder.append("[").append(record.getLoggerName()).append("] ");
            builder.append(formatMessage(record));
            builder.append("\u001B[0m\n");
            return builder.toString();
        }

        private static String getLevelName(Level level) {
            return levelNames.getOrDefault(level, level.getName());
        }
    }

    private static class LogOutputStream extends OutputStream {
        private final Level level;
        private StringBuilder stringBuilder;

        public LogOutputStream(Level level) {
            this.level = level;
            this.stringBuilder = new StringBuilder();
        }

        @Override
        public void write(int b) {
            char c = (char)b;
            if(c == '\r' || c == '\n')
            {
                if(stringBuilder.length()>0)
                {
                    Log.log(level, stringBuilder.toString());
                    this.stringBuilder = new StringBuilder();
                }
            }
            else
                stringBuilder.append(c);
        }
    }

    static {
        System.setOut(new PrintStream(new LogOutputStream(Level.CONFIG)));
        System.setErr(new PrintStream(new LogOutputStream(Level.SEVERE)));
    }
}
