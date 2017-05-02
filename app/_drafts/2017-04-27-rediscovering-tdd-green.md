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

Given a letter, print a diamond starting with ‘A’ with the supplied letter at the widest point.

    
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


## Diamond-kata w/ TDD

Given a letter, print a diamond starting with ‘A’ with the supplied letter at the widest point.

    
<pre>
#  Example: diamond 'A' prints
      A
      
#  Example: diamond 'C' prints
      A
     B B
    C   C
     B B
      A
</pre>


My main focus is to maintain a certain pace, moving from Red to Green to Refactor smoothly and without any risky leaps into unknown
that would question the correctness of the implementation. In other words, the correctness of the solution is achieved by starting with the correct solution
for the sub-problem and applying a series of trivial transformations (that you can easily explain and reason about) that end when the solution for the whole problem is found.

The first approach I tried was the Classicist TDD. When you see some examples of Classicist TDD, the smooth transitions are achieved by doing it bottom-up - small chunks are implemented first
and the final solution bottoms up. There is a catch though - I want the approach to be outside-in, so that we are sure that we solve the right problem. It means that we should start with the test that is as close to the original requirement as possible.
In that sense, it is closer to what some describe as BDD @link, but for me the **outside-in approach is the most cost-effective one, as existence of each piece of logic is justified by some requirement**.
If each piece of logic is justified by requirements, there is no un-justified code, that costs time and money, bringing no value. Compare it to the bottom-up approach, when we may end up creating
some unnecessary code that, in the best case, will be thrown away or, in the worst case, we will try to keep 'just for case' or use somewhere because we can't admit that we wasted time writing it in a first place.
 
Quick pace and outside-in approach are two benefits of the Mockist TDD @link . You may think that I should simply use this approach then. There is another catch though. I believe that mocks should be used
as a last resort and excessive use of mocks is an anti-pattern. By Mocks I mean ```Mockito.when(query.executeQuery()).thenReturn(rs);``` and alike.
If you are one of the Mockist TDD practitioners and wonder why sometimes when you refactor the production code many tests turn red, even though the solution
 is still correct; or worse even, when while refactoring you introduced a bug but all of them are still green - you know what I mean. It is so common, that you may even have convinced yourself that it is OK to
refactor on Red, and this gambling is a price that you are willing to pay for the sake of a better design. There is a reason why mock-based tests panic or stay calm when the shouldn't though - they are not real,
 they only pretend that they know what to do. **A mock is like an incompetent teacher that fails a student if their answer differs from the one found in the answer-key, despite of still being correct.**
As in school, your strategy may be to obey the teacher (tests) and not question any answer (interaction) or become an outlaw that ignores the teacher (tests) and refactors on Red.
 
To sum up, outside-in approach, swift transition from Red to Green to Refactor and Mocks as a last resort. Is it possible?

TODO: Discuss the 'incremental tests' approach 


```groovy
```

<p>
</p>
