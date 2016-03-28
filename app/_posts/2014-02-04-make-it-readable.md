---
layout: post
title:  "Make it readable"
category: software
tags: ["behavior-driven-development", "specification-by-example"]
---

Imagine that your department is building a web application for some banking institution and the purpose of this app is to help the bank customers understand better what happens with their money when they perform operations like opening a deposit account, making a consolidation loan etc.

In order to share common understanding of what exactly should be built, you decided to use Specification by Example. You’ve heard that one of the ways of doing this is to create scenarios describing required features. You’ve also heard that these scenarios should be runnable like any normal test and should automatically verify if the app behaves as expected. After several discussions with stakeholders, many of which were subject-matter experts, you prepared wireframes that should help you communicate with them better. Most of the people are visualizers after all and these wireframes help everyone express their vision and decide what they really want. You used these wireframes during the requirements gathering session.
<span class="readmore"/>

## What you see is what you get

One of the requirements identified during this session was a user story that reads:

{% highlight gherkin %}
Feature: Premature cancellation simulation
    As a bank customer facing unpredictable future
    I want to know what happens with my money in case of a time deposit premature cancellation    
    So that I can decide if I am ready to open such deposit.
{% endhighlight %}


You discussed user journeys and one of the dialogues went like this:

<div class="dialogue">
<strong>Bob (Subject-matter expert)</strong>: Hmm, I imagine that a user with a time deposit clicks ‘Cancel this deposit’ button.<br/>
<strong>You</strong>: That’s it, no confirmation at all?<br/>
<strong>Bob</strong>: No no, of course some confirmation is necessary, the user clicks it and then some big visible warning should appear, maybe orange, to draw her attention you know. So she clicks this confirmation button and that’s it. Do you need any additional information about that process?<br/>
<strong>You</strong>: Yes, for example what happens with her money?<br/>
<strong>Bob</strong>: Money, of course! It should be simple, not to scary the customer that her money is gone. The user’s current deposit area, the green one, should be updated immediately, so that she knows that her money is there, back on her current account. But she loses the interest, don’t forget about it!<br/>
<strong>You</strong>: Yes, I remember that. But this current deposit area wasn't blue last time we talked about it?<br/>
<strong>Bob</strong>: Yes, blue, the green one is the notification area, sorry.<br/>
<strong>You</strong>: No problem, I got it. Anything else?<br/>
<strong>Bob</strong>: Hmm, nothing comes to my mind now. It looks good.<br/>
<strong>You</strong>: Great, let me summarise it. A user clicks ‘Cancel this deposit’ button. System displays a big orange warning and asks for the confirmation, the user confirms, the money is transferred back to the user’s current account without any interest and she can see that the time deposit is cancelled and all the money is in this blue, current account area.<br/>
<strong>Bob</strong>: That’s right.<br/>
<strong>You</strong>: Ok, thanks, I’ll send this requirement to our developers.<br/>
<strong>Bob</strong>: Great.

</div>


Now, read the requirements gathering session carefully one more time and think about the test scenario you would build for this requirement. If you’ve never written any scenario, here you have an explanation what the Specification by Example is (TODO: link).

Came up with something? Below you can see what would possibly be created if we passed this discussed previously requirements to a developer, inexperienced in terms of Specification by Example, telling him that he should automate it using Gherking language and leaving him alone with this task:

{% highlight gherkin %}
Feature: Deposit cancellation
Given "#main div.currentaccount.blue div.balance" containing "<strong>0</strong> $"
  And "#main div.timedeposit div.balance" contains "<strong>100</strong> $"
  And "#main div.timedeposit div.interestrate" contains "<strong>2</strong> $"
When  the user clicks "#main div.timedeposit button.cancel"
  And wait 1 second
  And clicks "div.confirmation.orange span.confirm"
  And wait 1 second
Then  "#main div.currentaccount.blue div.balance" contains "<strong>100</strong> $"
  And "#main div.timedeposit div.balance" is not visible
{% endhighlight %}

I’ve seen this kind of scenarios many times. However, I’ll try to explain why this is not appropriate scenario for Specification by Example.

1. Specification should be equally readable for tech- and business-savvy people. In fact, this is the main reason why we make this effort and describe the scenario in a natural language like english. If the specification is difficult to read for business people, they won’t participate. Taking #main or div.balance for example &#8211; one can guess, that this is about balance of the current account. The chosen format (#main, &lt;strong&gt; etc.) is the easiest from the developer’s point of view because it follows closely HTML structure of the page that displays these information. It’s also absolutely confusing from the non-geek point of view.

2. There was no discussion after handing the requirements over to the developer so he had to guess many things. He decided for example, that the colour of the current account area is so important (it was mentioned in the requirements, wasn’t it) that deserves including in the test scenario (line one: div.currentaccount.blue)

3. In requirements, there is no information about example figures whatsoever. The developer had to come up with some that fits. However, he didn’t include after what time the deposit was canceled and Bob didn’t spot this, because he doesn’t understand the scenario in the first place.

## Ignore implementation details

There are many more pitfalls, but today I wanted to focus on the most important, which is the lack of readability.
Let's try to improve it a bit and make it more readable for non tech-savvy people:

{% highlight gherkin %}
Feature: Deposit cancellation
Given current account blue area containing $0
  And time deposit balance area containing $100
  And time deposit interest rate area containing 2%
When  the user clicks cancel button within the time deposit area
  And wait 1 second
  And clicks big orange confirm button
  And wait 1 second
Then  current account blue area should contain $100
  And time deposit balance area is not visible
{% endhighlight %}

Now it looks better. We simply got rid of technical jargon. That is a step in the right direction. Is the story more readable now? Yes. Is it good enough to communicate with stakeholders and keep the cost of maintenance low? Definitely not. Why? You’ve probably spotted a few low hanging fruits there. I’ll show in the next few posts how we can incrementally improve this scenario, focusing on different aspects of a good specification.

Thanks for reading.
