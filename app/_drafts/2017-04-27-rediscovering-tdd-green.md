---
layout: post
title:  "Rediscovering TDD - Green is new Red"
category: software
tags: ["software-craftsmanship", "tdd", "test-driven-development"]
---

<p class="excerpt">
In the previous post I promised that I would show how I do TDD. I am doing it in this blog.
It is an advance TDD in a sense that in order to appreciate it fully, one should have probably tried various approaches, be aware
of some shortcomings when picking either classicist or mockist approach etc. At the same time, this blog should be understood even by beginners
as this is yet another way of doing TDD. Nothing prevents a person that has never tried TDD to apply this particular approach, even if they
do not know the difference between above mentioned classicists and mockists. 
</p>
<span class="readmore"/>


My friend and former colleague organised a meeting during which we were asked to solve a diamond problem. I realised that
this may be a nice example showing my way of TDD. Requirement-wise, it is quite easy to understand. Implementation-wise, is complex enough
to be useful when presenting TDD. The solution I came up with is not necessarily the best one (define 'best'), but it is not about the solution as the problem has been
 already solved many times. As always in code katas, the journey is more important than the destination.
 
I want to show you my approach commit by commit, highlighting and commenting interesting pieces. Feel free to check out the project 
[kata-diamond](https://github.com/michaelszymczak/kata-diamond/tree/master) .

The problem is the following.

Given a letter, print a diamond starting with 'A' with the supplied letter at the widest point.

    
<pre>
#  Example: diamond 'C' prints
      A
     B B
    C   C
     B B
      A
</pre>


## TDD - recipe

You have probably heard this many times. The process is the following.

1. Write just enough new test code to make the test fail (Red)
2. Write just enough production code to make the tests pass. (Green)
3. Refactor - improve the design, without changing the observable (tested) behaviour.

As simple as that. However, there are many habits that make or break this approach.
Taking small steps (the 'just enough' phrase) is probably the most important one. Spending hours
on writing tests and after that writing production code is not TDD. It may be test-first, but definitely
not test-driven, as each test should drive the implementation. This is a fine-grained version of being agile, i.e.
as soon as you realise that the solution is not fit for purpose, being able to change the direction - and not even a second later.

The challenge is to make this happen - to **find a path from red to green to refactor, spending on each step no more that a minute or so**.
On top of that, you have to **repeat this sequence time and time again until you solve the problem**.
 
> If you had to spend no more than one minute on each step and at the same time deliver a fully working, correct solution, that would 
> change the way you code, wouldn't it.

Let's see why quick feedback loop and swiftly jumping from red to green and to refactor is important.

- If you spend too much time on any of the steps, you loose the sense of progress, that keeps your brain focused and interested (applies to all steps).
- If you spend too much time on Red (writing/changing production code) you risk introducing untested code. In addition, as the transition to
the next implementation is not trivial, it is easy to make some mistake and introduce a bug. You also risk introducing the unnecessary code
that you will have to maintain later. Each line of code costs time and money and makes future you poorer and more busy. On top of that, more time you spend
on Red, more likely it is that you will never manage to come back to Green and you will be forced to throw away all new code you created.
- If you spend too much time on Refactoring it either means that you gold-plating or make you code more generic that it has to be, or that you have postponed it
to much and should have done it earlier. The cheapest refactoring time is right after Red turned to Green and it should not take long. If you fail to do that, it will become more and more costly
 as it will not be that obvious where to start and, what may be even more important, the messy code has already slowed you down. Keeping you codebase clean
all the time is the quickest, cheapest and most pleasant way of writing software. The very existence of the 'quick and dirty' versus 'slower but clean' dilemma
originates from the fact that so many mistakes and unjustified 'quick and dirty' design crimes have been already made that everyone is less and less eager to
say no, take a blame, and fix it. As I said, refactor swiftly right after Red turned to Green and you will be happier, more agile and guilt-free.

## Preparatory steps

TDD is a great tool, but it may not always be the best idea to skip all the preparatory work. I observed that allocating just-enough time for some preparatory tasks makes the implementation
phase smoother. Just-enough varies, based on the domain, technology, your experience etc. Spending up to 10% on refining requirements and sketching/discussing the design is nothing unusual.
It obviously does not need to be done in one go, but rather on an ad-hoc basis and subject to stakeholders availability. 

### Clarifying requirements

Sometimes we have no idea what to build. In this case, refine the requirement using techniques such as Specification By Example @llink. Draw pictures, talk with stakeholders,
do whatever it takes to get it. The technique I use most often is paraphrasing and writing some examples. It highlights the discrepancies between my understanding and the actual requirements.

### Spiking a solution

You may also not be clear if you are able to build it - the right library may not exists, vendor's API is poorly documented etc. In this case, you should probably start with spike, hacking your way as you go.
You will test this solution picking the cheapest and quickest way, which is ofter manual testing or some ad-hoc created test with questionable design. Once you found the solution, ~~you deploy it and go home~~,
you establish boundaries allowing you to quickly create an end to end test automatically verifying that the solution still works. At this point the outcome may be one of the following.

 - Case A - I didn't even bother to do a spike as I am already confident that technology is not a problem.
 - Case B - There were one or two places that I wasn't confident about, but I narrowed them down and the rest is not a problem, technology-wise. The good example is that there was some undocumented part of API that
  required you to pass a custom header in order to get the response. Had you known that, you wouldn't have bothered doing a spike.
 - Case C - The whole solution is still a mystery for you. You are afraid to change anything as it may turn a working solution into a useless one.
 
After you know what to build and that you can build it using the tools of your choice, the next one is the design and implementation. In case A, when the technology does not pose any challenge for you,
there is nothing you have to do before the design and implementation phase. In case B and C you may want to reuse some or all of the existing code, especially in case C when you risk not being able to reproduce the
expected behaviour if you started from scratch again. You can, instead, apply series of refactoring steps to improve the design and create more tests as you go. In case B, you can isolate tricky parts, create tests for them
and re-use in the new design.
 
### Sketching and modeling 
 
If the domain or the problem itself is non-trivial, it may be advisable to think about the design beforehand. There are various techniques you can apply, such as @link https://en.wikipedia.org/wiki/Class-responsibility-collaboration_card
or https://en.wikipedia.org/wiki/Domain-driven_design . If you are confident that TDD and its feedback loop alone is sufficient for the problem in hand and you have proven track of implementing clean solutions
using TDD, you most probably know how just-enough design should look like in this context.
 
### Design and implementation phase 

As I mentioned earlier, in this blog I am implementing the diamond-kata. As many other katas, it is a perfect example of  Case A problem, i.e. technology side is not an issue.
The main focus is on design and implementation phase. I hope that I convinced you that when applying TDD, it is important to keep the pace. Before we move on and see my implementation,
it is important that you try it on your own, especially when you are already familiar with TDD. I am addressing some of the pain-points of the most popular existing TDD approaches, so it
would be nice if you experienced them on your own before I show you my approach.


## TDD - constraints

My main focus is to maintain a certain pace, moving from Red to Green to Refactor smoothly and without any risky leaps into unknown
that would question the correctness of the implementation. In other words, the correctness of the solution is achieved by starting with the correct solution
for the sub-problem and applying a series of trivial transformations (that you can easily explain and reason about) that end when the solution for the whole problem is found.

The first approach I tried was the Classicist TDD. When you see some examples of Classicist TDD, the smooth transitions are achieved by doing it bottom-up - small chunks are implemented first
and the final solution bottoms up. There is a catch though - I want the approach to be outside-in, so that we are sure that we solve the right problem. It means that we should start with the test that is as close to the original requirement as possible.
In that sense, it is closer to what some describe as BDD @link, but for me the **outside-in approach is the most cost-effective one, as existence of each piece of logic is justified by some requirement**.
If each piece of logic is justified by requirements, there is no un-justified code, that costs time and money, bringing no value. Compare it to the bottom-up approach, when we may end up creating
some unnecessary code that, in the best case, will be thrown away or, in the worst case, we will try to keep 'just for case' or use somewhere because we can't admit that we wasted time writing it in a first place.
 
Quick pace and outside-in approach are two benefits of the Mockist TDD @link . You may think that I should simply use this approach then. There is another catch though. I believe that mocks should be used
as a last resort and excessive use of mocks is an anti-pattern. By Mocks I mean mocked subset of class API, such as ```Mockito.when(query.executeQuery()).thenReturn(rs);```.
Why this is a bad idea deserves a separate blog post. If you are one of the Mockist TDD practitioners and wonder why sometimes when you refactor the production code many tests turn red, even though the solution
 is still correct; or worse even, when while refactoring you introduced a bug but all of them are still green - you know what I mean. It is so common, that you may even have convinced yourself that it is OK to
refactor on Red, and this gambling is a price that you are willing to pay for the sake of a better design. There is a reason why mock-based tests panic or stay calm when the shouldn't though - they are not real,
 they only pretend that they know what to do. **A mock is like an incompetent teacher that fails a student if their answer differs from the one found in the answer-key, despite of being correct.**

## Maybe test recycling?

To sum up, outside-in approach, swift transition from Red to Green to Refactor and Mocks as a last resort. Is it even possible? It seems that not without a bit of creativity.
The first approach is the one presented by [@sebrose](https://twitter.com/sebrose) in the blog post [Recycling tests in TDD](http://claysnow.co.uk/recycling-tests-in-tdd/) .
It is a quick blog post, and worth reading simply to see if you like it. 
 
Although I find the approach with incremental test refining interesting, if the same could be
achieved while still keeping old tests, I would definitely prefer it.
If you have ever climbed in your life (I haven't), you probably remember
the checkpoints you lock to climbing higher and higher (if you are still alive, you probably do).
When you have made mistake, and either want to pick a different root, or you simply have fallen,
this checkpoint guarantees that you don't fall any lower than necessary. It is also
important to be locked to at least one checkpoint close to you, at any given time.
Otherwise you risk falling long distance, and effectively dying.

Let's now look at the test rewriting approach. A final, correct implementation is our peak we climb.
Any intermediate solution satisfying more requirements is an equivalent of being higher.
Being higher, however, does not necessarily mean being closer to the final solution.
Sometimes we encounter an obstacle preventing us from progressing and we
have to take a step back to find another route.

Let's imagine what happens, when we need to retreat, take a few steps back and take another route.
It's a quite common case, especially when we design an algorithm for the first time and we do it
by test driving it.

In the traditional approach, when you keep previous tests and always run all of them, your safety check
is your previous test that you wrote. Even if it turned out that your last 5 steps were in the wrong direction,
you remove the last 5 tests, one by one, letting yourself to gracefully reach the place that enables you to take
the another route. You cannot fall lower than you planned.

Conversely, when you keep rewriting one and the same test, you climb higher and higher, with your last
checkpoint being also your only one. If it turned out, that our last 5 steps were in the wrong direction,
you simply, well, jump. Which is an equivalent of abandoning the idea and starting from scratch.
You have no previous checkpoint, let alone last five. 
You can always use version control, if you are lucky to have a habit of committing often, but
this option is easily available only right after you finished the last step. Imagine that you have
to improve the algorithm you worked on a month or a year ago and you realize that some aspects
of the solution are not fit for purpose any more - good luck with browsing commit history
and trying to revert some changes. You jump.

## My approach

I have just described the dark side of the test recycling and the reason why I normally do not use this approach. Let's come up with something safer.
If you look at the reason why someone even considered taking the rewriting approach, they are the following.

- going straight to the final solution is too risky with too many intermediate steps skipped, but
- requirements expressed by the intermediary steps contradict the final solution, i.e. all tests cannot be green at the same time

If we manage to fix the latter, we will be able to keep all the tests and solve the problem incrementally. Easier said then done, but the very blog
post you are reading was created to help you to achieve exactly that. Keep reading.


### Diamond kata - my way of TDD

Just to refresh the memory, the requirement is the following.

Given a letter, print a diamond starting with 'A' with the supplied letter at the widest point.
    
<pre>
#  Example: diamond 'A' prints
      A

#  Example: diamond 'B' prints
      A
     B B
      A

#  Example: diamond 'C' prints
      A
     B B
    C   C
     B B
      A
</pre>

As you remember, we do outside-in TDD, so the first test must be derived from the requirements, ideally as-is.

To create easy to maintain, flexible and readable tests I use [Spock framework](http://spockframework.org/) and write them in [Groovy](http://groovy-lang.org/)


```groovy
package com.michaelszymczak.diamond

import spock.lang.Specification

class DiamondAcceptanceTest extends Specification {

  def "contains one letter if it is the first letter"() {
    expect:
    Diamond.of('A' as char).rendered() == "A"
  }
}
```

I run all the tests (only one at the moment) - it's Red,  so wee need to introduce just enough production code to make it pass, and do it quickly!

For back-end production code, I usually use Java 8, as in some cases performance predictability is more important than flexibility. It is also much easier achieve a productivity boost and to convince
a company or a team to use Spock with Groovy if it does not require changing the production stack.

```java
package com.michaelszymczak.diamond;

public class Diamond {
  public static Diamond of(char letter) {
    return new Diamond();
  }

  public String rendered() {
    return "A";
  }
}
```

Run the test. It's Green now. Any refactoring? Not now. Commit!

The corresponding revision can be found [here](https://github.com/michaelszymczak/blog-support/commit/bfb5ecf3eb5711ca6baf7a58eacac996c3dfc868)

"It's cheating!" - one may say - "There is no logic, just some hardcoded value!". It's true, and it is intentional. If we are able to satisfy all
the requirements quickly and easily, the whole codebase benefits from it. We know that there are new requirements coming, that will make this naive solution insufficient,
 but NOW this is all we have to do. Let me repeat it - we should not write any code unless necessary. Nobody pays us for lines of code we write (I hope), but for solving problems in the most quick and maintainable way.
 As this test is an end to end one, at some point it may evolve into something similar to [Walking Skeleton](http://wiki.c2.com/?WalkingSkeleton).
 
Let's do the next cycle.

```groovy
class DiamondAcceptanceTest extends Specification {

  def "contains one letter if it is the first letter"() {
    expect:
    Diamond.of('A' as char).rendered() == "A"
  }

  def "creates diamond shape if more than one letter"() {
    expect:
    Diamond.of('B' as char).rendered() == " A " + "\n" +
                                          "B B" + "\n" +
                                          " A "

  }
}
```

I took another example from the requirements and created the next test. When I run both of them, the outcome is Red. Let's quickly write some production code to make it Green again.

```java
public class Diamond {

  private char letter;

  public static Diamond of(char letter) {
    return new Diamond(letter);
  }

  private Diamond(char letter) {
    this.letter = letter;
  }

  public String rendered() {
    return letter == 'A' ? "A" : " A \nB B\n A ";
  }
}
```

I split the execution path based on the input letter. This was the quickest way that came to my mind. When I run the tests, they are Green again. The corresponding revision can be found [here](https://github.com/michaelszymczak/blog-support/commit/8df0bc226c04bd34b968cff4c37676670ab0b929)

As we are on Green, we can do some Refactoring. I decided to change the input parameter to enum, as my understanding is that we should not support unknown characters. I basically whitelist the allowed inputs.

```java
package com.michaelszymczak.diamond;

public enum Letter {
  A, B
}
```

```java
class DiamondAcceptanceTest extends Specification {

  def "contains one letter if it is the first letter"() {
    expect:
    Diamond.of(Letter.A).rendered() == "A"
  }

  def "creates diamond shape if more than one letter"() {
    expect:
    Diamond.of(Letter.B).rendered() == "" +
            " A " + "\n" +
            "B B" + "\n" +
            " A "
  }
}
```

You can see the full commit [here](https://github.com/michaelszymczak/blog-support/commit/0d65319b95ed24d9d62f38d9a91776acc71423cd).

### Decision time

It's time to introduce something more sophisticated, otherwise our solution would have almost as many ifs as there are letters in the alphabet. As this is an algorithmic kata,
we could use the [Transformation Priority Premise](https://8thlight.com/blog/uncle-bob/2013/05/27/TheTransformationPriorityPremise.html) to guide us when writing the tests.
The approach I want to show you is, in my opinion, slightly simpler to apply, and, on top of that, it can be used in any context, not necessarily an algorithmic one.

<p>
</p>
