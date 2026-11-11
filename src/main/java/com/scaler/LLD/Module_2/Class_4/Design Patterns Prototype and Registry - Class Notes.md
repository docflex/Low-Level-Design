# Low-Level Design

## Design Patterns: Prototype and Registry

**Presented By:** Rehber Moin

---

## Agenda

1. The Problem
2. Prototype Design Pattern
3. Registry Design Pattern
4. AI Corner
5. Real Use Cases of Prototype & Registry

---

## 1. The Problem

Prototype and Registry are two different patterns, but they're almost always used together -- so we learn them together.

**Scenario:** you and I both want to provision a VM on AWS with the same setup. Instead of configuring a second VM from scratch, can you just **copy** mine?

> The core problem: **copying an object** correctly and cheaply.

### 1.1 Naive Approach: Copy Field by Field

```java
VMInstance vm1 = new VMInstance("Ubuntu 22.04", "Docker 1.4", "Datadog", "scaler.com", "18.243.93.102");

VMInstance vm2 = new VMInstance(...);
vm2.setOs(vm1.getOs());
vm2.setRuntime(vm1.getRuntime());
vm2.setMonitoringAgent(vm1.getMonitoringAgent());
// ... every field
```

| Problem | Why |
|---------|-----|
| Too many lines | One line per field |
| Tight coupling | The client must know **every** field of someone else's class |
| Private fields | A private field without a setter can't be copied from outside at all |

> **Note:** the script's snippet (`vm2.setOS(v1.getOS)`) is pseudo-code: `v1` should be `vm1`, and the getters need `()`. Also, Ubuntu releases are numbered `22.04`, not `22.4` -- the repo now uses `22.04`.

### 1.2 Copy Constructor

The class itself copies its own fields -- including private ones:

```java
public VMInstance(VMInstance o) {
    this.os = o.os;
    this.runtime = o.runtime;
    this.monitoringAgent = o.monitoringAgent;
    this.hostname = o.hostname;
    this.ipAddress = o.ipAddress;
}

VMInstance vm2 = new VMInstance(vm1);
```

Better -- but what about **child classes**?

```java
public class GPU_VMInstance extends VMInstance {
    private String gpuType;

    public GPU_VMInstance(GPU_VMInstance o) {
        super(o);              // parent copies its fields
        this.gpuType = o.gpuType;
    }
}
```

### 1.3 The Real Problem: Which Constructor?

```java
static VMInstance createCopy(VMInstance vmInstance) {
    // vmInstance could be a VMInstance OR a GPU_VMInstance -- which constructor do we call?
}
```

The naive answer is an `instanceof` ladder:

```java
static VMInstance createCopy(VMInstance vmInstance) {
    if (vmInstance instanceof VMInstance) {
        return new VMInstance(vmInstance);
    } else if (vmInstance instanceof GPU_VMInstance) {
        return new GPU_VMInstance((GPU_VMInstance) vmInstance);
    }
    return null;
}
```

> **Correction:** the ladder in the script (lines 153-157) checks the **parent first**. A `GPU_VMInstance` *is* a `VMInstance`, so the first branch always matches and the GPU branch can never run -- copying a GPU VM silently returns a plain `VMInstance` without `gpuType` (verified). The child check must come first. (The script's version also creates `copy` as a local variable and throws it away -- it should return it.)

```
instanceof order:     parent first                    child first
GPU_VMInstance  ──>  "is a VMInstance?" YES          "is a GPU_VMInstance?" YES
                      -> new VMInstance(...)          -> new GPU_VMInstance(...)
                      gpuType LOST                    correct
```

Even in the right order, the ladder is the wrong design:

| Issue | Why |
|-------|-----|
| Readability | Grows with every subclass |
| **SRP** | The client now knows the whole class hierarchy |
| **OCP** | Every new subclass (`TPU_VMInstance`, ...) means editing this method |

> Runnable: `PrototypeEvolutionDemo` sections 1-2

---

## 2. Prototype Design Pattern

> **Prototype: create new objects by copying an existing object (the prototype / template), instead of building them from scratch.**

GoF's wording: *"Specify the kinds of objects to create using a prototypical instance, and create new objects by copying this prototype."* The script's "official definition" (line 341) is a paraphrase of this.

**Goal:** one `clone()` method that works for every class in the hierarchy.

### 2.1 Enforce It with an Interface

```java
public interface Prototype<T> {
    T clone();
}
```

```java
public class VMInstance implements Prototype<VMInstance> {
    // fields ...

    protected VMInstance(VMInstance o) {   // copy constructor
        this.os = o.os;
        // ...
    }

    @Override
    public VMInstance clone() {
        return new VMInstance(this);
    }
}

public class GPU_VMInstance extends VMInstance {
    private final String gpuType;

    protected GPU_VMInstance(GPU_VMInstance o) {
        super(o);
        this.gpuType = o.gpuType;
    }

    @Override
    public GPU_VMInstance clone() {        // covariant return type
        return new GPU_VMInstance(this);
    }
}
```

The client no longer needs a ladder:

```java
VMInstance vm3 = vm1.clone();                 // VMInstance
VMInstance vm4 = gpuVmInstance.clone();       // GPU_VMInstance -- the right clone() runs automatically
```

```
                 Prototype<VMInstance>
                   + clone()
                        ^
                        |
                   VMInstance ─────── clone() -> new VMInstance(this)
                        ^
                        |
                 GPU_VMInstance ───── clone() -> new GPU_VMInstance(this)   (override)

Client: anyVm.clone()  ──>  Java picks the override for the object's real class
```

### 2.2 Two Improvements in the Repo

| Change | Why |
|--------|-----|
| `GPU_VMInstance.clone()` returns `GPU_VMInstance` (covariant return) | If you hold a `GPU_VMInstance`, the copy needs no cast. (A reference typed as `VMInstance` still needs one.) |
| Copy constructors are `protected` | `new VMInstance(gpuVm)` compiles and silently produces a plain `VMInstance`. Making it protected forces clients through `clone()`; subclasses can still call `super(o)` |

### 2.3 Why the Child Must Override `clone()`

- The parent's `clone()` calls the **parent's** copy constructor, which knows nothing about `gpuType`.
- The child doesn't need to `implements Prototype` again -- it inherits that from the parent. It only has to **override** `clone()`.

### Quiz 1

`GpuVMInstance extends VMInstance` but forgets to override `clone()`. What happens when you call `.clone()` on a `GpuVMInstance`?

**Answer: B** -- `VMInstance`'s `clone()` runs, silently dropping `gpuType`.

More precisely, the copy isn't a `GpuVMInstance` with a missing field -- it's a plain **`VMInstance`** object (verified). So code that casts the result, like the script's client `(GPU_VMInstance) template.clone()`, fails at runtime with a **`ClassCastException`**.

### 2.4 `Object.clone()` and `Cloneable`

Java has a built-in `Object.clone()`. To use it, the class must implement the `Cloneable` marker interface -- otherwise `super.clone()` throws `CloneNotSupportedException` (verified). It copies fields **shallowly** (a `List` field ends up shared between original and copy -- verified), and it creates the copy without calling a constructor. That's why most codebases, and this class, use a copy constructor behind their own `clone()` instead.

> Runnable: `PrototypeEvolutionDemo` section 3 (Quiz 1), `Client` (covariant clone)

---

## 3. Registry Design Pattern

> **Registry: store your templates in one central place, by name, and hand out copies on request.**

(Registry is not one of the 23 GoF patterns -- it's described in Martin Fowler's *Patterns of Enterprise Application Architecture*.)

### 3.1 Why

Once you have templates -- e.g. a *Welcome* email and a *Class Reminder* email -- they need a home. Every time a student joins, clone the Welcome template, fill in their details, send. Every time a class is about to start, clone the Reminder template for each student.

### 3.2 Implementation

```java
public class VMInstanceRegistry {
    private static final Map<String, VMInstance> registry = new ConcurrentHashMap<>();

    public static void addVMInstance(String name, VMInstance instance) {
        registry.put(name, instance.clone());            // clone on the way IN
    }

    public static VMInstance getInstance(String name) {
        VMInstance template = registry.get(name);
        if (template == null) {
            throw new IllegalArgumentException("No template registered with name '" + name + "'");
        }
        return template.clone();                         // clone on the way OUT
    }
}
```

Prefill it when the application starts:

```java
static void prefillRegistry() {
    VMInstance ubuntu = new VMInstance("Ubuntu 22.04", "Docker 1.4", "Datadog", null, null);
    VMInstance redHat = new VMInstance("RedHat", "Docker 1.2", "Splunk", null, null);
    GPU_VMInstance nvidiaH100 = new GPU_VMInstance(ubuntu, "Nvidia H100");
    // ...
    VMInstanceRegistry.addVMInstance("ubuntu-basic", ubuntu);
    VMInstanceRegistry.addVMInstance("nvidiaH100-High", nvidiaH100);
    // ...
}
```

> **Correction:** `prefillRegistry()` calls `new GPU_VMInstance(ubuntu, "Nvidia H100")`, but the `GPU_VMInstance` class shown in the script (lines 114-127) has no `(VMInstance, String)` constructor -- that line doesn't compile (verified). The repo has the constructor:
> ```java
> public GPU_VMInstance(VMInstance o, String gpuType) { super(o); this.gpuType = gpuType; }
> ```

Using it:

```java
VMInstance basicServer = VMInstanceRegistry.getInstance("ubuntu-basic");
basicServer.setHostname("api-1.scaler.com");
basicServer.setIpAddress("18.243.93.102");

for (int i = 1; i <= 3; i++) {
    VMInstance cluster = VMInstanceRegistry.getInstance("nvidiaGB300-XHigh");   // a fresh GPU copy each time
    cluster.setIpAddress("10.0.0." + i);
}
```

```
 Startup                          Registry (Map)                        Clients
 prefillRegistry() ──add──> ┌───────────────────────┐
                            │ "ubuntu-basic"   -> T1 │ ──getInstance──> clone of T1 -> customise
                            │ "nvidiaGB300-.." -> T2 │ ──getInstance──> clone of T2 -> customise
 @PostMapping (runtime) ──> │ "new-template"   -> T3 │ ──getInstance──> clone of T3 -> customise
                            └───────────────────────┘
                             templates are never handed out directly
```

### 3.3 Clone on the Way Out

If the registry returns the stored object itself, the client can change the template:

```java
VMInstance basicServer2 = VMInstanceRegistry.getInstance("ubuntu-basic");   // no clone in getInstance()
basicServer2.setIpAddress("1.1.1.1");
VMInstanceRegistry.getInstance("ubuntu-basic");   // ip = 1.1.1.1 -- the template is destroyed
```

So the **registry** clones, rather than trusting every client to remember.

### 3.4 Clone on the Way In

> **Correction:** cloning on the way out isn't enough. The script's `addVMInstance` stores the caller's object as-is, so the caller still holds a reference to the live template:
> ```java
> VMInstanceRegistry.addVMInstance("ubuntu-basic", ubuntu);
> ubuntu.setIpAddress("...");     // changes the stored template too
> ```
> Verified in `PrototypeEvolutionDemo` section 5. The repo's registry clones in `addVMInstance` as well.

### 3.5 How Many Registries?

The script asks "how many Registry instances should exist?" -- one -- and answers "Singleton".

> **Correction:** the registry in the script is an all-`static` class -- there are **zero** instances, and the single static map is already shared by the whole application. That works fine. A true Singleton (an object with `getInstance()`) is the alternative when you want to pass the registry around as an object -- e.g. behind an interface, for testing or dependency injection. (Watch the name clash: the registry's `getInstance(String name)` returns a template, not the registry.)

### 3.6 Adding Templates at Runtime

After startup, new templates can be added through an API (e.g. a Spring `@PostMapping`). Then many threads read the registry while another writes to it.

> **Correction:** a `HashMap` isn't safe for concurrent reads and writes. Use **`ConcurrentHashMap`** (as the repo now does) when templates can be added at runtime.

### Quiz 2

`registry.get("gpu-training-v1")` is called, but only `"backend-server-v3"` was ever registered. What happens?

**Answer: B** -- `get()` returns `null`; the real crash (`NullPointerException`) only happens later, wherever that `null` gets used (verified).

> **Watch out:** that's true for the raw `Map.get()`. The class's own `VMInstanceRegistry.getInstance()` does `registry.get(name).clone()`, so it throws the `NullPointerException` **immediately, inside `getInstance()`** -- which is option C for that method (verified). The repo's version checks for `null` and throws a clear `IllegalArgumentException` listing the available names.

> Runnable: `Client` (final registry), `PrototypeEvolutionDemo` sections 4-6

---

## 4. AI Corner

Add your findings to your learning document (LLD point of view + AI point of view), and compare with a classmate's.

### 4.1 Activity

Prompt: *"Add a `List<String> installedPackages` field to this VMInstance class and update the clone / copy constructor accordingly."* Try it with more than one model and compare.

### 4.2 What to Look For: Shallow vs Deep Copy

**Wrong (what many models produce):**

```java
protected VMInstance(VMInstance o) {
    this.os = o.os;
    this.installedPackages = o.installedPackages;    // copies the REFERENCE
}
```

```
template.installedPackages ──┐
                             ├──> [nginx, docker]     one list, shared
clone.installedPackages   ───┘

clone.install("redis")  ->  template's list now has redis too
```

**Right:**

```java
protected PackagedVMInstance(PackagedVMInstance o) {
    this.os = o.os;
    this.installedPackages = new ArrayList<>(o.installedPackages);   // the copy gets its OWN list
}
```

| Copy type | What gets copied | Result here |
|-----------|------------------|-------------|
| Shallow | The reference to the list | Clone and template share one list -- **bug** (verified) |
| New list, same elements | A new `ArrayList` holding the same `String`s | Correct -- `String` is immutable, so sharing the elements is safe |
| Fully deep | A new list **and** a copy of every element | Only needed if the elements are mutable objects (e.g. `List<Package>`) |

> **Precision note:** the script says "ensure your model returns a **deep copy**". For `List<String>`, a new list is enough -- copying each `String` adds nothing, because `String` can't change. The rule of thumb: **every mutable object reachable from the copy must be a new object.**

Also copy in the main constructor (`new ArrayList<>(installedPackages)`) and return a read-only view from the getter -- otherwise the caller's own list leaks in or out.

> Runnable: `aicorner/ShallowCopyDemo`

---

## 5. Real Use Cases of Prototype & Registry

| Where | Template (prototype) | What changes per copy |
|-------|----------------------|-----------------------|
| Atlassian Marketplace category pages | Hero section layout | Heading, body, image |
| Amazon invoices | Invoice structure | Customer, items, amounts |
| Emails (welcome, class reminder) | Email template | Name, class details |
| Cloud (VMs, load balancers) | Standard config with security settings | Hostname, IP, size |
| Games (e.g. Plants vs Zombies-style enemies) | One configured enemy | Position, health, speed |
| Overleaf | Document templates (resume, paper) | Your content |
| ID cards | Card layout | Name, photo, ID number |
| Frontend (React) | Reusable component | Props |

> **Correction:** the script says for invoices "the structure of the invoice changes and the content is the one that just changes" (line 297). It's the other way round: the **structure stays the same**; only the **content** changes. That's exactly what makes it a template.

> **Note:** whether a specific game is actually implemented with the Prototype pattern isn't something we can verify -- treat "clone a zombie" as an illustration of the idea.

---

## Summary

| Concept | Key Point |
|---------|-----------|
| **The problem** | Copying field by field couples the client to every field; `instanceof` ladders break SRP and OCP (and the parent-first ladder silently loses child fields) |
| **Prototype** | `Prototype<T>` with `clone()`; each class copies itself via its copy constructor |
| **Child classes** | Must override `clone()`; otherwise the copy is a plain parent object (Quiz 1) |
| **Covariant return** | `GPU_VMInstance clone()` -- no cast when you hold the child type |
| **Registry** | Central map of named templates |
| **Safe registry** | Clone on the way in and out; `ConcurrentHashMap` for runtime additions; clear error for unknown names |
| **Shallow vs deep** | Every mutable object reachable from the copy must be new (a new list for `List<String>`) |

---

## Demos

| Demo | What It Shows |
|------|---------------|
| `Client` | Final Prototype + Registry: prefill, clone and customise, 3 GPU machines from one template, covariant clone, unknown name |
| `PrototypeEvolutionDemo` | Field-by-field copy, the script's ladder losing `gpuType`, Quiz 1 (`ClassCastException`), template destroyed without clone-out, template changed without clone-in, Quiz 2 (`null` then NPE) |
| `aicorner/ShallowCopyDemo` | AI activity: shared list bug vs correct copy |

---

## Next Steps (from script)

- Add this class's learnings (Prototype and Registry) to your learning document, and compare with the other doc.
- AI activity: add `installedPackages` with different models and check which ones produce a correct copy.

---
