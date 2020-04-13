---
layout: post
title:  "Abstraction - less can be more"
category: software
tags: ["software", "design", "software-design", "java"]
---

<p class="excerpt">
When it comes to abstractions, less can be more and the trade-offs you need to make may not be the ones you think.
Let me prove it.  
</p>
<span class="readmore"/>



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

By the way, this post is a continuation of the
[previous article]({% post_url 2020-04-12-are-you-imperator %})
post, so it would be beneficial to read it first.

What can you say about the method doThings() - "is it something" or does it "do something"?

```java
class Main {
    public static void main(String[] args) {
        doThings();
    }

    static void doThings() {
        System.out.println("I am now doing the laundry...");
        System.out.println("I am now walking the dog...");
        System.out.println("I am now reading a book...");
    }
}
```

Having read the previous post, you know that it "does something", it's imperative. Let's make part of it declarative.


```java
class Main {
    public static void main(String[] args) {
        doThings();
    }

    static void doThings() {
        ThingsToDo thingsToDo = thingsToDo();
        doCarefully(thingsToDo);

    }

    private static void doCarefully(ThingsToDo thingsToDo) {
        // check everything works
        thingsToDo.doIt();
        // check that everything still works
    }

    private static Main.ThingsToDo thingsToDo() {
        return new ThingsToDo() {
            @Override
            public void doIt() {
                System.out.println("I am now doing the laundry...");
                System.out.println("I am now walking the dog...");
            }
        };
    }

    interface ThingsToDo
    {
        void doIt();
    }
}
```

The doThings method is still imperative as it contains an imperative part, but it contains a declarative part as well. It is split into "a thing", represented by a value of type ThingsToDo returned
from the thingsToDo() function. ThingsToDo is not only "a thing". It is also an **abstraction**.


## An abstraction

The common agreement is that an abstraction **hides something**. Another purpose of an abstraction is that it **represents a concept**. To be more precise, it **provides a perspective** through which
two things, although different, look the same. 

The `ThingsToDo` abstraction:

- represents a concept of something that can be done

- hides what exactly is to be done

- provides a perspective of 'I look at things and decide if I can do them or not'

I will discuss the difference between a perspective and the notion of hiding in more details later.


How abstract is this abstraction? It's pretty abstract in a sense that the method `void doCarefully(ThingsToDo thingsToDo)` has absolutely no idea what it is about to do.
We hid everything apart from the fact that there is something to be done. 

Making things more abstract

- decreases the level of a client's (doCarefully method) control over the abstract thing

- increases compatibility (including future compatibility of the code that is yet to be written) and reusability of the abstraction.

As ThingsToDo is quite high on the scale of abstraction, it is pretty easy to turn anything into something that the client's code can do carefully without even knowing about it 
at the time this client's code was written. In java all it takes is implement this interface.

```java
class Main {
    static void doThings() {
        ThingsToDo funThingsToDo = funThingsToDo();
        ThingsToDo importantThingsToDo = importantThingsToDo();

        doCarefully(importantThingsToDo);
        doCarefully(funThingsToDo);

    }

    private static Main.ThingsToDo importantThingsToDo() {
        return new ThingsToDo() {
            public void doIt() {
                System.out.println("I am now paying my bills...");
                System.out.println("I am now cooking...");
            }
        };
    }

    private static Main.ThingsToDo funThingsToDo() {
        return new ThingsToDo() {
            public void doIt() {
                System.out.println("I am now reading a book...");
            }
        };
    }

}
```

if you look at the implementation of the `doThings()` method, the doCarefully method has no problem with accepting more things to do although it did not about them earlier.
The order of returning values (fun things before important things) has no effect over the order of execution (important things are done first). It is because by following **declarative
style order of execution is decoupled from the things to execute**. This is yet another compelling reason to use this style over imperative style when other concerns, such as readability and modeling the domain are important factors that affect the code layout. In **imperative style** the **code layout is constrained by the order in which you want side effects to occurr**.

## Introduction of an abstraction

See what happens when we introduce another abstraction - a task.

```java
interface ThingsToDo extends Task
{
    void doIt();
    List<Task> things();
}

interface Task
{
    void doIt();
}
```

You can still do ThingsToDo, but now you can also return a list of tasks the ThingsToDo involve. By extending Task we automatically include their methods, so `void doIt();` declaration in ThingsToDo
is actually redundant now - it overrides a method with the same method, so this line can be deleted - I just left it to show that I did not remove anything from the ThingsToDo abstraction. All I did was adding one more method and adding one more abstraction - a Task.

Another question for you. I just added one more abstraction. From a ThingsToDo client's perspective (such as doCarefully method), did I make the code more or less abstract? The answer is - I made it **less** abstract. Is it a joke? Not really, as I actually revealed more information about what is to be done. I stopped hiding certain implementation details, such as how many distinct things are to be done. Does the client have to do anything with this additional information? Absolutely not! Look at the 3 clients below.

```java
private static void doCarefully(ThingsToDo toDo) {
    // check everything works
    toDo.doIt();
    // check that everything still works
}

private static void doQuickly(Task toDo) {
    // check everything works
    toDo.doIt();
    // print toDo.tasks() <- does not compile
    // check that everything still works
}

private static void doAsMuchAsYouCan(ThingsToDo thingsToDo) {
    long startTime = System.currentTimeMillis();
    int thingsDone = 0;
    thingsToDo.things().forEach(thingToDo -> {
        if (startTime + 100 > System.currentTimeMillis()) {
            System.out.println(
                    "Sorry, come back later, done "
                            + thingsDone + " out of " +
                            thingsToDo.things().size()
            );
        }
        doQuickly(thingsToDo);
    });
}
```

- doCarefully is the old unchanged version. The perspective of this method was broadened, but introducing new abstraction did not require existing code to change.

- doQuickly decides not to care about this additional information. It's perspective is as narrow as doCarefully's perspective before we introduced the new abstraction.

- doAsMuchAsYouCan perspective is as broad as doCarefully and on top of it, this method decided to make use of the new piece of information. It does not start a new task if the previous ones
took more than 100ms and returns the control back to the caller (obviously it does not mean it will finish within 100ms)

A decision not to care about something is made not only by the thing that implements the abstraction. It is also made by a client of the abstraction. Thus, it is not only a matter of hiding something. 
It is actually a matter of perspective. **Think about a cylinder and a cuboid. They don't have to hide anything, but one perspective (frontal) make them appear the same and other (from the top) - different**.


## Free lunch

Inverting the claim made earlier, one can conclude that as we make ThingsToRun **less abstract**

- increases the level of a client's control over the abstract thing that

- decreases compatibility (including future compatibility of the code that is yet to be written) and reusability of the abstraction.

The first statement is correct, as was proven by the doAsMuchAsYouCan method. The second statement is not, as was proven by doCarefully method.

We increased the level of client's control without decreasing compatibility and reusability. How come, is it magic? We were told there is no such thing as free lunch!

Don't worry - we still have a trade-off to make. It just lies somewhere else. I will describe this trade-off shortly but for now let's discuss what made it possible to get something, as far
as clients are concerned, for free.

The secret that made is possible is that we did not simply introduce some abstraction. We **decomposed existing abstraction**. As we mentioned earlier, an abstraction represents a concept, so 
we **decomposed a concept into sub-concepts**, or **broken down a problem into sub-problems**. Problem decomposition is one of the most important skills of any software developer and it is a an indispensable part of **Domain Driven Design**, when we decompose the concepts along according to our understanding of the domain, and by doing so we also start understanding and describing the domain better. We ask domain experts questions such as: 

- is it all we know about ThingsToDo?

- is there anything that two ThingsToDo have in common apart that there is something to be done?

- When I start doing something, do I have to finish it?

- Show me two ThingsToDo that have absolutely nothing in common. Why do you still think you should still describe it using the same concept?

- etc. etc.

Once you find some abstractions, it is a good idea to follow up with more questions and try to decompose it further. It is good idea not to do it blindly, but with domain knowledge at hand. Sometimes
it is domain knowledge that drives decomposition, sometimes it is a decomposition itself that triggers more questions for domain experts.

Taking a more pragmatic angle, problem decomposition has one very important benefit - it makes you design... composable. It means that instead of writing more code to solve new problems, you can start composing solutions from subsolutions. How many solutions do you get for free when you have 3 abstraction clients and 2 abstract things?

```java
static void doThings() {
    ThingsToDo importantThingsToDo = importantThingsToDo();
    ThingsToDo funThingsToDo = funThingsToDo();

    // Solution A
    doQuickly(importantThingsToDo);
    doAsMuchAsYouCan(funThingsToDo);

    // Solution B
    doAsMuchAsYouCan(funThingsToDo);
    doQuickly(importantThingsToDo);


    // Solution C
    doQuickly(importantThingsToDo);
    doQuickly(funThingsToDo);

    // Solution D
    doAsMuchAsYouCan(importantThingsToDo);
    doAsMuchAsYouCan(funThingsToDo);

    // continues...
}
```

Or, in other words, how many lines of code and potential bugs you saved by not having to write them in the first place? Abstraction decomposition is so powerful because as the code becomes composable,
it also becomes [orthogonal](https://en.wikipedia.org/wiki/Orthogonality_(programming)) - each problem is solved once and for all and "it is associated with simplicity; the more orthogonal the design, the fewer exceptions. This makes it easier to learn, read and write programs in a programming language".


## It is still a trade-off

Of course it is a trade-off, how else. Look what it now takes to **implement** the abstraction.

```java
// before
private static ThingsToDo importantThingsToDoBefore() {
    return new ThingsToDo() {
        public void doIt() {
            System.out.println("I am now doing the laundry...");
            System.out.println("I am now walking the dog...");
            System.out.println("I am now reading a book...");
        }
    };
}

// after
private static ThingsToDo importantThingsToDoAfter() {
    return new ThingsToDo() {
        public void doIt() {
            things().forEach(ThingToDo::doIt);
        }
        public List<ThingToDo> things() {
            return asList(
                    () -> System.out.println("I am now doing the laundry..."),
                    () -> System.out.println("I am now walking the dog..."),
                    () -> System.out.println("I am now reading a book...")
            );
        }
    };
    }
```

The trade-off is hence the following.

**By decomposing abstractions you increase number of things to name when you implement the abstraction**. It can be a cost, but it is also an opportunity. So here is another trade-off.
Taking time to name things properly increases understanding, increases maintainability as it saves time in the long run, but takes more time in the short run.

The final list of features is the following.

**Abstraction**

- decreases the level of a client's control over the abstract thing

- increases compatibility (including future compatibility of the code that is yet to be written) and reusability of the abstraction.


**Abstraction decomposition**

- takes more time when implementing an abstraction

but also

- increases the level of a client's control over the abstract thing

- make the code more orthogonal, and therefore simpler and easier to learn

- decreases the number of lines of code

And finally, The conclusions I want to leave you with that occurred to me after writing this post are the following.

- **An abstraction is not as only a matter of what's hidden, it is equally a matter of the perspective**

- **Abstraction decomposition, when modeled according to the domain concepts, has more pros than cons**.




