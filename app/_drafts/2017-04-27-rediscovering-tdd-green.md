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

## TDD - challenges

Now, when I (hopefully) convinced you that keeping the pace has its benefits, let's see what can stand in the way.

The first that comes to my mind is that we have no idea what to build. In this case, refine the requirement using techniques such as Specification By Example @llink. Draw pictures, talk with stakeholders,
do whatever it takes to get it. The technique I use most often is paraphrasing and writing some examples. It immediately highlights discrepancies between my understanding and actual requirements.

You may also not be clear if you are able to build it - the right library may not exists, vendor's API is poorly documented etc. In this case, you should probably start with spike, hacking your way as you go.
You will test this solution picking the cheapest and quickest way, which is ofter manual testing or some ad-hoc created test with questionable design. Once you found the solution, ~~you deploy it and go home~~,
you establish boundaries allowing you to quickly create an end to end test automatically verifying that the solution still works.

After you know what to build and that you can build it using the tools of your choice, the next unknown is the design. The outcome of the previous (optional) step may be one of the following.

 - Case A - I didn't even bother to do a spike as I am already confident that technology is not a problem.
 - Case B - There were one or two places that I wasn't confident about, but I narrowed them down and the rest is not a problem, technology-wise. The good example is that there was some undocumented part of API that
  required you to pass a custom header in order to get the response. Had you known that, you wouldn't have bothered doing a spike.
 - Case C - The whole solution is still a mystery for you. You are afraid to change anything as it may turn a working solution into useless one.


```groovy
```

<p>
</p>
