# Low-Level Design

## Design Patterns: Decorator and Flyweight

**Presented By:** Rehber Moin

---

## Agenda

1. Decorator Design Pattern
2. Real Life Use Case
3. A Production Example: Notifications
4. Flyweight Design Pattern
5. AI Corner

---

## 1. Decorator Design Pattern

> **Decorator: attaches extra behaviour to an object at runtime by wrapping it in another object of the same type -- without changing the original class and without a subclass for every combination.**

Both Decorator and Flyweight are **structural** patterns: they're about how objects are composed into larger structures.

### 1.1 The Problem: A Customisable Coffee Order

At a coffee shop you start with a base drink and customise it: an extra shot, decaf, stevia, mocha, whipped cream... Every drink needs two things:

- `cost()` -- the total price
- `description()` -- what's in it

**Attempt 1: one class with a list of add-ons**

```java
class Americano implements Coffee {
    private final List<String> addOns;

    public int cost() {
        int price = 150;
        for (String addOn : addOns) {
            if (addOn.equals("Mocha")) price += 50;
            else if (addOn.equals("Whip")) price += 60;
            // ... one branch per add-on
        }
        if (countOf("Mocha") >= 2) {
            price = price * 90 / 100;     // 10% off for two mochas
        }
        return price;
    }
}
```

| Problem | Why |
|---------|-----|
| **OCP** | Every new add-on or offer (festive discount, combo deal) means editing `cost()` |
| **SRP** | The coffee class now prices add-ons *and* runs discount rules |

**Attempt 2: one class per combination** -- `EspressoWithWhip`, `EspressoWithMochaMocha`, ...

Each class is simple, but with N optional add-ons that can each be on or off, you need up to **2^N** classes per base coffee (64 for 6 add-ons, 1,024 for 10). That's **class explosion** -- the same problem as the birds example from SOLID.

### 1.2 Building the Decorator

**The component -- what every coffee can do:**

```java
public interface Coffee {
    int cost();
    String description();
}

public class Espresso implements Coffee {
    public int cost() { return 100; }
    public String description() { return "Espresso"; }
}

public class Decaf implements Coffee {
    public int cost() { return 120; }
    public String description() { return "Decaf"; }
}
```

**First try at a condiment:** it holds the coffee it's added to.

```java
public abstract class Condiment {
    protected Coffee coffee;
    public Condiment(Coffee coffee) { this.coffee = coffee; }
    public abstract int cost();
    public abstract String description();
}

public class Mocha extends Condiment {
    public Mocha(Coffee coffee) { super(coffee); }
    public int cost() { return coffee.cost() + 50; }
    public String description() { return coffee.description() + " with Mocha"; }
}
```

`new Mocha(new Espresso())` works. But **Espresso + Mocha + Whip** doesn't: `WhippedCream` needs a `Coffee`, and a `Mocha` *isn't* a `Coffee` -- so `new WhippedCream(mocha)` doesn't compile.

**The key step: make the condiment a `Coffee` too.**

```java
public abstract class Condiment implements Coffee {
    protected final Coffee coffee;     // the coffee being decorated

    public Condiment(Coffee coffee) {
        this.coffee = coffee;
    }
}
```

A condiment now **is-a** `Coffee` (so it can be passed anywhere a coffee is expected -- including into another condiment) and **has-a** `Coffee` (the one it wraps).

```java
Coffee coffee = new Espresso();
coffee = new Mocha(coffee);          // Espresso with Mocha            -> 150
coffee = new WhippedCream(coffee);   // ... with Whipped Cream         -> 210
coffee = new Mocha(coffee);          // ... with Mocha                 -> 260

// or, inline:
Coffee order = new Mocha(new WhippedCream(new Mocha(new Espresso())));   // Rs.260
```

New customer request? Combine the existing pieces -- no new classes, no edits.

> **Common mistake:** "this works like a Builder or a Stream, because each step returns the **same object**." It doesn't -- each `new Mocha(coffee)` creates a **new** wrapper object, and you reassign your variable to it (verified: `before == coffee` is `false`). What stays the same is the **type**: every wrapper is still a `Coffee`, so it can be wrapped again. (A Builder returns the same object, `this`. A Stream returns a *new* Stream from each step -- also verified.)

```
                <<Coffee>>
              cost(), description()
               ^              ^
               |              |
     Espresso, Decaf     Condiment (abstract) ──has-a──> Coffee
                               ^
                               |
                      Mocha, WhippedCream
```

### 1.3 How a Decorated Object Is Built and Called

```java
Coffee coffee = new Mocha(new WhippedCream(new Mocha(new Espresso())));
```

**Construction -- inside out:** Java evaluates the innermost `new` first: `Espresso` -> `Mocha` -> `WhippedCream` -> outer `Mocha`.

**Calling `cost()` -- outside in, then results flow back out (verified):**

```
-> Mocha(outer).cost() called
  -> Whip.cost() called
    -> Mocha.cost() called
      -> Espresso.cost() called
      <- Espresso returns 100
    <- Mocha returns 150
  <- Whip returns 210
<- Mocha(outer) returns 260
```

Each layer calls the layer inside it, then adds its own part on the way back.

> Runnable: `Decorator/Client`, `Decorator/DecoratorEvolutionDemo` (steps 1-5, including this trace)

### 1.4 The Name

Like gift-wrapping a toy: you can add as many layers of wrapping as you like, and the toy inside never changes. Formally (GoF): *"Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality."*

| Role | In the coffee example |
|------|-----------------------|
| **Component** (interface) | `Coffee` |
| **Concrete component** | `Espresso`, `Decaf` |
| **Decorator** (abstract) | `Condiment` |
| **Concrete decorators** | `Mocha`, `WhippedCream` |

> **Note:** in the opening story, decaf and extra espresso shots were add-ons. In the code, `Espresso` and `Decaf` are **base** coffees and only `Mocha` and `WhippedCream` are decorators. Either modelling works -- what matters is deciding which things are bases and which are add-ons.

---

## 2. Real Life Use Case

### 2.1 Java I/O

```java
BufferedReader reader = new BufferedReader(new FileReader("abc.txt"));
```

| Class | Role |
|-------|------|
| `Reader` | Component (abstract class) |
| `FileReader` | Concrete component -- reads characters from a file |
| `BufferedReader` | Decorator -- *is* a `Reader`, *wraps* a `Reader`, adds buffering and `readLine()` |

`FileReader` isn't changed -- it's wrapped. And because `BufferedReader` accepts **any** `Reader`, the same decorator works over a `StringReader`, an `InputStreamReader`, and so on (verified: `new BufferedReader(new StringReader("line one\nline two")).readLine()` returns `"line one"`).

Same idea in the collections framework:

| Wrapper | Adds |
|---------|------|
| `Collections.unmodifiableList(list)` | Blocks changes -- `add()` throws `UnsupportedOperationException` (verified) |
| `Collections.synchronizedList(list)` | Thread-safe access through a lock |

### 2.2 UI Components

`AnimatedButton(LoggingButton(BasicButton))`: logging, animations, analytics or a loading spinner can each be added to a button by wrapping it. When `click()` is called, it passes through the outer decorators down to the basic button.

### 2.3 When to Use Decorator

Use it when you have a base object and want to add **optional** behaviours **at runtime**, in **any combination**, without changing the original class.

**Signal:** without it, you'd need a subclass for every combination of features.

### Beyond the Class: Decorator Trade-offs

| Downside | What it means |
|----------|---------------|
| Many small objects | A heavily decorated object is a chain of wrappers -- harder to debug |
| Order can matter | `Retry(Logging(sms))` and `Logging(Retry(sms))` behave differently (section 3) |
| Identity / type checks | `order instanceof Mocha` is false if the outermost layer is `WhippedCream` -- the Mocha is hidden inside |

---

## 3. A Production Example: Notifications

**Requirement:** notifications go out via Email, SMS and Push. Add logging -- but only for Email for now. Later: logging for SMS too, and retries for unreliable channels.

### 3.1 Channels

```java
public interface Notification {
    void send(String user, String message);
}

public class Email implements Notification {
    public void send(String user, String message) {
        System.out.println("Sending Notif Via Email from: " + user + " Message: " + message);
    }
}

public class SMS implements Notification { ... }
```

### 3.2 The Decorator Base Class

```java
public abstract class NotificationDecorator implements Notification {
    protected final Notification wrappedNotification;

    public NotificationDecorator(Notification wrappedNotification) {
        this.wrappedNotification = wrappedNotification;
    }
}
```

- **`implements Notification`**: a decorated notification is still a `Notification`, so it can be used -- and wrapped -- anywhere.
- **`protected`**: subclasses (and classes in the same package) can use `wrappedNotification`.

### 3.3 Logging and Retry

```java
public class LoggingDecorator extends NotificationDecorator {
    public LoggingDecorator(Notification wrapped) { super(wrapped); }

    @Override
    public void send(String user, String message) {
        System.out.println("[LOG]: Sending Message to " + user);
        wrappedNotification.send(user, message);
    }
}

public class RetryDecorator extends NotificationDecorator {
    private final int maxAttempts;

    public RetryDecorator(Notification wrapped) { this(wrapped, 3); }
    public RetryDecorator(Notification wrapped, int maxAttempts) { super(wrapped); this.maxAttempts = maxAttempts; }

    @Override
    public void send(String user, String message) {
        for (int attempt = 1; ; attempt++) {
            try {
                wrappedNotification.send(user, message);
                return;                                   // success
            } catch (RuntimeException e) {
                if (attempt == maxAttempts) throw e;      // out of attempts: let the caller know
            }
        }
    }
}
```

> **Common mistake:** a "retry" decorator that prints `[RETRY]` and calls `send()` once. If the gateway fails, the exception escapes after **one** attempt -- nothing was retried (verified). The decorator must catch the failure and call the wrapped notification again.

### 3.4 Using It

```java
Notification email = new LoggingDecorator(new Email());               // logging for email only
Notification sms   = new SMS();                                        // plain

Notification smsLogged = new LoggingDecorator(new SMS());              // later: logging for SMS -- no code change
Notification reliable  = new RetryDecorator(new LoggingDecorator(new SMS()));   // retry + logging
```

**Order matters (verified, with an SMS gateway that fails twice):**

```
Retry(Logging(SMS))                       Logging(Retry(SMS))
[RETRY] attempt 1                         [LOG] Sending Message to Nehal
[LOG]   Sending Message to Nehal          [RETRY] attempt 1  -> fails
  -> fails                                [RETRY] attempt 2  -> fails
[RETRY] attempt 2                         [RETRY] attempt 3
[LOG]   Sending Message to Nehal          SMS sent
  -> fails
[RETRY] attempt 3
[LOG]   Sending Message to Nehal
SMS sent
Logs every attempt                        Logs once per send
```

### 3.5 Why an Abstract Decorator Class?

Couldn't `LoggingDecorator` and `RetryDecorator` just implement `Notification` directly? They could -- and they'd still *be* `Notification`s by type (that's what makes them stackable). Two reasons to add the abstract `NotificationDecorator`:

1. **No duplication.** Every decorator needs the same field, the same constructor and the same assignment. Write it once in the base class; `EncryptionDecorator`, `MetricsDecorator`, ... only write their extra behaviour.
2. **Clear roles.** `Email` and `SMS` are *channels*. `LoggingDecorator` and `RetryDecorator` are *behaviours added to a channel*. The base class makes that distinction visible in the design.

> Runnable: `Decorator/NotificationSystem/Client` (logging, real retries, order of decorators, giving up after 3 attempts)

---

## 4. Flyweight Design Pattern

> **Flyweight: saves memory when you have a huge number of similar objects, by sharing the parts they have in common instead of storing a copy in each object.**

### 4.1 The Problem: Bullets

In a shooter game, every bullet in flight is an object:

```java
class Bullet {
    int size;
    String color;
    int speed;
    int xCoord;
    int yCoord;
    double radius;
    int maxRange;
    int damage;
}
```

Thousands of bullets, and all AK-47 bullets have the same size, colour, speed, radius, range and damage. Only the **position** differs.

### 4.2 How Big Is One Bullet?

> **Correction:** counting only the fields gives the wrong answer. An `int` is 4 bytes (not 8), a `String` field holds a 4-byte **reference** (with compressed pointers) rather than the text, and every Java object also has a **header** (12 bytes on a typical 64-bit JVM) and is padded to a multiple of 8 bytes.

| Part | Bytes (64-bit JVM, compressed pointers) |
|------|------------------------------------------|
| Object header | 12 |
| 6 `int` fields (size, speed, x, y, maxRange, damage) | 24 |
| 1 `double` (radius) | 8 |
| 1 reference (color) | 4 |
| **Total** | **48** (already a multiple of 8) |

Measured: **~48 bytes** per full bullet (verified in `FlyweightEvolutionDemo`, 1,000,000 bullets). The `"Brown"` text itself is shared, because identical string literals point to the same object.

### 4.3 Intrinsic vs Extrinsic

| | Intrinsic state | Extrinsic state |
|--|----------------|-----------------|
| Meaning | Shared -- same for every object of a kind | Unique to each object, changes over time |
| Bullet | color, speed, radius, maxRange, damage | xCoord, yCoord |
| Where it lives | One shared **flyweight** object | Each individual object |

### 4.4 The Code

```java
public final class BulletType {               // the flyweight: intrinsic state only
    private final String color;
    private final int speed;
    private final double radius;
    private final int maxRange;
    private final int damage;
    // constructor + getters, no setters
}

public class Bullet {                          // one per bullet in flight
    private final BulletType bulletType;       // shared
    private int coordX;                        // extrinsic
    private int coordY;
}
```

Fifty thousand AK-47 bullets now point to **one** `BulletType` object:

```
Bullet(12, 40) ──┐
Bullet(55, 7)  ──┼──> BulletType AK47 {Brown, 100, 7.0, 20, 10}     (one object)
Bullet(90, 63) ──┘
   ... 50,000 bullets
```

> **Precision note:** this isn't a "shallow copy" -- nothing is copied at all. Every bullet just holds a **reference** to the same `BulletType`.

### 4.5 How Much Memory Does It Save?

A flyweight bullet = header (12) + one reference (4) + two ints (8) = **24 bytes**.

| | Per bullet | 1,000,000 bullets |
|--|-----------|-------------------|
| Every field in every bullet | ~48 bytes | ~48 MB |
| Flyweight | ~24 bytes + one shared `BulletType` (~40 bytes) | ~24 MB |
| **Saving** | | **~2x** |

> **Correction:** you may see this calculated as "56 bytes vs 8 bytes per bullet -- a 7x saving". That leaves out the object header and the reference to the shared type that every bullet still needs. Measured on a 64-bit JVM, the saving here is about **2x** (verified, consistently, across runs). Flyweight's savings grow with how **large** the shared part is: if each bullet type also carried a 3D model and textures (kilobytes), sharing it would save far more.

### 4.6 Must the Shared Object Be Immutable? Yes.

```java
// If BulletType had a setter...
bulletA.getBulletType().setDamage(999);    // meant to buff ONE bullet
// ...every AK-47 bullet now does 999 damage (verified with a mutable version)
```

Flyweights are shared by thousands of objects (and often across threads), so they must be **immutable**: `final` fields, no setters. Immutability also makes them safe to share between threads without locks.

### 4.7 Why Not Make the Intrinsic Fields `static`?

`static` fields belong to the **class**, so there's exactly one value for all bullets. That breaks as soon as you have two bullet types: setting up the M416 overwrites the AK-47's damage (verified). Flyweight gives you one shared object **per type**.

### 4.8 Who Creates the Flyweights? A Factory

The client shouldn't create `BulletType`s -- if two places each did `new BulletType(...)` for AK-47, you'd have two copies and lose the sharing. A **flyweight factory** (here, a registry) hands out the same object for the same name:

```java
public class BulletTypeProvider {
    private static final Map<String, BulletType> bulletTypeRegistry = new ConcurrentHashMap<>();

    static {
        registerBulletType("AK47", new BulletType("Brown", 100, 7, 20, 10));
    }

    public static void registerBulletType(String name, BulletType bulletType) {
        bulletTypeRegistry.put(key(name), bulletType);
    }

    public static BulletType getBulletType(String name) {
        BulletType type = bulletTypeRegistry.get(key(name));
        if (type == null) {
            throw new IllegalArgumentException("Unknown bullet type: " + name);
        }
        return type;
    }
}

public Bullet(String bulletType, int coordX, int coordY) {
    this.bulletType = BulletTypeProvider.getBulletType(bulletType);
    ...
}
```

> **Correction:** this is sometimes described as "Prototype without cloning". It isn't Prototype -- Prototype's whole point is making **copies**, and copying a `BulletType` per bullet would bring the memory back (5 bullets -> 5 copies; verified). This is the **flyweight factory** from the Flyweight pattern, implemented with a **Registry**.

> Runnable: `Flyweight/Client` (50,000 bullets of two types share 2 `BulletType` objects), `Flyweight/FlyweightEvolutionDemo` (measured memory, mutable flyweight bug, static fields, per-bullet copies)

### 4.9 The Name

From boxing: *flyweight* is one of the lightest weight classes. The pattern makes each object as light as possible.

### Quiz

**A text editor renders millions of characters. Each character has a font, size, colour and a row/column position. What's the correct intrinsic/extrinsic split?**

**Answer: B** -- intrinsic: font, size, colour; extrinsic: row/column position. Many characters share the same style, but each one sits at its own position. (In real editors the character itself -- the glyph for `'a'` -- is also shared intrinsic state.)

### Beyond the Class: Flyweights in the JDK

| Example | What's shared |
|---------|---------------|
| `Integer.valueOf(127) == Integer.valueOf(127)` | `true` -- small values (-128 to 127 by default) come from a cache; `128 == 128` is `false` (verified) |
| String literals | `"Brown" == "Brown"` is `true` -- identical literals share one object; `new String("Brown")` doesn't, until you call `intern()` (verified) |
| `Boolean.valueOf(...)` | Only two `Boolean` objects ever needed: `TRUE` and `FALSE` |

---

## 5. AI Corner

**Task:** give a **10% discount on the total** if an order has **both** Mocha and Whip. Ask an AI to add it to the coffee decorator code, then review what it gives you.

### 5.1 Why It's Tricky

Each decorator only knows the one drink it wraps. A rule that depends on the **combination** of add-ons needs to see the whole chain.

### 5.2 A Clean Solution

Let every drink answer "do you contain an add-on of type X?", and add the discount as one more decorator, wrapped **outermost** so it sees the final total:

```java
public interface Beverage {
    int cost();
    String description();
    default boolean contains(Class<? extends Beverage> type) {
        return type.isInstance(this);                          // a base drink only contains itself
    }
}

public abstract class AddOn implements Beverage {
    protected final Beverage beverage;
    @Override
    public boolean contains(Class<? extends Beverage> type) {
        return type.isInstance(this) || beverage.contains(type);   // check me, then the drink I wrap
    }
}

public class ComboDiscount extends AddOn {
    public int cost() {
        int total = beverage.cost();
        boolean combo = beverage.contains(Mocha.class) && beverage.contains(Whip.class);
        return combo ? total - total / 10 : total;
    }
}

Beverage order = new ComboDiscount(new Whip(new Mocha(new Espresso())));   // 210 -> Rs.189 (verified)
```

### 5.3 What to Watch for in the AI's Answer

> **Common mistake:** checking the **description text** -- `description().contains("Mocha") && description().contains("Whip")`. It breaks as soon as a name happens to contain the word: a `Mochaccino` base drink with only Whip gets the combo discount, because "Mochaccino" contains "Mocha" (verified). Check the decorator chain **by type** instead.

Other things to check:
- Is the discount applied to the **total** (outermost), not just to the price of one add-on?
- Does adding the add-ons in a different order (Whip then Mocha) still trigger it? (It should -- verified.)
- **Money and rounding:** 10% of an odd total isn't a whole number. Real billing code uses `BigDecimal` or stores amounts in the smallest unit (paise) and states the rounding rule explicitly.

> Runnable: `aicorner/ComboDiscountDemo`

---

## Summary

| Concept | Key Point |
|---------|-----------|
| **Decorator** | Wrap an object in another object of the same type to add behaviour at runtime |
| **is-a + has-a** | A decorator *is* a `Coffee` (stackable) and *has* a `Coffee` (the one it wraps) |
| **Same type, new object** | Each wrap creates a new object; the variable is reassigned |
| **Call order** | Built inside-out; calls go outside-in and results come back out |
| **Real examples** | `BufferedReader(FileReader)`, `Collections.unmodifiableList`, UI button wrappers |
| **Notification decorators** | Logging and retry added to any channel; order of wrapping changes behaviour |
| **Flyweight** | Share intrinsic state in one immutable object; keep extrinsic state per object |
| **Real savings** | Count headers and references: ~48 vs ~24 bytes per bullet here (~2x) |
| **Flyweight factory** | One place that hands out the shared object per key -- never copy it |
| **Combo rules** | Check the decorator chain by type; don't match on description text |

---

## Interview Corner

**1. Decorator vs inheritance -- why not just subclass?**
Subclassing fixes behaviour at compile time and needs a class per combination (class explosion). Decorators combine behaviours at runtime, in any order and any number, from a small set of classes.

**2. Decorator vs Adapter vs Proxy -- they all wrap an object. What's the difference?**
Decorator: same interface, **adds** behaviour. Adapter: **changes** the interface to the one the client expects. Proxy: same interface, **controls access** (lazy loading, caching, security, remote calls).

**3. Give a Decorator example from the JDK.**
`java.io`: `new BufferedReader(new FileReader(...))`, `new BufferedInputStream(new FileInputStream(...))`. Collections: `Collections.unmodifiableList`, `Collections.synchronizedList`.

**4. What's the difference between intrinsic and extrinsic state?**
Intrinsic: shared, identical across many objects, stored once in the flyweight (bullet colour, damage). Extrinsic: unique per object and often changing, stored in (or passed to) each object (bullet position).

**5. Why must flyweights be immutable?**
They're shared by many objects (and often threads). A change through one reference changes it for everyone -- and immutable objects are thread-safe without locks.

**6. Where does Java itself use flyweights?**
The `Integer` cache for `valueOf(-128..127)`, string literal pooling / `String.intern()`, and `Boolean.TRUE` / `Boolean.FALSE`.

---

## Practice Questions

**1. What's the cost and description?**

```java
Coffee c = new WhippedCream(new Mocha(new Mocha(new Decaf())));
```

<details><summary>Answer</summary>

Cost: 120 + 50 + 50 + 60 = **Rs.280**. Description: **"Decaf with Mocha with Mocha with Whipped Cream"** -- the innermost coffee's description comes first, and each layer appends its own on the way back out.

</details>

**2. The SMS gateway fails on the first attempt and succeeds on the second. How many `[LOG]` lines does each print?**

```java
new RetryDecorator(new LoggingDecorator(new FlakySMS(1))).send("Nehal", "Hi");
new LoggingDecorator(new RetryDecorator(new FlakySMS(1))).send("Nehal", "Hi");
```

<details><summary>Answer</summary>

The first prints **2** (`Retry` is outside, so every attempt goes through `Logging`). The second prints **1** (`Logging` is outside and runs once; the retries happen inside it).

</details>

**3. In a forest game with a million trees, which state is intrinsic and which is extrinsic: tree species, 3D model, bark texture, position, height, age?**

<details><summary>Answer</summary>

Intrinsic (shared per species): species, 3D model, bark texture. Extrinsic (per tree): position, height, age. The big win is sharing the 3D model and texture, which are large.

</details>

**4. Spot the bug:**

```java
public class BulletType {
    public int damage;
    ...
}
bullet.getBulletType().damage += 50;   // power-up for this bullet
```

<details><summary>Answer</summary>

`BulletType` is shared by every bullet of that type, so this gives the power-up to **all** of them. Flyweights must be immutable; per-bullet changes belong in the extrinsic state (e.g. a `bonusDamage` field on `Bullet`).

</details>

**5. What does this print?**

```java
Integer a = 100, b = 100, c = 1000, d = 1000;
System.out.println((a == b) + " " + (c == d));
```

<details><summary>Answer</summary>

`true false` (with default JVM settings). `Integer.valueOf` returns cached, shared objects for -128 to 127, so `a` and `b` are the same object; `1000` is outside the cache, so `c` and `d` are different objects. Always compare `Integer`s with `equals()`.

</details>

---

## Demos

| Demo | What It Shows |
|------|---------------|
| `Decorator/Client` | Base coffees, single condiments, building an order step by step and inline |
| `Decorator/DecoratorEvolutionDemo` | One class with an add-on list + discount if-else, class explosion counts, condiment that isn't a `Coffee`, construction vs call order trace, new object / same type |
| `Decorator/NotificationSystem/Client` | Logging for email, then SMS; real retries; how decorator order changes the logs; giving up after max attempts |
| `Flyweight/Client` | 50,000 bullets of two types share 2 `BulletType` objects; per-bullet position; case-insensitive lookup; unknown type fails fast |
| `Flyweight/FlyweightEvolutionDemo` | Measured ~48 vs ~24 bytes per bullet; mutable flyweight bug; static fields; per-bullet copies |
| `aicorner/ComboDiscountDemo` | Combo discount wrapped outermost, type-based check vs description matching |

---

## Further Reading

**Decorator**
- Gamma, Helm, Johnson, Vlissides -- *Design Patterns* (GoF): "Decorator" chapter
- Freeman & Robson -- *Head First Design Patterns*, chapter 3 "The Decorator Pattern" (also built around a coffee shop and condiments)
- Joshua Bloch -- *Effective Java* (3rd ed.), Item 18 "Favor composition over inheritance" (its wrapper-class example is a decorator)
- refactoring.guru -- https://refactoring.guru/design-patterns/decorator

**Flyweight**
- GoF: "Flyweight" chapter (its example is characters in a document editor)
- *Head First Design Patterns*: appendix "Leftover Patterns" (Flyweight)
- *Effective Java* (3rd ed.), Item 6 "Avoid creating unnecessary objects"
- refactoring.guru -- https://refactoring.guru/design-patterns/flyweight
- OpenJDK **JOL** (Java Object Layout) -- a tool for seeing exactly how big your objects are

---

## Homework / Next Steps

No homework was assigned for this class. To practise, try the Practice Questions above, or add a `SugarFree` decorator that **lowers** the price by Rs.10 and check that it combines correctly with `ComboDiscount`.

---
