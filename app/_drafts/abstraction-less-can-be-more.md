---
layout: post
title:  "Abstraction - less can be more"
category: software
tags: ["software", "design", "software-design", "java"]
---

```bash
$ echo "foo" > input.txt && \
  less input.txt > a && \
  more input.txt > b && \
  diff a b && \
  echo "same" || echo "different"

same
```

&#8718; Q.E.D.

On a more serious note, see the java code below.

```java
class Main {
    public static void main(String[] args) {
        thingsToDo();
    }

    static void thingsToDo() {
        System.out.println("I am now doing the laundry...");
        System.out.println("I am now walking the dog...");
        System.out.println("I am now reading a book...");
    }
}
```

Imagine that by invoking the line System.out.println("I am now doing the laundry...") your computer actually does the laundry,
not only printing it on the screen, then it walks the dog and in the end it reads a book. This style is
called imperative programming. You were an [imperator](https://en.wikipedia.org/wiki/Imperator)
and you made your computer do the above mentioned things.

Now, please look at a different implementation.

```java
class Main2 {
    public static void main(String[] args) {
        thingsToDo();
    }

    static void thingsToDo() {
        // 1: a thing
        ThingsToDo thingsToDo = new ThingsToDo() {
            public void doIt() {
                System.out.println("I am now doing the laundry...");
                System.out.println("I am now walking the dog...");
                System.out.println("I am now reading a book...");
            }
        };

        // 2: do the thing
        thingsToDo.doIt();
    }

    interface ThingsToDo {
        void doIt();
    }
}
```

We introduced an interface that represents things to do and we divided the thingsToDo method implementation into sections.
The section '// 1:' declares things to do. When you reach
the section '// 2:' you make the computer do the things to do that you defined in section '// 1:'. 
It is just a more fancy way of making your computer do the same things as in the first example. 


In the implementation below I extracted the section 1 and 2 into their own methods.

```java
class Main3 {
    public static void main(String[] args) {
        doThings();
    }

    static void doThings() {
        // a thing
        ThingsToDo thingsToDo = thingsToDo();

        // do the thing
        doThingsToDo(thingsToDo);
    }

    private static ThingsToDo thingsToDo() {
        return new ThingsToDo() {
            public void doIt() {
                System.out.println("I am now doing the laundry...");
                System.out.println("I am now walking the dog...");
                System.out.println("I am now reading a book...");
            }
        };
    }

    static void doThingsToDo(ThingsToDo thingsToDo) {
        thingsToDo.doIt();
    }

    interface ThingsToDo {
        void doIt();
    }
}
```

By extracting the methods, It became clear that the `static void thingsToDo()` method name from the previous example `Main2` was incorrect.
The method is not "a thing", it "does a thing". "ThingsToDo" type represents "a thing", `ThingsToDo thingsToDo = thingsToDo();` assigns "a thing" to the variable and `doThingsToDo(thingsToDo);`
"does a thing". The top method then "does a thing" by first retrieving it from somewhere and then doing it. It becomes quite obvious when we look at not only a method signature (name and list of arguments), but at the return type as well. Top level now-renamed-to `void doThings()` and method `void doThingsToDo(ThingsToDo thingsToDo)` both return nothing. However, `ThingsToDo thingsToDo()` returns something. The last method is also a so-called [referentially transparent function](https://en.wikipedia.org/wiki/Referential_transparency), which means that if we know what value it would return, we can remove it and replace it with this value, without affecting the outcome of the program. Both other methods that return nothing are not referentially transparent. You can't replace it with, well, nothing, and expect that the program will behave the same. If a method does not **return a thing**, it must **do** something "on side" - side effect, otherwise it would be pointless to keep it. The top level method `void doThinds()` is imperative, as it contains imperative parts and it **does something**, not **is something** as it does not return a value. It also follows [CQS](https://en.wikipedia.org/wiki/Command%E2%80%93query_separation). 


To wrap up, invoking methods that do something is part of imperative style. You make the computer do something for you. Declaring and returning things to do is on the other hand, you guessed it, a declarative style. It is also a cornerstone of functional programming, so you can say it's also a functional style (by the way, object oriented vs functional is a false dichotomy, the real dichotomy is imperative vs functional).
