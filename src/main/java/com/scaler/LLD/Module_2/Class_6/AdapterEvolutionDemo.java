package com.scaler.LLD.Module_2.Class_6;

import com.scaler.LLD.Module_2.Class_6.Adapter.ICICIBankAdapter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Adapter, step by step:
 *  1. PhonePe tied to Yes Bank's own methods     2. An adapter that drops the balance (bug)
 *  3. A lookup that returns null                  4. Beyond the class: class adapter vs object adapter
 *  5. Beyond the class: retries and transaction IDs   6. Adapters you already use in the JDK
 */
public class AdapterEvolutionDemo {

    /** Step 1: calls Yes Bank's methods directly. Moving to ICICI means rewriting every call. */
    static class TightlyCoupledPhonePe {
        private final YesBank yesBank = new YesBank();

        void sendMoney(String accountNumber, double amount) {
            yesBank.makeTransaction(accountNumber, amount);   // ICICI calls this transferMoney(..., txnId)
        }
    }

    /** Step 2: an adapter that calls the bank but forgets to return what the bank said. */
    static class BalanceDroppingICICIAdapter implements BankAPI {
        private final ICICIBank iciciBank = new ICICIBank();

        @Override
        public void send(String accountNumber, double amount) {
            iciciBank.transferMoney(accountNumber, amount, UUID.randomUUID().toString());
        }

        @Override
        public double checkBalance(String accountNumber) {
            iciciBank.seeCurrentBalance(accountNumber);
            return 0;
        }
    }

    /** Step 4 (beyond the class): class adapter -- inherits from the adaptee instead of wrapping it. */
    static class YesBankClassAdapter extends YesBank implements BankAPI {
        @Override
        public void send(String accountNumber, double amount) {
            makeTransaction(accountNumber, amount);
        }

        @Override
        public double checkBalance(String accountNumber) {
            return getBalance(accountNumber);
        }
    }

    /** Step 5 (beyond the class): a bank that remembers which transaction IDs it has already processed. */
    static class IdempotentBank {
        private final Map<String, Double> processed = new HashMap<>();

        void transfer(String txnId, double amount) {
            if (processed.putIfAbsent(txnId, amount) != null) {
                System.out.println("    bank: " + txnId.substring(0, 8) + " already processed -- ignoring the retry");
            } else {
                System.out.println("    bank: transferred " + amount + " (txn " + txnId.substring(0, 8) + ")");
            }
        }

        int transfers() {
            return processed.size();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println("=== Step 1: PhonePe calls Yes Bank's API directly ===");
        new TightlyCoupledPhonePe().sendMoney("123", 3.2);
        System.out.println("  switching to ICICI = new method names + a transactionId in every call site");

        System.out.println("\n=== Step 2: adapter that drops the balance ===");
        System.out.println("  buggy adapter  -> " + new BalanceDroppingICICIAdapter().checkBalance("123") + "  (bank said 2500.0)");
        System.out.println("  ICICIBankAdapter -> " + new ICICIBankAdapter().checkBalance("123"));

        System.out.println("\n=== Step 3: a registry lookup that returns null ===");
        Map<String, BankAPI> registry = new HashMap<>();
        registry.put("ICICI Bank", new ICICIBankAdapter());
        BankAPI missing = registry.get("ICICI");          // wrong key: no error yet
        System.out.println("  registry.get(\"ICICI\") -> " + missing);
        try {
            new PhonePe(missing);
        } catch (NullPointerException e) {
            System.out.println("  new PhonePe(null) -> NullPointerException: " + e.getMessage()
                    + "  (BankAPIFactory now fails at the lookup instead)");
        }

        System.out.println("\n=== Step 4 (beyond the class): class adapter ===");
        YesBankClassAdapter classAdapter = new YesBankClassAdapter();
        System.out.println("  balance via class adapter -> " + classAdapter.checkBalance("123"));
        System.out.println("  but it IS-A YesBank too, so callers can still reach makeTransaction() directly");

        System.out.println("\n=== Step 5 (beyond the class): a retry with a new transaction ID ===");
        IdempotentBank bank = new IdempotentBank();
        System.out.println("  adapter generates a new ID on every call, request times out, PhonePe retries:");
        bank.transfer(UUID.randomUUID().toString(), 500);
        bank.transfer(UUID.randomUUID().toString(), 500);
        System.out.println("  -> transfers made: " + bank.transfers() + "  (customer charged twice)");
        IdempotentBank bank2 = new IdempotentBank();
        String idempotencyKey = UUID.randomUUID().toString();
        System.out.println("  caller passes the same idempotency key on the retry:");
        bank2.transfer(idempotencyKey, 500);
        bank2.transfer(idempotencyKey, 500);
        System.out.println("  -> transfers made: " + bank2.transfers());

        System.out.println("\n=== Step 6: adapters you already use in the JDK ===");
        String[] array = {"a", "b"};
        List<String> list = Arrays.asList(array);         // adapts an array to the List interface
        list.set(0, "z");
        System.out.println("  Arrays.asList: list.set(0, \"z\") -> array[0] = " + array[0] + "  (same data, List interface)");
        Reader reader = new InputStreamReader(new ByteArrayInputStream("hi".getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
        System.out.println("  InputStreamReader: bytes -> chars, first char = '" + (char) reader.read() + "'");
    }
}
