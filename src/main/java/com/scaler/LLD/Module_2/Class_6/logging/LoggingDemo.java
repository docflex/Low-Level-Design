package com.scaler.LLD.Module_2.Class_6.logging;

import com.scaler.LLD.Module_2.Class_6.logging.LogAdapters.DatabricksLogAdapter;
import com.scaler.LLD.Module_2.Class_6.logging.LogAdapters.FileLogAdapter;
import com.scaler.LLD.Module_2.Class_6.logging.LogAdapters.SplunkLogAdapter;
import com.scaler.LLD.Module_2.Class_6.logging.ThirdPartyLogClients.DatabricksClient;
import com.scaler.LLD.Module_2.Class_6.logging.ThirdPartyLogClients.FileAppender;
import com.scaler.LLD.Module_2.Class_6.logging.ThirdPartyLogClients.SplunkClient;

import java.util.List;

/**
 * Adapter + Facade together: a single centralized log service writing to Splunk,
 * Databricks and a file. Adapters make the three vendors look the same;
 * the facade gives the app one simple call.
 */
public class LoggingDemo {
    public static void main(String[] args) {
        SplunkClient splunk = new SplunkClient();
        FileAppender file = new FileAppender();
        LogFacade log = new LogFacade(List.of(
                new SplunkLogAdapter(splunk),
                new DatabricksLogAdapter(new DatabricksClient()),
                new FileLogAdapter(file)));

        System.out.println("=== 1. One call, three destinations ===");
        log.info("User rehber logged in");

        System.out.println("\n=== 2. Sensitive data is masked once, in the facade ===");
        log.warn("Payment retry for card 4111111111111111");

        System.out.println("\n=== 3. Splunk goes down -- the others still get the log ===");
        splunk.setDown(true);
        log.error("Order ORD-1001 failed to ship");

        System.out.println("\n  lines written to file: " + file.lineCount());
    }
}
