---
layout: post
title:  "Automated test cases"
category: software
tags: ["behavior-driven-development", "testing"]
---

<p class="excerpt">
There are many types of tests. We need more than one type, because we focus on different characteristics of the system.
The human readable tests (e.g. created using Gherkin language) are not an exception to the rule.  
</p>

In the [previous article]({% post_url 2014-02-04-make-it-readable %}) I presented the test scenario that was far from ideal – mainly because we were focused on too many aspects at once. My intention was to refine that scenario in the upcomming articles by splitting it into many scenarios of different types in order to make it easy to comprehend, maintain and run. In this article I’m going to describe one of the types I use – an automated test case.
<span class="readmore"/>

## Describing requirements

There are also many ways of describing requirements. One of the most popular one is a use case, where one focuses on the actor’s interactions with the the system when he or she (or it in case of an external system) performs some defined tasks. I’ve observed, that non tech-savvy people, which constitute a big part of IT stakeholders, are particularly good at describing the desired flow (happy path) of the functionality, but tricky edge cases or failure paths are not really their cup of tea – this is our (QA, testers, devs) job to find and discuss such problems as soon as possible. Nevertheless, they are able to tell us their story and we are able to, after some refinement, write it down and express in the form of a use case. An example use case can read as follows:

<pre>
<strong>Use case: Cancel a time deposit</strong>

1. The system presents the currently active time deposit with an informative summary
   (end date, amount of money, interest rate etc.)
2. The user clicks ‘cancel this deposit’.
3. The system asks for confirmation.
4. The user confirms.
    4.a.1 The uses decides not to cancel the time deposit
    4.a.2 The system does not transfer anything and 
         the money is still on the active time deposit.
    4.a.3 END
5. The time deposit is canceled and the money from the time deposit
   is transferred to the current deposit.
6. END
</pre>


## Automating requirements verification

The question I ask myself during requirements gathering phase is ‘How can I automate this?’. In the case above, I’d probably write something similar to:

~~~gherkin
Scenario: A user cancels the deposit prematurely.

Given the time deposit summary screen with the open time deposit
When  as a deposit owner I confirm the time deposit cancellation
Then  the time deposit should be canceled
And   the money from the canceled time deposit should be transferred back to the current deposit
~~~

I use Cucumber (or an analogous tool, such as Behat), create fixtures and prepare the context in the *Given* step,
then I use a crawler to perform actions in *When* and verifies results in *Then*. 

## The test characteristic

#### The test scenario above is:

- **Concise, with one path only (happy path in this case).** End to end tests like this are much slower than isolated unit tests, so I avoid unnecessary steps and don’t verify every aspect of the functionality here.
- **Every information that is not necessary to understand the process is hidden.** We don’t want to clutter the scenario with unimportant information. Keep focused and don’t confuse readers.
- **Doesn’t contain UI/technical jargon whatsoever.** These tests are meant to be a communication channel between IT and business worlds, so we must be sure that the business understands them as well as we do.

#### The goals of these tests are:

- **Specification** – to be sure that we and the stakeholders are on the same page.
- **Regression** – to be confident that the system works and a user can use it.

#### However:

- **Not all details has been expressed**, there are still some edge cases and some not-so-obvious business rules that will be discovered after a further discussion. When the time deposit is cancelled during the first half of the deposit period the user loses their interest, did you know that? -> we need the Specification by Example
- **We don’t know if the system is user friendly.** Maybe the user can’t find the cancel button and we don’t know about it -> we need UX audit, focus groups, real users’ feedback, analytics etc.
- **We don’t know if the system breaks in the future**, we haven’t test every code branch and every state of the system -> we need Unit Tests, Integration Tests, Load Tests, QA, testers etc.

## Summary

In this article I wanted to convince you that most of the time we need more than one type of tests. If we try to achieve all goals using only one type of tests we probably fail and the tests will be unmaintainable. I also presented one of the types of tests I use to communicate with non tech-savvy people. In the next article I’ll present other type of tests that focuses on business rules and are faster by an order of magnitude than these presented today.
