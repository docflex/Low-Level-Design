package com.scaler.LLD.Module_2.Class_6.Factory;

import com.scaler.LLD.Module_2.Class_6.Adapter.ICICIBankAdapter;
import com.scaler.LLD.Module_2.Class_6.Adapter.YesBankAdapter;
import com.scaler.LLD.Module_2.Class_6.BankAPI;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry-based factory: returns the right Adapter for a bank name.
 * Adding a bank = registerAPI(...) -- no if-else to edit.
 * Names are case-insensitive; an unknown name fails immediately instead of returning null.
 */
public class BankAPIFactory {
    private static final Map<String, BankAPI> registeredAPIProviders = new ConcurrentHashMap<>();

    static {
        registerAPI("Yes Bank", new YesBankAdapter());
        registerAPI("ICICI Bank", new ICICIBankAdapter());
    }

    private BankAPIFactory() {
    }

    public static void registerAPI(String providerName, BankAPI bankAPI) {
        registeredAPIProviders.put(key(providerName), bankAPI);
    }

    public static BankAPI getBankAPI(String providerName) {
        BankAPI bankAPI = registeredAPIProviders.get(key(providerName));
        if (bankAPI == null) {
            throw new IllegalArgumentException("Unknown bank: " + providerName + ". Registered: " + providers());
        }
        return bankAPI;
    }

    public static Set<String> providers() {
        return new TreeSet<>(registeredAPIProviders.keySet());
    }

    private static String key(String providerName) {
        return providerName.toLowerCase(Locale.ROOT);
    }
}
