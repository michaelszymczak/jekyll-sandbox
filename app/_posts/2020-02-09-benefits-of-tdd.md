---
layout: post
title:  "Benefits of TDD "
category: software
tags: ["software-craftsmanship", "tdd", "test-driven-development"]
---

<p class="excerpt">
  Sometimes I hear people saying that we should focus on writing software instead of blindly following the rules such as the ones prescribed by TDD (Test Driven Development). I agree. In real case scenarios, we should almost never <em>blindly</em> follow any rules. What we can do, instead, is to understand why the rules exist and if the reason behind them makes sense in the current context, consider applying them. As TDD is a set of rules that shape the software development process, it must somehow affect some qualities of the process and the software that is built. 
  The benefits must outweigh the cost (a cost of the initial investment - learning the new skill, an ongoing opportunity cost - when only certain paths are allowed etc.).
  There is also the whole set of problems when TDD offers you little help. For instance, it can help you with clarifying and implementing requirements but what if you have vague or no understanding of the problem you want to solve? 
  When we understand the costs and benefits, we can make informed decisions if the trade-off works in our favor, or if we should change the process, or maybe this time take a shortcut.
  It is understanding of the rules' rationale that distinguishes a fanatic from a pragmatic person.  
  Following the rules when they make sense is a characteristic of a pragmatism (think of rules of the road and traffic lights). 
  Consciously not following the same rules, or changing the rules that are applicable, in special circumstances (ambulance driver when using blue lights) is another characteristic of a pragmatism.
  However, not understanding and thus dismissing the rules, or following the rules without understanding why, have more to do with 
  an ignorance and can be equally dangerous. Let’s leave ignorance behind us and dive into why-s then.

</p>
<span class="readmore"/>

<h2>Product characteristics</h2>

<p>
  I’ve identified certain benefits of using TDD. I call them benefits as they are aligned with the qualities I care about. If you read the following bullet points and decide that none of them are desirable within your context, this article won’t help you. If, on the other hand, you decide that some of them are useful, I will later link them to the steps in the TDD process to equip you with more why-s.
</p>

<p>
  Some of the things you may care about.
</p>  



  - [1] It is important that your employees understand a requirement before they implement something. You pay them for solving a problem, not for lines of code.

  - [2] When a certain problem has been solved in the past, you prefer your employees to know it and try to use the existing solution. You prefer to spend time and money only if it’s not sufficient.

  - [3] If the circumstances change, you want to be in a position to modify some of the existing solutions to be better aligned with the new circumstances. You want to be able to do it without unknowingly breaking other parts.

  - [4] You want to understand, or to be able to get answers for questions such as: “why we reject so many inquiries”, “why we discount the price at this time”, “if we decided to speed up the onboarding process and skip this step, what would we miss”.

  - [5] You prefer to avoid situations such as “we noticed that we started rejecting 50% more customers, but we are not sure how it worked in the past. There may have been some undefined smart ways of doing it more efficiently and we may have changed it unknowingly. Unfortunately we can’t revert to the version from 2 months ago as there may be other parts of the system that depend on the new version of this component. We can’t revert the whole system, because the data that the system operates on requires the new version. We are sorry”



  The points mentioned above have certainly been important to me throughout my entire career. When I learned that there was a lightweight process with all of them being a simple consequence of following this process, I decided I must give it a go.
  Later in this post I will refer to the bullet points [1] to [5] to show how the process and the characteristics are related.


<h2>They call it Test Driven Development</h2>


  Test Driven Development (TDD) cycle, as defined by Kent Beck, goes as follows.

  - Step 1. Write a test. Invent the interface you wish you had. Define all necessary inputs and expected outputs. See that the unit under test fails (produces incorrect answer, does not compile etc.) This is called a red phase.

  - Step 2. Make it run, make the unit under test produce the correct answer. Aim for the most obvious/fastest solution. If the ‘ideal’ solution can be implemented in seconds, do it, otherwise pick one that is faster to implement. This is called a green phase.

  - Step 3. If the solution is not ‘ideal’, improve it, but make sure all your tests pass, or, in other words, that you are only a few seconds away since the tests passed. This is called a refactor phase.
  
  
  You can also look at it from a slightly different perspective by following 3 rules of TDD defined by Robert C. Martin.

  - Rule 1. You are not allowed to write any production code unless it is to make a failing unit test pass.

  - Rule 2. You are not allowed to write any more of a unit test than is sufficient to fail; and compilation failures are failures.

  - Rule 3. You are not allowed to write any more production code than is sufficient to pass the one failing unit test.



The first difference when embarking on TDD is the order in which we write code. During a usual non-TDD flow, we write production code, execute it and then we see how it works. During TDD flow, we write what we call a test first. The latter is actually aligned with how we usually approach software development - we start with defining a problem, then we try to come up with some requirements for the solution that we hope can address the problem. The real difference is that before we implement a solution, we try to express the requirements in one of the most unambiguous ways we know - the code. 
This approach supports [1].

<h3>RGB</h3>

I will now show one cycle of the TDD, split into three distinct phases, called Red , Green, Refactor. The colors
are correlated with the color of feedback received when running the tests (Red - a test fails, Green - a test passes, Refactor - not really a color, but the goal of making it 'right', making it better).
What's important is the goal of each phase.

- During the first phase, you aim for Red (failing test).
- During the next phase, you aim for Green (passing test).
- During the last phase, you aim for Better (eg. better design, but no new features).

RGB in short ;)

<h3>Step 1 - aiming for Red</h3>

Step 1 is ‘write a test’. We place our focus on a requirement, not a solution. As Rule 2 says that it must be a _failing_ test, we know that we verify something (and not simply write some test afterwards that gives us false confidence that it was verified when it passes). The test must first fail before we implement a solution. Sometimes the test doesn’t fail. The reason may be that we forgot to assert/verify that a solution to be written does what is required to do. It means that the test is indifferent to the tested behaviour of a solution - it’s not fit for purpose and should be fixed. Another reason may be that a solution already exists and the application behaviour does not need to be changed. It sometimes can be a surprise that the already implemented logic is generic enough to cover more cases that we initially thought. It means that we avoid unknowingly adding to/changing the code that already works, resulting in less code and less noise. [2]

As we trust our tests more (we know they verify something and that they cover all known requirements), we are free to change production code. We can delete some code and run the tests. We can keep modifying code and as long as the tests pass we can be confident that the production code still does what’s required. [3]

Rule 2 also states that it is “not allowed to write any more of a unit test than is sufficient to fail”. If we wrote more test(s) that are sufficient to fail, we would have to write more production code to make it pass. It would be harder to see if all the production code was necessary to make it pass, or we could do without some. It would be harder to correlate what system does with what it is required to do. [4] This would it turn introduce some undefined behaviour, not verified by any test and thus not explicitly required. Why this is dangerous is explained in the next section. Writing more test code that is required to fail also increases a risk of forgetting to assert/check everything we want to check.

```
(starts writing tests)
Test something (A)
Test something else (actually forgot to write assertion) (B)
Test yet another thing (C)
(runs the tests, it fails - red)
(starts implementing features)
Implement something (A)
Implement something (or not) (B)
Implement yet another thing (C)
(runs the tests, it passes - green)
(refactor etc…)
```

Result:

Features (A) and (C) are tested. Feature (B) is either not tested, or not implemented at all, but nothing informed us about this fact.

When we apply Rule 2, the workflow is the following

```
(starts writing tests)
Test something (A)
(runs the tests, it fails - red)
(starts implementing features)
Implement something (A)
(runs the tests, it passes - green)
(refactor etc…)
(starts writing tests)
Test something else (actually forgot to write assertion) (B)
(runs the tests, it does not fail, the mistake in the test has been detected)
```

<h3>Step 2 - aiming for Green</h3>

Step 2 is ‘make the test pass’. Rule 1 insists that it should not be more that is necessary to make it pass. Stopping writing production code the moment the test passes may not feel natural at times, but it is done for a reason. By not writing more production code that is not yet covered by tests, we do not risk undetected regression. Regression happens when we accidentally change/remove behaviour of our system so that it no longer satisfies all the requirements. If we wrote more production code that is tested, there would be nothing but pure luck and manual testing that could help us discover it. It is usually better to know that something is yet to be implemented than to think that was already implemented but for some reason it is no longer and nobody noticed. It is usually much better to know that something is yet to be implemented than not understanding what the system does and fear that any change can break existing functionality that we depend on and there is no way of knowing if it happened. [5] Following rule 1 prevents this.

<h3>Step 3 - aiming for Better</h3>

Step 3 says that “if the solution is not ‘ideal’, improve it, but make sure all your tests pass”. The difference between Step 2 is that although we change the code, the goal is not to introduce new behaviour (that is not allowed by Rule 1 and 3), but reshape existing code and keep existing behaviour. Step 1 validates Step 2 and 3 by transitioning to fail (red) phase at expected moment and Step 2 validates Step 1 transitioning to pass (green) phase at the right time. Step 3 does not validate anything. It is important as it encourages us to keep the code maintainable and thus speeds up the development, but it is also important as during Step 3 - refactor phase - developers are free to do whatever is necessary to meet some other constraints. Step 3 is perfect for internal and external cross-functional requirements. One of the common external cross-functional requirements is performance and one of the common internal requirements is so called internal quality leading to better understanding of the codebase, increased speed and lower cost of delivery.

<h3>Alternatives</h3>

I’ve started with mentioning a few characteristics of the product I care about. Then I showed how TDD naturally leads to a product that features all the above mentioned characteristics. If you don’t care about any of them, it’s possible that TDD may be a waste of time for you. Even if you do, there are other ways of achieving the same results without using TDD. You can, for instance, be really careful and hope for the best. You can avoid making mistakes and try to deliver great products. It’s simply a matter of what we believe is possible when we take into consideration our abilities and risk appetite. If you ask me though, I know that I make mistakes, in fact I believe that all people make mistakes and I think that hope is not a strategy. I don’t take my chances.



