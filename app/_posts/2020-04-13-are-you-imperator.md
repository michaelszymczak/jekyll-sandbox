---
layout: post
title:  "You are Imperator"
category: software
tags: ["software", "design", "software-design", "java"]
---

<p class="excerpt">
  A short piece on styles of programming and naming things.
</p>
<span class="readmore"/>


See the java code below.

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
"does a thing". 
The top method then "does a thing" by first retrieving it from somewhere and then doing it.
Doing a thing is described using verbs (swim, sing) and things are described using nouns (a swimming pool, a song). 
It becomes quite obvious when we look at not only a method signature (name and list of arguments), but at the return type as well. 
Top level now-renamed-to `void doThings()` and method `void doThingsToDo(ThingsToDo thingsToDo)` both return nothing. 
However, `ThingsToDo thingsToDo()` returns something. 
The last method is also a so-called [referentially transparent function](https://en.wikipedia.org/wiki/Referential_transparency), 
which means that if we know what value it would return, we can remove it and replace it with this value, 
without affecting the outcome of the program. Both other methods that return nothing are not referentially transparent. 
You can't replace it with, well, nothing, and expect that the program will behave the same. If a method does not **return a thing**, it must **do** something "on side" - side effect, otherwise it would be pointless to keep it. The top level method `void doThings()` is imperative, as it contains imperative parts and it **does something**, not **is something** as it does not return a value. It also follows [CQS](https://en.wikipedia.org/wiki/Command%E2%80%93query_separation) and it's related to
[builders and manipulators separation](https://www.yegor256.com/2018/08/22/builders-and-manipulators.html).


To wrap up, invoking methods that do something is part of imperative style. You make the computer do something for you. Declaring and returning things to do is on the other hand, you guessed it, a declarative style. It is also a cornerstone of functional programming, so you can say it's also a functional style (by the way, object oriented vs functional is a false dichotomy, the real dichotomy is imperative vs functional).
Being aware of the distinction between between "a thing" (here - ThingsToDo and the corresponding variable), and "doing a thing"
(here - doThings() and doThingsToDo() methods),
will help with naming. Better names then in turn lead to better problems modeling and designing better solutions.


## If it looks like a duck...

I've taken the last example and added new class called MoreStuff. It has `void doIt()` method, the same signature and return type as the method in the already known ThingsToDo interface.
Here is the question for you - is the Song ThingsToDo? 


```java
class Main {

    static class Song
    {
        public void static doIt() {
            System.out.println("I now sing...");
        }
    }
    
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

Well, unfortunately it is not. It's not a thing, it does something, the same way System.out.println("I am now doing the laundry...") does something.
If in doubt, look at the return type, if it's void it does something. 
You can always ask this question: If you have something for me, please give it to me, oh it's nothing (void)? the maybe you can do something for me instead?
We can make it a thing though, the same way we make a thing from System.out.println("I am now doing the laundry..."). But first let's fix the name.

```java
static class SingASong
{
    public void static doIt() {
        System.out.println("I now sing...");
    }
}
```

Then let's make it a thing following the earlier process.

```java
static class SingASong
{
    public void static doIt() {
        System.out.println("I now sing...");
    }
}

private static ThingsToDo moreThingsToDo() {
    return new ThingsToDo() {
        public void doIt() {
            SingASong.doIt();
        }
    };
}

```

We can finally use it as a thing to do

```java
static void doThings() {
    // a thing
    ThingsToDo thingsToDo = thingsToDo();
    ThingsToDo moreThingsToDo = moreThingsToDo();

    // do the thing
    doThingsToDo(thingsToDo);
    doThingsToDo(moreThingsToDo);
}
```

Since Java8 we can remove a boiler plate code by using a method reference

```java
static void doThings() {
    // a thing
    ThingsToDo thingsToDo = thingsToDo();
    ThingsToDo moreThingsToDo = SingASong::doIt;

    // do the thing
    doThingsToDo(thingsToDo);
    doThingsToDo(moreThingsToDo);
}
```

This syntax is quite convinient, but under the hood it is an equivalent of what we did manually. The syntax is in fact so convenient, that you can sometimes mistake doIt method of SingASong class for
"a thing" as opposed of "doing thing". Only `ThingsToDo moreThingsToDo = SingASong::doIt` is a thing assigned to a variable, not the method on the SingASong class.

## Why should I care?

If you have just spent 5 minutes of your life reading this to discover that verbs are not nouns and to "do something" is not the same as to "be something", and it bore no fruits, you would have every right to be disapointed. And believe me, I would be more disapointed as it would mean that I wasted 3 hours writing it.

So this is what I really want to tell you. This distinction makes all the difference. Nouns/things make for great building blocks of a composable software, verbs are important and neccesary parts, but are also fraught with perils. It's so important distinction that the whole patterns and paradigms, such as functional programming were built around it as even one verb can destroy the whole tree of nouns and render the whole thing unusable. Quoting the Joe Armstrong, creator of Erlang, you wanted a banana but what you got was a gorilla holding the banana and the entire jungle. Last but not least, this distinction helps with names.

