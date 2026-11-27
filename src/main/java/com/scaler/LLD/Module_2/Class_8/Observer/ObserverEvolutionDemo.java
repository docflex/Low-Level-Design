package com.scaler.LLD.Module_2.Class_8.Observer;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Observer, step by step:
 *  1. Pull (polling) vs push
 *  2. A public notify() lets anyone fake an event
 *  3. One failing subscriber stops the rest
 *  4. Unsubscribing during a notification (ArrayList vs CopyOnWriteArrayList)
 *  5. The same subscriber added twice
 */
public class ObserverEvolutionDemo {

    interface Listener {
        void onEvent(String event);
    }

    /** A subject written the simple way: ArrayList, public notify, no error handling, duplicates allowed. */
    static class SimpleSubject {
        final List<Listener> listeners = new ArrayList<>();

        void subscribe(Listener l) {
            listeners.add(l);
        }

        void unsubscribe(Listener l) {
            listeners.remove(l);
        }

        public void notifyAllListeners(String event) {
            for (Listener l : listeners) {
                l.onEvent(event);
            }
        }
    }

    /** The safer version: copy-on-write list, no duplicates, failures isolated, notify is private. */
    static class SaferSubject {
        private final List<Listener> listeners = new CopyOnWriteArrayList<>();

        void subscribe(Listener l) {
            if (!listeners.contains(l)) {
                listeners.add(l);
            }
        }

        void unsubscribe(Listener l) {
            listeners.remove(l);
        }

        void publish(String event) {
            notifyAllListeners(event);
        }

        private void notifyAllListeners(String event) {
            for (Listener l : listeners) {
                try {
                    l.onEvent(event);
                } catch (RuntimeException e) {
                    System.out.println("    (a listener failed: " + e.getMessage() + " -- others still notified)");
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Pull vs push: 1 new video over 10 checks ===");
        int polls = 10;
        int changes = 1;
        System.out.println("  polling: the client asks " + polls + " times, " + (polls - changes) + " answers are \"nothing new\"");
        System.out.println("  push:    the server sends " + changes + " message, when the video is uploaded");

        System.out.println("\n=== 2. Public notify: anyone can fake an event ===");
        SimpleSubject orders = new SimpleSubject();
        orders.subscribe(e -> System.out.println("    loyalty points added for " + e));
        orders.notifyAllListeners("Mallory (no order was placed!)");

        System.out.println("\n=== 3. One failing subscriber ===");
        SimpleSubject simple = new SimpleSubject();
        simple.subscribe(e -> { throw new IllegalStateException("recommendation DB down"); });
        simple.subscribe(e -> System.out.println("    loyalty updated"));
        try {
            simple.notifyAllListeners("Aman");
        } catch (IllegalStateException e) {
            System.out.println("  simple subject: exception escaped, loyalty never ran (" + e.getMessage() + ")");
        }
        SaferSubject safer = new SaferSubject();
        safer.subscribe(e -> { throw new IllegalStateException("recommendation DB down"); });
        safer.subscribe(e -> System.out.println("    loyalty updated"));
        System.out.println("  safer subject:");
        safer.publish("Aman");

        System.out.println("\n=== 4. A subscriber unsubscribes itself while being notified ===");
        for (int count : new int[]{2, 3}) {
            SimpleSubject subject = new SimpleSubject();
            List<String> ran = new ArrayList<>();
            Listener[] self = new Listener[1];
            self[0] = e -> { ran.add("A"); subject.unsubscribe(self[0]); };
            subject.subscribe(self[0]);
            for (int i = 1; i < count; i++) {
                String name = String.valueOf((char) ('A' + i));
                subject.subscribe(e -> ran.add(name));
            }
            try {
                subject.notifyAllListeners("event");
                System.out.println("  ArrayList, " + count + " subscribers: ran " + ran + " -- " + (count - ran.size()) + " silently SKIPPED");
            } catch (ConcurrentModificationException e) {
                System.out.println("  ArrayList, " + count + " subscribers: ConcurrentModificationException after " + ran);
            }
        }
        SaferSubject cow = new SaferSubject();
        List<String> ran = new ArrayList<>();
        Listener[] self = new Listener[1];
        self[0] = e -> { ran.add("A"); cow.unsubscribe(self[0]); };
        cow.subscribe(self[0]);
        cow.subscribe(e -> ran.add("B"));
        cow.subscribe(e -> ran.add("C"));
        cow.publish("event");
        System.out.println("  CopyOnWriteArrayList, 3 subscribers: ran " + ran);

        System.out.println("\n=== 5. Same subscriber added twice ===");
        int[] simpleCount = {0};
        Listener counter = e -> simpleCount[0]++;
        SimpleSubject dup = new SimpleSubject();
        dup.subscribe(counter);
        dup.subscribe(counter);
        dup.notifyAllListeners("event");
        int[] saferCount = {0};
        Listener counter2 = e -> saferCount[0]++;
        SaferSubject noDup = new SaferSubject();
        noDup.subscribe(counter2);
        noDup.subscribe(counter2);
        noDup.publish("event");
        System.out.println("  simple subject: notified " + simpleCount[0] + " times; safer subject: " + saferCount[0]);
    }
}
