---
layout: post
title:  "Testing Business Rules"
category: software
tags: ["behavior-driven-development", "specification-by-example"]
---

<p class="excerpt">
In the previous article I presented one group of the business-facing tests I often use, which is Automated test case / use case. These tests help people understand the flow, the process and the actor’s interaction with the System Under Test. However, if you want to build a non-trivial system and you aren’t the only stakeholder, this is not enough. In order to deeply understand and validate our assumptions we need more fine grained tests. Today I’ll show you how to create such tests and be almost sure that you and other stakeholders are on the same page.
</p>
<span class="readmore"/>

## The value of natural language

If you’ve read my previous posts on Specification by Example, you already know that testers or developers use semi-human language like Gherkin to communicate with subject matter experts and business in general. They use Gherkin, because the natural language is often too ambiguous to be used to validate the system behaviour. We can of course ignore this step and directly code the logic into the system using more traditional xUnit test frameworks. I’ve seen this approach applied many times, especially when the domain isn’t that complicated (or at least when we think so). However, if we do skip this step, some very important aspects (precise, quick and not expensive feedback loop) are lost. The accurate feedback from subject matter experts or business-facing people is available no sooner than after the completion of the coding phase (typically after a few days at best).

In the previous post we discussed the scenario that reads:

```gherkin
Scenario: A user cancels the deposit prematurely.
Given the time deposit summary screen with the open time deposit
When  as a deposit owner I confirm the time deposit cancellation
Then  the time deposit should be cancelled
And   the money from the cancelled time deposit should be transferred back to the current deposit
```

Although the scenario above describes the feature so that one can have a general understanding of the expected behaviour, it provides no information about underlying business rules. Now it’s good time to introduce the next kind of business-facing tests I use: Business rules tests using Specification by Example. 


## The Story of The Two Teams

*Warning: For the sake of brevity this example does not reflect the actual deposit rules, interest rates etc. Do not try to launch your own banking service after reading the article. ;)*

Imagine two teams – team A and team B. Developers and subject matter experts aren’t collocated and the stakeholders have some pre-allocated time once a week. Both teams use this time slot to run a sprint demo and then to plan the work for the next sprint. Team A uses automated use cases, but no business rules tests, let alone the Specification by Example. Team B uses automated use cases and also business rules tests with Specification by Example.

### Team A: no business rules tests, nor Specification by Example

After a conversation with the subject matter expert developers write down several rules:

- When the time deposit is cancelled during the first half of the deposit period the user loses their interest
- When the time deposit is cancelled during the second half of the deposit period the user loses 50% of their interest


Developers start coding. After the two week sprint they demo the finished feature. The stakeholders ask some questions, the developers present the behaviour, everything looks fine. Then one of the subject-matter experts asks what will happen if a client launches a year-long deposit and closes it after a year and seven months. Developers demonstrate this behaviour and the application says that the user receives 100% of their interest.


<p class="dialogue">
- No, it should be 150% . 100% of the interest from the first deposit and the 50% of the renewed one – the expert replies.<br/>
- Renewed? Never heard of such requirement. We closed the deposit after one year as it’s a year-long deposit.<br/>
- Well, the bottom line is that we can’t accept such behaviour as it’s clearly wrong and can mislead our customers. The deposit has to be renewed automatically. Can you fix it?<br/>
- Uhmm, maybe after the next sprint?
</p>

### Team B: uses business rules tests with Specification by Example

After a conversation with the subject matter expert developers write down several rules and discuss them further:

<p class="dialogue">
- As we understand, when the time deposit is cancelled during the first half of the deposit period the user loses their interest, but when the time deposit is cancelled during the second half of the deposit period the user loses only the 50% of their interest. I’d like to create some acceptance tests that validate our understanding if you don’t mind:
</p>

```gherkin
Scenario: A user cancels the deposit prematurely.
Given a year long time deposit
And the initial amount of 100 pounds
And the interest rate of 2% a year
When I cancel the deposit after <MONTHS> months and <DAYS> days
Then I should have <GBP> pounds transferred to my account
```

<p class="dialogue">
- I’ll give you some example &lt;MONTHS&gt;, &lt;DAYS&gt; values and ask you how many &lt;GBP&gt; should I expect in each case, OK? First, if I cancel the deposit after 0 MONTHS and 0 DAYS, I’ll have the very same amount, that is 100 pounds, am I right?<br/>
- Not quite. Indeed, we do not charge our clients for premature cancellation, but for some reasons, we need to freeze the money for at least a week.<br/>
- OK. But you can cancel it after the first week and you aren’t extra charged?<br/>
- That’s right.<br/>
- Excellent. Next, what if I close it after 6 MONTHS and 1 day?<br/>
- You’ll have 101 pounds, because your deposit has been canceled during the second half and 50% of 2% interest rate is 1%. 101% of 100 pounds is 101 pounds. I hope it’s clear.<br/>
- Yes, perfectly clear. Now, as you probably understand the process, I’d like you to fill the rest of the table:
</p>

The subject matter expert receives the following table:


<pre>
MONTHS | DAYS | GBP
0      | 6    | X
0      | 7    | 100
6      | 1    | 101
12     | 1    | ?       
18     | 1    | ?
24     | 1    | ?
</pre>

The table returned to the developer after being filled by the subject matter expert:

<pre>
MONTHS | DAYS | GBP
0      | 6    | X
0      | 7    | 100
6      | 1    | 101
12     | 1    | 102       
18     | 1    | 103
24     | 1    | 104
</pre>

<p class="dialogue">
- Thank you. I understand figures 100, 101 and 102, but what about 103 and 104? The deposit is closed after 12 months, isn’t it?<br/>
- No, the deposit is automatically renewed and the client has to cancel it manually.<br/>
- Really? It means that we don’t have to develop an additional feature that queries all the deposits and closes ones that need to be closed. That’s great news, it seems that we still have plenty of time in the upcoming sprint. We can even take the next item from the backlog if you want.
</p>

##  Use examples whenever you can

Thanks to the quick feedback loop two bugs (immediate cancellation and not renewed deposit) have been detected even before coding phase. In addition, an unnecessary feature (a deposit auto-closing mechanism) has been avoided, which in turn enabled the team to develop more features from the backlog.

The story above can be derided as being too naive. Indeed, real cases are usually much more complex. However, the technique described above (specifying business rules using Specification by Example) thrives in more complex domains and when one misunderstanding can cost you a fortune. In the next post I’ll describe how to automate the scenario and make its execution very fast by avoiding bottlenecks such as testing the user interface.
