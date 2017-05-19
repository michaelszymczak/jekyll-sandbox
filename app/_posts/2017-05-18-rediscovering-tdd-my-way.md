---
layout: post
title:  "Rediscovering TDD - My favourite flavour of TDD"
category: software
tags: ["software-craftsmanship", "tdd", "test-driven-development", "java", "java8", "spock", "groovy"]
---

<p class="excerpt">
As promised in the previous post, it's time to show how I do TDD.
It is an advanced TDD blog post in a sense that in order to appreciate it fully, one should have probably tried various approaches, be aware
of some shortcomings when picking either classic or mockist approach etc. At the same time, this blog should be understood even by beginners
as this is yet another way of doing TDD. Nothing prevents a person that has never tried TDD to apply this particular approach, even if they
do not know the difference between above mentioned classical and mockist TDD. In fact, I think that the industry as a whole would
be much better off if the first style of TDD that newcomers come across was the one presented here.
</p>
<span class="readmore"/>


My friend and former colleague organised a meeting during which we were asked to solve a diamond problem. I realised that
this may be a nice example showing my way of TDD. Requirement-wise, it is quite easy to understand. Implementation-wise, is complex enough
to be useful when presenting TDD. The solution I came up with is not necessarily the best one (define 'best'), but it is not about the solution as the problem has been
 already solved many times. As always in code katas, the journey is more important than the destination.
 
I want to show you my approach commit by commit, highlighting and commenting interesting pieces.

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
3. Refactor - improve the design, without changing the observable behaviour.

As simple as that. However, there are many habits that make or break this approach.
Taking small steps (the 'just enough' phrase) is probably the most important one. Spending hours
on writing tests and only after that writing production code is not TDD. It may be test-first, but definitely
not test-driven, as each test should drive the implementation. TDD is a fine-grained version of being agile, i.e.
constantly listening to the feedback and as soon as you realise that the solution is not fit for purpose, you stop and change the direction,
or refactor to allow yourself to take another path.

The challenge is to make this happen - to **find a path from red to green to refactor, spending on each step no more than a minute or so**.
On top of that, you have to **repeat this sequence time and time again until you solve the problem**.
 
> If you had to spend no more than one minute on each step and at the same time deliver a fully working, correct solution, that would 
> change the way you code.

Let's see why quick feedback loop and swiftly jumping from red to green and to refactor is important.

- If you spend too much time on any of the steps, you loose the sense of progress, that keeps your brain focused and interested (applies to all steps).
- If you spend too much time on Red (writing/changing production code) you risk introducing untested code. In addition, as the transition to
the next implementation is not trivial, it is easy to make some mistake and introduce a bug. You risk introducing the unnecessary code (be it 'correct' or not)
that you will have to maintain later. Each line of code costs time and money and makes future you poorer and more busy. On top of that, more time you spend
on Red, more likely it is that you will never manage to come back to Green and you will be forced to throw away all new code you created.
- If you spend too much time on Refactoring it either means that you gold-plating or make you code more generic that it has to be, or that you have postponed it
to much and should have done it earlier. The cheapest refactoring time is right after Red turned to Green and it should not take long. If you fail to do that, it will become more and more costly
 as it will not be that obvious where to start and, what may be even more important, the messy code has already slowed you down. Keeping you codebase clean
all the time is the quickest, cheapest and most pleasant way of writing software. The very existence of the 'quick and dirty' versus 'slower but clean' dilemma
originates from the fact that so many mistakes and unjustified 'quick and dirty' design crimes have been already made that everyone is less and less eager to
say no, take a blame, and fix it. As I said, refactor swiftly right after Red turned to Green and you will be happier, more agile and free from any guilt.

## Preparatory steps

TDD is a great tool, but it may not always be the best idea to skip all the preparatory work. I observed that allocating just-enough time for some preparatory tasks makes the implementation
phase smoother. Just-enough varies, based on the domain, technology, your experience etc. Spending up to 10% on refining requirements and sketching/discussing the design is nothing unusual.
It obviously does not need to be done in one go, but rather on an ad-hoc basis and subject to stakeholders availability. 

### Clarifying requirements

Sometimes we have no idea what to build. In this case, refine the requirement using techniques such as [Specification By Example]({% post_url 2013-12-23-specification-by-example %}). Draw pictures, talk with stakeholders,
do whatever it takes to get it. The technique I use most often is paraphrasing and writing some examples. It highlights the discrepancies between my understanding and the actual requirements.

### Spiking a solution

You may also not be clear if you are able to build it - the right library may not exists, vendor's API is poorly documented etc. In this case, you should probably start with spike, hacking your way as you go.
You will test this solution picking the cheapest and quickest way, which is often manual testing or some ad-hoc created test with questionable design. Once you found the solution, ~~you deploy it and go home~~,
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
 
If the domain or the problem itself is non-trivial, it may be advisable to think about the design beforehand. There are various techniques you can apply, such as [CRC cards](https://en.wikipedia.org/wiki/Class-responsibility-collaboration_card)
or [DDD](https://en.wikipedia.org/wiki/Domain-driven_design). If you are confident that TDD and its feedback loop alone is sufficient for the problem in hand and you have proven track of implementing clean solutions
using TDD, you most probably know how just-enough design should look like in this context.
 
### Design and implementation phase 

As I mentioned earlier, in this blog I am implementing the diamond-kata. As many other katas, it is a perfect example of  Case A problem, i.e. technology side is not an issue.
The main focus is on design and implementation phase. I hope that I convinced you that when applying TDD, it is important to keep the pace. Before we move on and see my implementation,
it is important that you try it on your own, especially when you are already familiar with TDD. I am addressing some of the pain-points of the most popular existing TDD approaches, so it
would be nice if you experienced them on your own before I show you my approach.


## TDD - constraints

My main focus is to maintain a certain pace, moving from Red to Green to Refactor smoothly and without any risky leaps into unknown
that would question the correctness of the implementation. In other words, the correctness of the solution is achieved by starting with the correct solution
for the sub-problem and applying a series of trivial transformations (that you can easily explain and reason about) that ends when the solution for the whole problem is found.

The first approach I tried was the Classical TDD. When you see some examples of Classical TDD, the smooth transitions are achieved by doing it middle out, or bottom up - smaller chunks are implemented first
and the final solution is created by making use of just created components. There is a catch though - I want the approach to be outside-in, so that we are sure that we solve the right problem.
It means that we should start with the test that is as close to the original requirement as possible.
In that sense, it is closer to what some describe as [BDD](https://en.wikipedia.org/wiki/Behavior-driven_development), but for me the **outside-in approach is the most cost-effective one, as existence of each piece of logic is justified by some requirement**.
If each piece of logic is justified by requirements, there is no unjustified code, that costs time and money, bringing no value. Compare it to the middle out/bottom up approach, when we may end up creating
some unnecessary code that, in the best case, will be thrown away or, in the worst case, we will try to keep 'just for case' or use somewhere because we can't admit that we wasted time writing it in a first place.
 
Quick pace and outside-in approach are two benefits of the [Mockist TDD (scroll to 'Outside-In' section)](https://codurance.com/2015/05/12/does-tdd-lead-to-good-design/) . You may think that I should simply use this approach then. There is another catch though. I believe that mocks should be used
as a last resort and excessive use of mocks is an anti-pattern. By Mocks I mean mocked subset of class API, such as ```Mockito.when(query.executeQuery()).thenReturn(rs);```.
Why this is a bad idea deserves a separate blog post. If you are one of the [Mockist TDD practitioners](https://martinfowler.com/articles/mocksArentStubs.html#ClassicalAndMockistTesting) and wonder why sometimes when you refactor the production code many tests turn red, even though the solution
 is still correct; or worse even, when while refactoring you introduced a bug but all of them are still green - you know what I mean. It is so common, that you may even have convinced yourself that it is OK to
refactor on Red, and this gambling is a price that you are willing to pay for the sake of a better design. There is a reason why mock-based tests panic or stay calm when the shouldn't though - they are not real,
 they only pretend that they know what to do. **A mock is like an incompetent teacher that fails a student if their answer differs from the one found in the answer-key, despite of being correct.**

## Test recycling for the rescue?

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

After describing the dark side of the test recycling and the reason why I normally do not use this approach, let's come up with something safer.
If you look at the reason why someone even considered taking the rewriting approach, they are the following.

- going straight to the final solution is too risky with too many intermediate steps skipped, but
- requirements expressed by the intermediary steps contradict the final solution, i.e. all tests cannot be green at the same time

If we manage to fix the latter, we will be able to keep all the tests and solve the problem incrementally. Easier said than done, but the very blog
post you are reading was created to help you to achieve exactly that. Keep reading.

# TDD - my way

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

I run all the tests (only one at the moment) - it's Red,  so we need to introduce just enough production code to make it pass, and do it quickly!

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

```java
public class Diamond {

  private Letter letter;

  public static Diamond of(Letter letter) {
    return new Diamond(letter);
  }

  private Diamond(Letter letter) {
    this.letter = letter;
  }

  public String rendered() {
    return letter == Letter.A ? "A" : " A \nB B\n A ";
  }
}
```

You can see the full commit [here](https://github.com/michaelszymczak/blog-support/commit/0d65319b95ed24d9d62f38d9a91776acc71423cd).

### Stay on Green

To practice TDD, one must be focused and disciplined. There are two cases when existing Green test can become a Red one. It is when we introduced a bug, or there was a bug/deprecated requirement in the test that became
visible once we introduced new piece of production logic.
If any of the Green tests turns Red because you have introduced a bug, you quickly revert the last change to be on Green again and come up with a new, this time correct, solution.
If it turned red because you have found requirement that is not valid any more, you quickly revert the last change to be on Green again and look for a safer path that involves deprecation phase and supporting both old and new solution,
removing the old test, and finally removing the deprecated code. There is no need to touch Red at all. Let me repeat it - once a test became green, it stays this way, until it is removed.
You can think of modifying the behaviour that the test expects as of removing the old one and introducing a new one. If the change is small, you may win and be on Green soon enough, but if you can't do it in less
than one minute, you probably save time by doing it the right way, that I described above.
Another rule is that there can be only one Red test at a time - one that we have just written to express new requirement we are about to implement. It's quite strict, I know, but it is for a reason.
Being on Green gives you much more feedback than being on Red. If you think about a software as a complex function transforming some input to some output, there is an infinite number of combinations 
in which you can implement it. If your application is deterministic, for each input there is one correct output and infinite number of incorrect outputs. If you add 1 to 0, there is only one correct answer, which is 1.
There are many incorrect answers, starting from minus infinity and ending at plus infinity, excluding number 1. Being on Red means being somewhere between minus infinity and plus infinity, but not 1. Being on Green means
being on 1. If you compare those two sets, you can easily notice that Green gives you precise answer, whereas Red gives you almost no answer whatsoever. If you stay on Green, you know exactly where you are. If you are on Red,
you have no idea if you are close to the actual requirements, or you are as far as you could be. The only useful thing about Red is the moment when it becomes one. On it's own, being on Red gives you nothing.
The next useful moment is when it turns Green again. That is why xUnit test frameworks do not display anything when they are on Green and can be quite verbose when they are on Red - it is because Red on it's own gives you nothing.
Conversely, Green on its own gives you a lot. You know exactly where you are and, regardless of what you do (i.e. refactor), you stay exactly there. Green saves your time, Red wastes it. Red is like a lava.
Once you touch it, you need quickly jump back on the grass again. Not by jumping further hoping that you will eventually find some new land with grass (you may be dead by then), but by jumping back at the place when you
knew that was green (which as an equivalent of reverting the last changes). Reverting changes is not a popular choice among developers. Remember, however, that you don't have to revert more than 60 seconds of your work.
If you do, revert what you have already learned and start reading this blog post from the start again.

Long story short, if you want to be much more productive when practicing TDD, try to be on Green almost all the time. Avoid debugging, reasoning about failed assertions and, at all cost, avoid flaky tests
(I was a bit disappointed when I [read](https://testing.googleblog.com/2017/04/where-do-our-flaky-tests-come-from.html), that at Google around 1.5% of their tests are flaky over the course of a week.
In the current project I've been working for one year, this number is still 0%, and this should be you target as well.) 

### Overcoming dead-ends

Let's go back to our Diamond kata. It's time to introduce something more sophisticated, otherwise our solution would have almost as many ifs as there are letters in the alphabet. As this is an algorithmic kata,
we could use the [Transformation Priority Premise](https://8thlight.com/blog/uncle-bob/2013/05/27/TheTransformationPriorityPremise.html) to guide us when writing the tests (try it, it's an excellent exercise).
The approach I want to show you is, in my opinion, slightly simpler to apply, and, on top of that, it can be used in any context, not necessarily an algorithmic one.

As I mentioned earlier, all the tests I have already wrote should stay Green and acceptance tests are no exception. I can't simply add more and more if statements, so now is the right time to think about the overall design.
It's clear that in order to progress, some refactoring is necessary. I noticed that I struggled, because the Diamond class has too many responsibilities. The responsibilities are hidden behind the simple output string,
but if you look closely at your thought process when you reason about the solution, you can discover, that you:

- make sure that the right letters are present
- calculate the positions of each letter
- format the output

The current design is not future-proof, because it simply ignores the above mentioned concerns. The best way to make them explicit is to delegate them to separate classes. 
In Mockist TDD, the outside-in approach leads to the delegation of some responsibility to the classes that are yet to be created and using some Mocks instead to pretend that this part of functionality
is already done. This way our existing tests are still Green. However, they couple our tests with the implementation and make the later refactoring on Green impossible, as the tests expecting certain interaction fail
when we change the way we interact with other classes. The only way I can accept interaction based testing is when this is an intermediate step helping to shape the desired design
and the Mocks are eventually replaced with the real objects, not to hinder the future refactoring. Instead of using Mocks, I will sketch the desired design.

```
// pseudocode
public class Diamond {
  // ...
  public String rendered() {
    return if (letter == A) then
      board with  A on (0,0)
    else
      board with     A on (0,1)
              B on (1,0) , B on (1,2)
                    A on (2,1)
  }
}
```

To dream about being able to compile it from Java, we must be slightly more verbose. After rewriting it in Java the desired (and not yet existing) design is the following. 
  
```java
public class Diamond {
  // ...
  public String rendered() {
    return letter == A ? new Board(new PositionedLetter(ofYX(0,0), A)).toString()
            : new Board(
            new PositionedLetter(ofYX(0,1), A),
            new PositionedLetter(ofYX(1,0), B), new PositionedLetter(ofYX(1,2), B),
            new PositionedLetter(ofYX(2,1), A)
    ).toString();
  }
}
```

The design above would enable Diamond to delegate one of the responsibilities, namely formatting the output, to the newly created Board class. Both deciding which letters should be used and the layout of the letters
are still responsibilities of the Diamond class. However, because we don't have Board class yet, in order to keep all the tests Green we can't use this design yet.

The difference between the current and desired design should be small, and you should be confident that it can be easily achieved, ideally in one TDD cycle.
The one presented above is slightly too big to achieve it in one go, so I will split it into more steps. Let's achieve something smaller first. My achievable desired design is the following.

```java
public class Diamond {
  // ...
  public String rendered() {
    return letter == A ? 
      new Board(new PositionedLetter(ofYX(0,0), A)).toString() :
      " A \nB B\n A ";
  }
}
```

However, the current implementation of the Diamond's rendered method is still the following.


```java
public class Diamond {

  // ...

  public String rendered() {
    return letter == Letter.A ? "A" : " A \nB B\n A ";
  }
}
```

Before making any changes, let's run all the tests - Everything Green. Now let's write the first test for the Board class. 

```groovy
package com.michaelszymczak.diamond

import spock.lang.Specification

class BoardShould extends Specification {

  def "should print a symbol"() {
    expect: new Board([new PositionedLetter(Coordinates.ofYX(0,0),Letter.B)]).toString() == "B"
  }
}
```

Red, as classes do not exist yet. After creating the tiny types of PositionedLetter and Coordinates, and implementing the Board::toString method that ignores coordinates and simply returns given letter, we are Green again.
It's time for the refactoring step, i.e. making use of the Board class in the Diamond class.  

```java
public class Diamond {
  // ...
  public String rendered() {
    return letter == A ? 
      new Board(new PositionedLetter(ofYX(0,0), A)).toString() :
      " A \nB B\n A ";
  }
}
```

We are still Green, as Board correctly handles the case of one letter. It's time to finish what we started. The second case with letter B requires Board to be able to render more than one letter and respect the positions of them.
Same as previously, first we have to write a failing test for the Board class and make it quickly pass. After two cycles of Red-Green-Refactoring for the Board class, we have:

```groovy
package com.michaelszymczak.diamond

import spock.lang.Specification

class BoardShould extends Specification {

  def "should print a symbol"() {
    expect: new Board([new PositionedLetter(Coordinates.ofYX(0,0),Letter.B)]).toString() == "B"
  }

  def "should print symbols respecting their positions"() {
    expect:
    new Board([
            new PositionedLetter(Coordinates.ofYX(0,0),Letter.A),
            new PositionedLetter(Coordinates.ofYX(0,1),Letter.B),
            new PositionedLetter(Coordinates.ofYX(1,0),Letter.C),
            new PositionedLetter(Coordinates.ofYX(1,1),Letter.D),
    ]).toString() == shapeOf("""
AB
CD
""")
  }

  def "should fill gaps with defined symbol "() {
    expect:
    new Board("_", [
            new PositionedLetter(Coordinates.ofYX(0,2),Letter.A),
            new PositionedLetter(Coordinates.ofYX(1,1),Letter.B),
            new PositionedLetter(Coordinates.ofYX(1,3),Letter.B),
            new PositionedLetter(Coordinates.ofYX(2,0),Letter.C),
            new PositionedLetter(Coordinates.ofYX(2,4),Letter.C),
            new PositionedLetter(Coordinates.ofYX(3,1),Letter.B),
            new PositionedLetter(Coordinates.ofYX(3,3),Letter.B),
            new PositionedLetter(Coordinates.ofYX(4,2),Letter.A),
    ]).toString() == shapeOf("""
__A__
_B_B_
C___C
_B_B_
__A__
""")
  }
  private static String shapeOf(String shape) {
    shape.replaceAll("^\n", "").replaceAll("\n\$", "")
  }
}
```

```java
package com.michaelszymczak.diamond;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Board {

  private final PositionedLetter[][] board;
  private final String emptyCell;

  public Board(PositionedLetter... cells) {
    this(" ", asList(cells));
  }

  public Board(Collection<PositionedLetter> cells) {
    this(" ", cells);
  }

  public Board(String emptyCell, Collection<PositionedLetter> cells) {
    this.emptyCell = emptyCell;
    this.board = boardWith(cells);
  }


  private static PositionedLetter[][] boardWith(Collection<PositionedLetter> cells) {
    int maxCellPosition = cells.stream()
            .mapToInt(PositionedLetter::maxXorY)
            .max()
            .orElse(0);
    PositionedLetter[][] board = new PositionedLetter[maxCellPosition+1][maxCellPosition+1];
    cells.forEach(cell -> board[cell.getY()][cell.getX()] = cell);

    return board;
  }

  @Override
  public String toString() {
    return Arrays.stream(board)
            .map(this::rendered)
            .collect(Collectors.joining("\n"));
  }

  private String rendered(PositionedLetter[] row) {
    return Arrays.stream(row)
            .map(cell -> (cell != null) ? cell.letterAsString() : emptyCell)
            .collect(Collectors.joining());
  }

}
```

It's time for Diamond to delegate rendering for all the cases to the Board class.
 
```java
package com.michaelszymczak.diamond;

import static com.michaelszymczak.diamond.Coordinates.ofYX;
import static com.michaelszymczak.diamond.Letter.A;
import static com.michaelszymczak.diamond.Letter.B;

public class Diamond {

  private Letter letter;

  public static Diamond of(Letter letter) {
    return new Diamond(letter);
  }

  private Diamond(Letter letter) {
    this.letter = letter;
  }

  public String rendered() {
    return letter == A ? new Board(new PositionedLetter(ofYX(0,0), A)).toString()
            : new Board(
            new PositionedLetter(ofYX(0,1), A),
            new PositionedLetter(ofYX(1,0), B), new PositionedLetter(ofYX(1,2), B),
            new PositionedLetter(ofYX(2,1), A)
    ).toString();
  }
}
```

Now it's good time to [commit the changes](https://github.com/michaelszymczak/blog-support/commit/0906c04c30152a8a6a5b9e041e61b3644f84c509).

## Big and small cycles

All our tests are still Green, and we added a few more of them. There was no time when we turned Red for the reason other that adding new test.
We were also able to keep the pace and smoothly transition from Red to Green couple of times. The interesting observation was that in TDD there are big and small cycles.
Although we have done a couple of small cycles of Red Green Refactoring when we were implementing Board class (I showed you just the end result for brevity), it was part of a bigger cycle,
that I would classify as a pre-refactoring step for a Diamond class.


```
# My way of TDD, capital letters indicate big cycle, lowercase letter - the small one
RED -> GREEN -> (red->green->refactor) REFACTOR
```

In the light of big and small cycles, my approach differs from middle out/bottom up classic TDD in a sense that small Red-Green-Refactor cycles are part of a Refactor phase in a bigger cycle, whereas in middle out/bottom up
classical TDD small Red-Green-Refactor cycles happens before the Red phase of a bigger cycle, i.e. small building blocks are developer first, then higher-level functionality is specified (Red) and implemented
using the already built small components (Green).

```
# Middle out/bottom up classical TDD 
(red->green->refactor) RED -> GREEN -> REFACTOR
```

It also differs from the way how some developers do BDD, when the acceptance test is pending/failing/not implemented until the building blocks are ready. In this case Red phase of a bigger cycle happens first,
then there is a small Red-Green-Refactor cycle, that eventually moves big cycle into a Green phase.

```
# Some BDD practitioner
RED (red->green->refactor) -> GREEN -> REFACTOR
```

Mockist TDD practitioners do one cycle after another. Once the top-level class is finished and dependencies mocked, they go level down and repeat the process. It like searching the graph-type approach.
They can Mock dependencies immediately, or they can change their mind later and extract already existing logic into a separate class that they can replace with a Mock and use instead of the actual logic.
The tend to think about the design quite early and try to be right the first time. The have to, as refactoring with all those Mocks around is far less pleasant than in the state-based testing (classic TDD). 

```
# Mockist TDD practitioners 
RED -> (mock dependencies) GREEN -> RED -> GREEN -> REFACTOR (extract logic and mock dependencies)
```

Developer that know more than one flavor of TDD can obviously jump from one to another, case by case. The problem with jumping is that some of tests may still fail during the Refactoring step (those with Mocked dependencies)
and that we have to remember to create some additional end to end tests with real dependencies for the classes we developed with Mockist TDD approach. If you forget to create them, your system may not work at all
although all your tests are green. We definitely don't want to be there. 


## It's all about confidence

If you do Continuous Delivery, a successful build with all the tests Green switches the 'Deploy' button on. This button can be then pressed by someone in charge to deploy it to production.
If you do Continuous Deployment, there is no need for the 'Deploy' button, as it can be deployed automatically. If you don't have enough confidence to promise any of that, you are not there yet.
In our case, whenever all the tests are Green and we are happy with the scope delivered so far, we can deploy our Diamond app to production. At the moment, all we know is
that it works fine for letter A and B, but we know nothing about the rest of the alphabet. 

Testing can only prove the absence of some, but not all, bugs. In our case, testing only two cases is clearly not enough. So far, our tests are example-based tests, not property-based tests.
Those testing strategies are not mutually exclusive and one can support another.
When we do property-based testing, we need to verify sufficient number of properties, so that we know that for each input there will be a correct output produced.
When we test using examples, we need to be sure that there are enough examples that implicitly test all the properties we care about and that we can extrapolate assumptions based on limited examples to be confident
that our software will produce correct outputs regardless of the input. I would be much more confident if I proved that Diamond produces correct output for more than two cases.

I could test the case with the letter Z. It would give me a lot of confidence. I am planning to do it when the feature is ready, as a confirmation that the solution is generic enough
to produce correct result even for complex cases. However, as the feature is still under development, I will pick the cases that does not turn my refactoring efforts into nightmare - just enough
to give me confidence at this stage. I am not even sure if this is necessary - maybe letter A and B are enough to highlight all the underlying rules of the final solution. As I am not confident though,
I will do that, to be on a safe side. It takes only couple of seconds, so the confidence boost / test TCO ratio is quite high.

```groovy
class DiamondAcceptanceTest extends Specification {

  // ...

  def "creates diamond-like shape"() {
    expect:
    Diamond.of(Letter.C).rendered() == "" +
            "  A  " + "\n" +
            " B B " + "\n" +
            "C   C" + "\n" +
            " B B " + "\n" +
            "  A  "
  }
}
```

The just added test is Red, time to make them Green again.

```java
public class Diamond {

  private Letter letter;

  public static Diamond of(Letter letter) {
    return new Diamond(letter);
  }

  private Diamond(Letter letter) {
    this.letter = letter;
  }

  public String rendered() {
    if (letter == A)
    {
      return new Board(new PositionedLetter(ofYX(0,0), A)).toString();
    }
    if (letter == B)
    {
      return new Board(
              new PositionedLetter(ofYX(0,1), A),
              new PositionedLetter(ofYX(1,0), B), new PositionedLetter(ofYX(1,2), B),
              new PositionedLetter(ofYX(2,1), A)
      ).toString();
    }

    return new Board(
            new PositionedLetter(ofYX(0,2), A),
            new PositionedLetter(ofYX(1,1), B), new PositionedLetter(ofYX(1,3), B),
            new PositionedLetter(ofYX(2,0), C), new PositionedLetter(ofYX(2,4), C),
            new PositionedLetter(ofYX(3,1), B), new PositionedLetter(ofYX(3,3), B),
            new PositionedLetter(ofYX(4,2), A)
    ).toString();

  }
}
```

All of them are Green again. [It's time to commit](https://github.com/michaelszymczak/blog-support/commit/ab42e6db69bc18e95af132b7ccd495a335808149)

We just turned Red into Green, which means that we can do some Refactoring. The big cycle Refactoring step can be achieved by a couple of small cycles.
A few commits ago we discovered that the Diamond class had too many responsibilities. By extracting one of the responsibilities (formatting the output)
into a separate class, we made the next refactoring steps possible. The current refactoring is about delegating the calculation of the letter layout.

In order to maintain a steady pace, we will replace coordinates one by one. Let's introduce the new class Layout that calculates the coordinates of letters.

```groovy
package com.michaelszymczak.diamond

import spock.lang.Specification

class LayoutShould extends Specification {
  def "let the top letter to be in ordinal number distance from the top"() {
    given:
    def layout = new Layout()

    expect:
    layout.yOfTopLeft(Letter.A) == 0
    layout.yOfTopLeft(Letter.C) == 2
    layout.yOfTopRight(Letter.A) == 0
    layout.yOfTopRight(Letter.C) == 2
  }
}
```

I have some idea how to implement the class, but before that let me confirm that the order in which I specify letters can be useful.

```groovy
package com.michaelszymczak.diamond

import spock.lang.Specification

class LetterShould extends Specification {

  def "have position in the alphabet as its ordinal number"() {
    expect:
    Letter.A.ordinal() == 0
    Letter.B.ordinal() == 1
    Letter.C.ordinal() == 2
    Letter.D.ordinal() == 3
  }
}
```

Now I can implement the Layout, in the simplest way possible of course.

```java
package com.michaelszymczak.diamond;

public class Layout {


  public int yOfTopLeft(Letter letter) {
    return letter.ordinal();
  }

  public int yOfTopRight(Letter letter) {
    return letter.ordinal();
  }
}
```

Having implemented calculating top left and top right y coordinate of the letters, I can now delegate this responsibility to the newly implemented class.
 
```java
public class Diamond {
  // ...
  public String rendered() {
    final Layout layout = new Layout();
    if (letter == A)
    {
      return new Board(new PositionedLetter(ofYX(layout.yOfTopLeft(A),0), A)).toString();
    }
    if (letter == B)
    {
      return new Board(
              new PositionedLetter(ofYX(layout.yOfTopLeft(A),1), A),
              new PositionedLetter(ofYX(layout.yOfTopLeft(B),0), B), new PositionedLetter(ofYX(layout.yOfTopRight(B),2), B),
              new PositionedLetter(ofYX(2,1), A)
      ).toString();
    }

    return new Board(
            new PositionedLetter(ofYX(layout.yOfTopLeft(A),2), A),
            new PositionedLetter(ofYX(layout.yOfTopLeft(B),1), B), new PositionedLetter(ofYX(1,3), B),
            new PositionedLetter(ofYX(layout.yOfTopLeft(C),0), C), new PositionedLetter(ofYX(layout.yOfTopRight(C),4), C),
            new PositionedLetter(ofYX(3,1), B), new PositionedLetter(ofYX(3,3), B),
            new PositionedLetter(ofYX(4,2), A)
    ).toString();

  }
}
```

We have just finished the first small cycle of the bigger Refactoring step. [Let's commit it](https://github.com/michaelszymczak/blog-support/commit/43527243bb5fcc2767c660a5d3ec0a1d37af008d)

## Outside-in TDD with Refactoring made possible

I think this is the focal point of this blog and a stark example of the power of this flavor of TDD. We reached the point when we are able to extract the logic piece by piece,
maintaining the small Red Green Refactor steps. The refactoring is not only possible, but it is almost enforced as it is the only reasonable way to progress. On top of that,
we refactor the code that already works, so all our efforts won't be wasted. The refactoring step has been postponed just enough to make an informed decision on what kind of refactoring makes sense and supports the final solution.
If you compare it to the Mockists TDD refactoring, that is done way before we have a working solution, and with middle out/bottom up Classical TDD where we are not sure if all the classes we create will be useful at all,
you can clearly see the benefits. 

## Minimal Viable Product

Let's pretend that we run out of time and money (after 10 minutes, I know, tough market). Our Diamond startup's venture capital firm demands some results before the next round of funding. After [some research](https://en.wikipedia.org/wiki/Letter_frequency)
we think that already supported letters A, B and C account to roughly 10% of the use cases. Instead of waiting, let's produce some diamonds, earn some money and collect some feedback!
Business is fine with rejecting requests for diamonds with letters other than A,B or C until they are implemented. We are happy to deploy the existing version as MVP. 

>Meanwhile in the middle out/bottom up Classical TDD camp...
>
>-Dev: "We have some classes already implemented, but we are not ready yet to create a Diamond class"
>
>-Product Owner: "OK, what if we cut the scope and release only A and B diamonds, without C?"
>
>-Dev to other Dev: "He doesn't get it, does he?"
 

>Meanwhile in the Mockist TDD camp...
> 
>--- Dev: "We have all tests green and 100% test coverage"
>
>--- Product Owner: "Excellent, way ahead of the schedule, let's deploy it"
>
>--- Dev: "Actually, if we did it, it wouldn't work at all"
>
>--- Product Owner: "Can you next time do what you are paid for?"
>
> (Hint: if you are from the Mockist TDD camp, make sure you use [walking skeletons](http://wiki.c2.com/?WalkingSkeleton).)
 

They are of course just a humorous stories and reality is never as simple as that. However, it highlights very important quality of the approach we took, namely, that we always focus on
the end goal and try to achieve it as quickly as possible. We do it incrementally, so that the progress can be actually measured and if we we think we are 80% done, we probably are.
This is due not only to the fact that we implement working (as opposed to mocked) software outside-in, but also to the preparatory work (described in one of first chapters of this blog), that addressed the highest risks first.
If you read my other blog posts, e.g. about Specification by Example, you can notice the same pattern applied at various levels of abstraction. It allows you to measure the progress both on a macro- and a micro-scale.
 
## Keep calm and TDD

Next steps are quite obvious. We replace one by one each of the hardcoded coordinates, making the code more and more generic.

We noticed that there is only one y coordinate for each top letter, regardless of the leftness or rightness of them. We were Green, so we were able to refactor it.
We then followed with creating the next test demanding calculating y coordinates of bottom letters. We had to hold our horses though, having realised that the y coordinates of bottom letter depend not only
on the letter in question, but also on the letter used as an input. In other words, the stateless, immutable Layout became an immutable, but stateful one.

```
  A
 B B <- same y coordinate for both top letters
C   C
 B B <- same y coordinates for both bottom letters, that depend on the input (maximum) letter
  A
```

As an intermediate step, we can deprecate the old constructor, so that all the tests are still Green. The deprecated constructor will be removed after migrating all clients into the stateful version
of the Layout. I am also in favour of using [static factory methods if they help to understand the meaning](http://www.informit.com/articles/article.aspx?p=1216151).

```java
public class Layout {

  private final Letter lastLetter;

  public static Layout forLastLetterBeing(Letter lastLetter) {
    return new Layout(lastLetter);
  }
  
  @Deprecated
  public Layout() {
      this(Letter.A);
  }

  public Layout(Letter lastLetter) {
    this.lastLetter = lastLetter;
  }
}
```

The existing test has been refactored as follows.

```groovy
class LayoutShould extends Specification {
  def "let the top letter to be in ordinal number distance from the top"() {
    given:
    def layout = layout()

    expect:
    layout.yOfTop(Letter.A) == 0
    layout.yOfTop(Letter.C) == 2
  }

  private static Layout layout() {
    Layout.forLastLetterBeing(Letter.C)
  }
}
```

Although we now instantiate Layout passing the last letter in the diamond `Layout.forLastLetterBeing(Letter.C)`, this is irrelevant for the first test, therefore
we hide this detail behind the method `def layout = layout()`. When testing, we should try to hide all the implementation details irrelevant for the outcome of the test,
and make explicit all details that are relevant. This approach makes the test understandable, [keeping cause and effect clear](https://testing.googleblog.com/2017/01/testing-on-toilet-keep-cause-and-effect.html)
(funny enough, I later accidentally inlined it again, but luckily the test is small enough to pose no challenge in understanding it - this rule of the least knowledge is especially useful when we have more than one input that affects output).

We are ready to demand more from the Layout - to calculate y coordinates of the bottom letters. In this case the last letter is important, so we make this explicit.
 
```groovy
class LayoutShould extends Specification {
  // ...
  def "let the bottom letter to be in ordinal number distance from the bottom which is twice the ordinal number of the max letter"() {
    given:
    def layout = Layout.forLastLetterBeing(Letter.D)

    expect:
    layout.yOfBottom(Letter.D) == 3
    layout.yOfBottom(Letter.C) == 4
    layout.yOfBottom(Letter.B) == 5
    layout.yOfBottom(Letter.A) == 6
  }
}
```

Turning from Red into Green is as easy as realising that the diamond is almost twice as high as the ordinal number of the last letter (as each but the middle one appears twice) and the
calculated letter is as far from the bottom as its ordinal number.

```java
public class Layout {

  private final Letter lastLetter;

  public static Layout forLastLetterBeing(Letter lastLetter) {
    return new Layout(lastLetter);
  }

  private Layout(Letter lastLetter) {
    this.lastLetter = lastLetter;
  }

  public int yOfTop(Letter letter) {
    return letter.ordinal();
  }

  public int yOfBottom(Letter letter) {
    return lastLetter.ordinal() * 2 - letter.ordinal();
  }
}
```


As we are on Green again, let's finish with Refactoring, that closes the next small cycle of the bigger Refactoring cycle we are still in.
All y coordinates of each positioned letter are now calculated by the Layout.

```java
package com.michaelszymczak.diamond;

import static com.michaelszymczak.diamond.Coordinates.ofYX;
import static com.michaelszymczak.diamond.Letter.*;

public class Diamond {

  private Letter letter;

  public static Diamond of(Letter letter) {
    return new Diamond(letter);
  }

  private Diamond(Letter letter) {
    this.letter = letter;
  }

  public String rendered() {
    if (letter == A)
    {
      Layout layout = Layout.forLastLetterBeing(A);
      return new Board(new PositionedLetter(ofYX(layout.yOfTop(A),0), A)).toString();
    }
    if (letter == B)
    {
      Layout layout = Layout.forLastLetterBeing(B);
      return new Board(
              new PositionedLetter(ofYX(layout.yOfTop(A),1), A),
              new PositionedLetter(ofYX(layout.yOfTop(B),0), B), new PositionedLetter(ofYX(layout.yOfTop(B),2), B),
              new PositionedLetter(ofYX(layout.yOfBottom(A),1), A)
      ).toString();
    }

    Layout layout = Layout.forLastLetterBeing(C);
    return new Board(
            new PositionedLetter(ofYX(layout.yOfTop(A),2), A),
            new PositionedLetter(ofYX(layout.yOfTop(B),1), B), new PositionedLetter(ofYX(layout.yOfTop(B),3), B),
            new PositionedLetter(ofYX(layout.yOfTop(C),0), C), new PositionedLetter(ofYX(layout.yOfTop(C),4), C),
            new PositionedLetter(ofYX(layout.yOfBottom(B),1), B), new PositionedLetter(ofYX(layout.yOfBottom(B),3), B),
            new PositionedLetter(ofYX(layout.yOfBottom(A),2), A)
    ).toString();

  }
}
```

[It's enough to commit it](https://github.com/michaelszymczak/blog-support/commit/5bb73a9f6782f08e0082c512c3bb1355cae36f86).
 
In the [next commit](https://github.com/michaelszymczak/blog-support/commit/e65563a37f419facebebdbbbc30ff990e1a2b681) , the x coordinates
of the letters forming the left hand side of the diamond has been calculated as well. It happens to be simply a difference between the maximum and given letter's ordinal number.

If the x coordinate of the left letter was a difference, the x coordinate of the right letter is a sum of the maximum and given letter,
which was implemented in the [subsequent commit](https://github.com/michaelszymczak/blog-support/commit/fe3bb6271db0a50b98ba411571921e3614577dd2). 

At this stage, all of the coordinates are calculated automatically and the Diamond implementation looks as follows.

```java
package com.michaelszymczak.diamond;

import static com.michaelszymczak.diamond.Coordinates.ofYX;
import static com.michaelszymczak.diamond.Letter.*;

public class Diamond {

  private Letter letter;

  public static Diamond of(Letter letter) {
    return new Diamond(letter);
  }

  private Diamond(Letter letter) {
    this.letter = letter;
  }

  public String rendered() {
    if (letter == A)
    {
      Layout layout = Layout.forLastLetterBeing(A);
      return new Board(new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfLeft(A)), A)).toString();
    }
    if (letter == B)
    {
      Layout layout = Layout.forLastLetterBeing(B);
      return new Board(
              new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfLeft(A)), A),
              new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfLeft(B)), B), new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfRight(B)), B),
              new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfLeft(A)), A)
      ).toString();
    }

    Layout layout = Layout.forLastLetterBeing(C);
    return new Board(
            new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfLeft(A)), A),
            new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfLeft(B)), B), new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfRight(B)), B),
            new PositionedLetter(ofYX(layout.yOfTop(C),layout.xOfLeft(C)), C), new PositionedLetter(ofYX(layout.yOfTop(C),layout.xOfRight(C)), C),
            new PositionedLetter(ofYX(layout.yOfBottom(B),layout.xOfLeft(B)), B), new PositionedLetter(ofYX(layout.yOfBottom(B),layout.xOfRight(B)), B),
            new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfLeft(A)), A)
    ).toString();

  }
}
```


## You're not a beautiful and unique snowflake.

Hey, edge case, you are not special. You're not a beautiful and unique snowflake. Whenever I see an if, I see an opportunity
to discover something deeper about the problem I am solving. Ideally, the solution should be applicable
to all the cases, even the edge ones. Edge cases are not inherently special. They simply make great test fixtures.
Let's look at the diamond shape again.

```
   A
  B B
 C   C
D     D
 C   C
  B B
   A
```

The first and last letters (A and D respectively) seem to be special. They occur only twice, whereas all other letters occur exactly four times.
It has been expressed in our current Diamond implementation, when we have only one `PositionedLetter` in case A, 4 of them in the case B and 8 in case of C.
Similarly to the physicists searching for the [Theory of everything](https://en.wikipedia.org/wiki/Theory_of_everything), and trying to get rid of this
annoying 'if (large-scale and high-mass) then apply general relativity, if (small scale and low mass) then apply quantum field theory' if statement,
we will try to find the Theory of every letter for our Diamond. 

I was looking at the diamond shapes and at the if statements, when the 'aha' moment happened. What if the diamond shape is just a projection of letters onto the two-dimensional space,
and each letter, like in a kaleidoscope, occurs several times. Four times, to be precise, no ifs, no buts. The thing is that some of the letters are projected on top of each other, covering
the one being underneath. Even letter 'A' in A-diamond occurs four times. All of them in the same place, that is why we can see only one 'A'.

```
A <- 4 letters A with coordinates: (0,0), (0,0), (0,0), (0,0)
```

## TDD makes verifying your ideas easy 

Any serious theoretical physicist, after coming up with new hypothesis, rigorously uses mathematical apparatus to prove it.
Experimentalists, on the other hand, tend to conduct a series of experiments to discover new features, or to support or discard the existing hypothesis.
TDD practitioners are somewhat closer to the latter. Both of them have to be disciplined though, not to make any mistakes that can render their efforts useless.
TDD (done right) is a great and practical tool that makes the verification phase possible and reliable.
Scientists can only wish they had access to such controlled environment, when all experiments are repeatable. Appreciate and enjoy it.

Our most recent hypothesis is that each letter is projected in four places - sometimes different, sometimes overlapping. Let's verify it. 

```java
public class Diamond {
  // ...
  public String rendered() {
    if (letter == A)
    {
      Layout layout = Layout.forLastLetterBeing(A);
      return new Board(
              new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfLeft(A)), A),
              new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfRight(A)), A),
              new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfLeft(A)), A),
              new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfRight(A)), A)
      ).toString();
    }
    if (letter == B)
    {
      Layout layout = Layout.forLastLetterBeing(B);
      return new Board(
              new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfLeft(A)), A),
              new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfRight(A)), A),
              new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfLeft(A)), A),
              new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfRight(A)), A),
              
              new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfLeft(B)), B),
              new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfRight(B)), B),
              new PositionedLetter(ofYX(layout.yOfBottom(B),layout.xOfLeft(B)), B),
              new PositionedLetter(ofYX(layout.yOfBottom(B),layout.xOfRight(B)), B)
      ).toString();
    }

    Layout layout = Layout.forLastLetterBeing(C);
    return new Board(
            new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfLeft(A)), A),
            new PositionedLetter(ofYX(layout.yOfTop(A),layout.xOfRight(A)), A),
            new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfLeft(A)), A),
            new PositionedLetter(ofYX(layout.yOfBottom(A),layout.xOfRight(A)), A),
            
            new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfLeft(B)), B),
            new PositionedLetter(ofYX(layout.yOfTop(B),layout.xOfRight(B)), B),
            new PositionedLetter(ofYX(layout.yOfBottom(B),layout.xOfLeft(B)), B),
            new PositionedLetter(ofYX(layout.yOfBottom(B),layout.xOfRight(B)), B),

            new PositionedLetter(ofYX(layout.yOfTop(C),layout.xOfLeft(C)), C),
            new PositionedLetter(ofYX(layout.yOfTop(C),layout.xOfRight(C)), C),
            new PositionedLetter(ofYX(layout.yOfBottom(C),layout.xOfLeft(C)), C),
            new PositionedLetter(ofYX(layout.yOfBottom(C),layout.xOfRight(C)), C)
    ).toString();

  }
}
```

Don't worry, it's OK to introduce slightly more code if it makes the future refactoring easier and helps to spot some patterns.
As this was a Refactoring step, let's run all the tests to see if they are still Green. They are, which means that our hypothesis was
most probably right, so [a commit is in order](https://github.com/michaelszymczak/blog-support/commit/52368faedd3e9973c7de2da5f980386a080cc2e3).

## On man's Refactoring is another man's full TDD cycle
 
I think I repeat myself, but in the flavour of TDD that I practice, the magic happens at many levels at the same time. Bigger unit is Refactored (here - Diamond class), while
smaller units are part of the full TDD cycle with Red, Green Refactor.

The following ASCII-art presents my favourite way of doing TDD, which is an **outside-in, classic, stay-on-green, quick-transitions, just-enough-design, flavour of TDD**.
The vertical axis symbolizes time, passing from the top to the bottom.
The horizontal axis symbolizes the scale, where left hand side is specification/acceptance test facing.
The more to the right, the more fine grained the tests, and corresponding units of production code, are.
Starting from the top-left corner and following the arrows, you will eventually finish at DONE.
Each step down shouldn't take more than a minute. Whenever all the tests are green, you are allowed
(and often encouraged), to commit your work. You can also take a break or think about the next
steps for a while, doing just-enough, just-in-time design as you go.


```
 Time
  |
  |  |                         Outside <-> Inside                         |
  |  |                  Coarse grained <-> Fine Grained                   |
  |  +--------------------------------------------------------------------+------------
  |  |  Specification |  Big Unit  |  Smaller Units  | Even Smaller Units | Can commit?
  |  +--------------------------------------------------------------------+------------
  |  +                +            +                 +                    + 
  |  +    Red         +            +                 +                    + 
  |  +     '--------> + Naive impl +                 +                    + 
  |  +    Green <-----+-----'      +                 +                    +  Yes
  |  +      '---------+------------+--> Red          +                    + 
  |  +                +            +   Green         +                    +  Yes
  |  +                +            +     '-----------+--> Red             + 
  |  +                +            +                 +   Green            +  Yes
  |  +                +            +                 +  Refactor          +  Yes
  |  +                +            +                 +     |              + 
  |  +                +            +                 +     v              + 
  |  +                +            +                 +    Red             + 
  |  +                +            +                 +   Green            +  Yes
  |  +                +            +                 +  Refactor          +  Yes
  |  +                +            +  Refactor <-----+-----'              +  Yes
  |  +                +            +    Red          +                    + 
  |  +                +            +   Green         +                    +  Yes
  |  +                +            +  Refactor       +                    +  Yes
  |  +                + Refactor <-+-----'           +                    +  Yes
  |  +   Red   <------+----'       +                 +                    + 
  |  +    '---------->+Quick impl  +                 +                    +  Yes
  |  +  Green <-------+----'       +                 +                    +  Yes
  |  +     '----------+------------+> Red            +                    + 
  |  +                +            + Green           +                    +  Yes
  |  +                +            +Refactor         +                    +  Yes
  |  +                + Refactor <-+---'             +                    +  Yes
  |  +  Final test    +   '        +                 +                    + 
  |  +  as ultimate <-+---'        +                 +                    + 
  |  +  proof         +            +                 +                    + 
  |  +  Green?        +            +                 +                    +  
  |  +   |            +            +                 +                    + 
  |  +   v            +            +                 +                    + 
  |  +  DONE          +            +                 +                    +  Yes!
  |  +                +            +                 +                    +
  |  
  v   

```


## Finish it

The Diamond class has been expanded for a reason. We observed that each letter occurs four times,
but some of them overlap. Layout class has everything it needs to calculate position for all of them.

We are in the big cycle of Diamond's Refactoring, starting the new small TDD cycle for the Layout class.
 
```groovy
class LayoutShould extends Specification {
  // ...
  def "generate coordinates for each letter based on last letter"() {
    expect:
    forLastLetterBeing(A).positioned(A) == [new PositionedLetter(ofYX(0,0), A)] as Set
    forLastLetterBeing(B).positioned(A) == [
            new PositionedLetter(ofYX(0,1), A),
            new PositionedLetter(ofYX(2,1), A)
    ] as Set
    forLastLetterBeing(B).positioned(B) == [
            new PositionedLetter(ofYX(1,0), B),
            new PositionedLetter(ofYX(1,2), B)
    ] as Set
    forLastLetterBeing(C).positioned(B) == [
            new PositionedLetter(ofYX(1,1), B), new PositionedLetter(ofYX(1,3), B),
            new PositionedLetter(ofYX(3,1), B), new PositionedLetter(ofYX(3,3), B),
    ] as Set
  }
}
```

Green turned into Red, but within couple of seconds it becomes Green again when we copy
the logic from Diamond to the Layout class.

```java
package com.michaelszymczak.diamond;

import com.google.common.collect.ImmutableSet;

import java.util.Set;

import static com.michaelszymczak.diamond.Coordinates.ofYX;
import static com.michaelszymczak.diamond.Letter.A;

public class Layout {
  // ...
  public Set<PositionedLetter> positioned(Letter letter) {
    return ImmutableSet.of(
            new PositionedLetter(ofYX(yOfTop(letter),xOfLeft(letter)), letter),
            new PositionedLetter(ofYX(yOfTop(letter),xOfRight(letter)), letter),
            new PositionedLetter(ofYX(yOfBottom(letter),xOfLeft(letter)), letter),
            new PositionedLetter(ofYX(yOfBottom(letter),xOfRight(letter)), letter)
    );
  }
}
```

Last step of Diamond class refactoring ends this small TDD cycle.

```java
public class Diamond {
  // ...
  public String rendered() {
    if (letter == A)
    {
      return new Board(Layout.forLastLetterBeing(A).positioned(A)).toString();
    }
    if (letter == B)
    {
      Layout layout = Layout.forLastLetterBeing(B);
      return new Board(new ImmutableSet.Builder<PositionedLetter>()
              .addAll(layout.positioned(A))
              .addAll(layout.positioned(B))
              .build()
      ).toString();
    }

    Layout layout = Layout.forLastLetterBeing(C);
    return new Board(new ImmutableSet.Builder<PositionedLetter>()
            .addAll(layout.positioned(A))
            .addAll(layout.positioned(B))
            .addAll(layout.positioned(C))
            .build()
    ).toString();

  }
}
```

The full commit can be viewed [here](https://github.com/michaelszymczak/blog-support/commit/b66b302f6a5b19e96687a31515de3f7867518943)


The Diamond class has clearly one more responsibility that can be moved somewhere else. It decides which letters
should be rendered based on the input letter. We start the next small TDD cycle with the Letter.

```groovy
class LetterShould extends Specification {
  // ...
  def "return all letters up to the specified one"() {
    expect:
    A.inclusiveSequence() == [A]
    B.inclusiveSequence() == [A,B]
    D.inclusiveSequence() == [A,B,C,D]
  }
}
```

Red.

```java
public enum Letter {
  // ...
  public List<Letter> inclusiveSequence() {
    return Stream.of(values())
            .filter(l -> l.ordinal() <= ordinal())
            .sorted((l1, l2) -> Integer.compare(l1.ordinal(), l2.ordinal()))
            .collect(Collectors.toList());
  }
}
```

Green

```java
public class Diamond {
  // ...
  public String rendered() {
    return new Board(allPositionedLetters()).toString();
  }

  private List<PositionedLetter> allPositionedLetters() {
    final Layout layout = Layout.forLastLetterBeing(letter);

    return letter.inclusiveSequence().stream()
            .flatMap(l -> layout.positioned(l).stream())
            .collect(Collectors.toList());
  }
}
```

Refactor.

After adding all the supported characters to the Letter, it seems that the feature is ready, it's time to verify it.

```groovy
class DiamondAcceptanceTest extends Specification {
  // ...
  def "uses all letters if possible"() {
    expect:
    Diamond.of(Letter.Z).rendered() == "" +
            "                         A                         " + "\n" +
            "                        B B                        " + "\n" +
            "                       C   C                       " + "\n" +
            "                      D     D                      " + "\n" +
            "                     E       E                     " + "\n" +
            "                    F         F                    " + "\n" +
            "                   G           G                   " + "\n" +
            "                  H             H                  " + "\n" +
            "                 I               I                 " + "\n" +
            "                J                 J                " + "\n" +
            "               K                   K               " + "\n" +
            "              L                     L              " + "\n" +
            "             M                       M             " + "\n" +
            "            N                         N            " + "\n" +
            "           O                           O           " + "\n" +
            "          P                             P          " + "\n" +
            "         Q                               Q         " + "\n" +
            "        R                                 R        " + "\n" +
            "       S                                   S       " + "\n" +
            "      T                                     T      " + "\n" +
            "     U                                       U     " + "\n" +
            "    V                                         V    " + "\n" +
            "   W                                           W   " + "\n" +
            "  X                                             X  " + "\n" +
            " Y                                               Y " + "\n" +
            "Z                                                 Z" + "\n" +
            " Y                                               Y " + "\n" +
            "  X                                             X  " + "\n" +
            "   W                                           W   " + "\n" +
            "    V                                         V    " + "\n" +
            "     U                                       U     " + "\n" +
            "      T                                     T      " + "\n" +
            "       S                                   S       " + "\n" +
            "        R                                 R        " + "\n" +
            "         Q                               Q         " + "\n" +
            "          P                             P          " + "\n" +
            "           O                           O           " + "\n" +
            "            N                         N            " + "\n" +
            "             M                       M             " + "\n" +
            "              L                     L              " + "\n" +
            "               K                   K               " + "\n" +
            "                J                 J                " + "\n" +
            "                 I               I                 " + "\n" +
            "                  H             H                  " + "\n" +
            "                   G           G                   " + "\n" +
            "                    F         F                    " + "\n" +
            "                     E       E                     " + "\n" +
            "                      D     D                      " + "\n" +
            "                       C   C                       " + "\n" +
            "                        B B                        " + "\n" +
            "                         A                         "
  }
}
```

After [committing the changes](https://github.com/michaelszymczak/blog-support/commit/169792fb3a003f46f991007097d22b4aebf79e71) and
[doing some cleanup](https://github.com/michaelszymczak/blog-support/commit/8da449a27a1ae385f655a557e8e81f3a9ad3441f) we are done.


