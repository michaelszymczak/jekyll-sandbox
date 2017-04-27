---
layout: post
title:  "Rediscovering TDD - Stay on green"
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
For example: print-diamond ‘C’ prints
      A
     B B
    C   C
     B B
      A

</pre>


```groovy
```

<p>
</p>
