package com.scaler.LLD.Module_1.Part_3_Advanced_Java.Class_3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Java Advanced Concepts - 3 [Streams and Lambdas]
 *
 * Covers:
 * - Lambdas as syntactic sugar for functional interfaces
 * - Custom @FunctionalInterface (Calculator)
 * - Streams pipeline: filter, map, distinct, sorted, forEach
 * - Lazy evaluation (intermediate ops don't run without terminal op)
 * - Short-circuiting (findFirst stops early)
 * - Checked vs unchecked exceptions intro
 */
public class Client {
    public static void main(String[] args) {

        // ════════════════════════════════════════════
        // Part 1: Lambdas
        // ════════════════════════════════════════════

        // ── 1a: Runnable via class vs lambda ──
        System.out.println("=== 1a: Runnable -- class vs lambda ===");

        // Traditional way: create a Task class implementing Runnable
        Task task = new Task();
        Thread t1 = new Thread(task);
        t1.start();
        try { t1.join(); } catch (InterruptedException ignored) {}

        // Lambda way: same thing in one line
        Runnable lambdaTask = () -> System.out.println("Hello World (lambda)");
        Thread t2 = new Thread(lambdaTask);
        t2.start();
        try { t2.join(); } catch (InterruptedException ignored) {}

        // ── 1b: Comparator via class vs lambda ──
        System.out.println("\n=== 1b: Comparator -- class vs lambda (sort by squares) ===");

        List<Integer> l = Arrays.asList(2, 4, -3, -5, 1);
        System.out.println("Before: " + l);

        // Traditional: SquaresComparator class
        Collections.sort(l, new SquaresComparator());
        System.out.println("SquaresComparator: " + l);

        // Lambda: inline, no class needed
        l = Arrays.asList(2, 4, -3, -5, 1);
        Collections.sort(l, (o1, o2) -> Integer.compare(o1 * o1, o2 * o2));
        System.out.println("Lambda comparator: " + l);

        // ── 1c: Custom @FunctionalInterface ──
        System.out.println("\n=== 1c: Custom @FunctionalInterface (Calculator) ===");

        Calculator sum = (a, b, c) -> a + b + c;
        Calculator product = (a, b, c) -> a * b * c;
        System.out.println("sum(1,2,3) = " + sum.calculate(1, 2, 3));
        System.out.println("product(2,3,4) = " + product.calculate(2, 3, 4));

        // ════════════════════════════════════════════
        // Part 2: Streams
        // ════════════════════════════════════════════

        // ── 2a: Basic pipeline -- filter evens, square, distinct ──
        System.out.println("\n=== 2a: Stream pipeline -- filter evens, square, distinct ===");

        List<Integer> nums = Arrays.asList(3, 1, 3, 4, 2, 7, 9, 7, 8);
        System.out.println("Input: " + nums);

        // Step-by-step
        Stream<Integer> s = nums.stream();
        s = s.filter(num -> num % 2 == 0);   // keep evens
        s = s.map(num -> num * num);          // square them
        List<Integer> result = s.distinct().collect(Collectors.toList());
        System.out.println("Step-by-step (filter->map->distinct): " + result);

        // Inline (fluent) version -- same result
        List<Integer> inline = nums.stream()
                .filter(num -> num % 2 == 0)
                .map(num -> num * num)
                .distinct()
                .toList();
        System.out.println("Inline: " + inline);

        // ── 2b: sorted + forEach (reverse order) ──
        System.out.println("\n=== 2b: Sorted reverse + forEach ===");

        nums.stream()
                .filter(num -> num % 2 == 0)
                .map(num -> num * num)
                .distinct()
                .sorted((a, b) -> Integer.compare(b, a))  // reverse; avoid b-a overflow
                .forEach(num -> System.out.println("  " + num));

        // ════════════════════════════════════════════
        // Part 3: Lazy Evaluation
        // ════════════════════════════════════════════

        System.out.println("\n=== 3a: Lazy evaluation -- no terminal op ===");

        List<Integer> ints = Arrays.asList(1, 2, 3);

        Stream<Integer> stream = ints.stream()
                .map(x -> {
                    System.out.println("  Mapping x = " + x);
                    return x * x;
                });

        System.out.println("(No output above -- map() didn't run without terminal op!)");

        // ── 3b: With terminal op -- now it runs ──
        System.out.println("\n=== 3b: Lazy evaluation -- with terminal op ===");

        List<Integer> squared = ints.stream()
                .map(x -> {
                    System.out.println("  Mapping x = " + x);
                    return x * x;
                })
                .toList();

        System.out.println("Result: " + squared);

        // ════════════════════════════════════════════
        // Part 4: Short-Circuiting
        // ════════════════════════════════════════════

        // ── 4a: findFirst -- stops after first match ──
        System.out.println("\n=== 4a: Short-circuit -- filter + findFirst ===");

        List<Integer> nums2 = Arrays.asList(1, 2, 3, 4, 5, 6);

        Optional<Integer> firstEven = nums2.stream()
                .filter(x -> {
                    System.out.println("  Checking x = " + x);
                    return x % 2 == 0;
                })
                .findFirst();

        System.out.println("First even: " + firstEven.orElse(-1));
        System.out.println("(Only checked 1 and 2 -- stopped early!)");

        // ── 4b: filter + sorted + findFirst -- must check all ──
        System.out.println("\n=== 4b: filter + sorted + findFirst (no short-circuit) ===");

        List<Integer> nums3 = Arrays.asList(9, 7, 6, 5, 3, 1, 2);

        Optional<Integer> firstSortedEven = nums3.stream()
                .filter(x -> {
                    System.out.println("  Checking x = " + x);
                    return x % 2 == 0;
                })
                .sorted()
                .findFirst();

        System.out.println("First sorted even: " + firstSortedEven.orElse(-1));
        System.out.println("(Checked all elements -- sort needs full input!)");

        // ── 4c: sorted THEN filter + findFirst -- short-circuits ──
        System.out.println("\n=== 4c: sorted + filter + findFirst (short-circuits!) ===");

        firstSortedEven = nums3.stream()
                .sorted()
                .filter(x -> {
                    System.out.println("  Checking x = " + x);
                    return x % 2 == 0;
                })
                .findFirst();

        System.out.println("First sorted even: " + firstSortedEven.orElse(-1));
        System.out.println("(Sorted first, then only checked until first even found!)");
    }
}
