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
to be useful when presenting TDD. The solution I came up with is probably not the best one (define 'best'), but it is not about the solution (the problem has been
 already solved many times. In this case, the journey is more important than the destination.
 
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

As simple and as difficult as that. However, there are many habits that make or break this approach.
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
that you will have to maintain later. Each line of code costs time and money and makes future you poorer and more busy.


```groovy
```

<p>
</p>
