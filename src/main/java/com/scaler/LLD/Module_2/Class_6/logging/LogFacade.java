package com.scaler.LLD.Module_2.Class_6.logging;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Facade: the single entry point the rest of the application uses for logging.
 * Behind one info()/warn()/error() call it:
 *  1. masks sensitive data (card numbers) before anything leaves the app
 *  2. sends the line to every destination through its adapter
 *  3. keeps going if one destination fails, and reports which ones failed
 */
public class LogFacade {
    private static final Pattern CARD_NUMBER = Pattern.compile("\\b\\d{12}(\\d{4})\\b");

    private final List<LogSink> sinks;

    public LogFacade(List<LogSink> sinks) {
        this.sinks = List.copyOf(sinks);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    private void log(LogLevel level, String message) {
        String safe = CARD_NUMBER.matcher(message).replaceAll("************$1");
        for (LogSink sink : sinks) {
            try {
                sink.write(level, safe);
            } catch (RuntimeException e) {
                System.out.println("    (" + sink.name() + " failed: " + e.getMessage() + " -- other destinations unaffected)");
            }
        }
    }
}
