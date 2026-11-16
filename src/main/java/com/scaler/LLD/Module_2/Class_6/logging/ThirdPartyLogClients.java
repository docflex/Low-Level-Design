package com.scaler.LLD.Module_2.Class_6.logging;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adaptees: stand-ins for three vendor libraries, each with its own incompatible API.
 * (Simplified fakes -- they print instead of calling real services.)
 */
public final class ThirdPartyLogClients {
    private ThirdPartyLogClients() {
    }

    /** Splunk-style: send one JSON event. Can be switched "down" to show failure isolation. */
    public static class SplunkClient {
        private boolean down;

        public void setDown(boolean down) {
            this.down = down;
        }

        public void sendEvent(String json) {
            if (down) {
                throw new IllegalStateException("Splunk HTTP endpoint unreachable");
            }
            System.out.println("    [Splunk]     event " + json);
        }
    }

    /** Databricks-style: insert a row into a table. */
    public static class DatabricksClient {
        public void insertRow(String table, Map<String, String> row) {
            System.out.println("    [Databricks] insert into " + table + " " + row);
        }
    }

    /** File-style: append a line. Keeps lines in memory instead of touching the disk. */
    public static class FileAppender {
        private final List<String> lines = new ArrayList<>();

        public void appendLine(String line) {
            lines.add(line);
            System.out.println("    [File]       " + line);
        }

        public int lineCount() {
            return lines.size();
        }
    }
}
