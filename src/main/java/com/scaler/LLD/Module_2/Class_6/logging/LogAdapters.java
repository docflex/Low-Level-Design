package com.scaler.LLD.Module_2.Class_6.logging;

import com.scaler.LLD.Module_2.Class_6.logging.ThirdPartyLogClients.DatabricksClient;
import com.scaler.LLD.Module_2.Class_6.logging.ThirdPartyLogClients.FileAppender;
import com.scaler.LLD.Module_2.Class_6.logging.ThirdPartyLogClients.SplunkClient;

import java.util.LinkedHashMap;
import java.util.Map;

/** Adapters: each one translates LogSink.write(...) into one vendor's API. */
public final class LogAdapters {
    private LogAdapters() {
    }

    public static class SplunkLogAdapter implements LogSink {
        private final SplunkClient splunk;

        public SplunkLogAdapter(SplunkClient splunk) {
            this.splunk = splunk;
        }

        @Override
        public String name() {
            return "Splunk";
        }

        @Override
        public void write(LogLevel level, String message) {
            splunk.sendEvent("{\"level\":\"" + level + "\",\"msg\":\"" + message.replace("\"", "\\\"") + "\"}");
        }
    }

    public static class DatabricksLogAdapter implements LogSink {
        private final DatabricksClient databricks;

        public DatabricksLogAdapter(DatabricksClient databricks) {
            this.databricks = databricks;
        }

        @Override
        public String name() {
            return "Databricks";
        }

        @Override
        public void write(LogLevel level, String message) {
            Map<String, String> row = new LinkedHashMap<>();
            row.put("level", level.name());
            row.put("message", message);
            databricks.insertRow("app_logs", row);
        }
    }

    public static class FileLogAdapter implements LogSink {
        private final FileAppender file;

        public FileLogAdapter(FileAppender file) {
            this.file = file;
        }

        @Override
        public String name() {
            return "File";
        }

        @Override
        public void write(LogLevel level, String message) {
            file.appendLine(level + " " + message);
        }
    }
}
