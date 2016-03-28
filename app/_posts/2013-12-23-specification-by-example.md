---
layout: post
title:  "Specification by Example"
category: software
tags: ["behavior-driven-development", "specification-by-example"]
---

<p class="excerpt">
A few weeks ago I attended the local JUG meeting, topic of which was Specification by Example and Living Documentation. When the speaker asked if anyone had used this technique in their work, three people (from round about 30 attending) raised their hand. “10%, could be worse” he surely thought. The point was, we were all from the same company and we’d been using it because I convinced developers and management of my company to employ it. So, excluding us, no one had used it before. Why is that? People don’t need it, don’t get it or maybe haven’t heard of it?
</p>
<span class="readmore"/>


## Definition

I know that Specification by Example has many benefits and it’s worth considering when you want to create better products 
and share common understanding of the product with your client, because we have been using it for almost a year and we feel the difference. 
But maybe you haven’t even heard of it.
According to the Wikipedia, [Specification by Example](https://en.wikipedia.org/wiki/Specification_by_example) (SBE) is a 
"collaborative approach to defining requirements and business-oriented functional tests for software products based on capturing 
and illustrating requirements using realistic examples instead of abstract statements. It is applied in the context of agile 
software development methods, in particular behavior-driven development. This approach is particularly successful for managing requirements 
and functional tests on large-scale projects of significant domain and organisational complexity." 



##  Examples

If you want to try SBE, or you are simply serious about delivering right software that matters
I strongly encourage you to read the [book about Specification by Example](http://www.amazon.com/gp/product/1617290084).
Here I’ll try to describe SBE technique using some…that’s right, examples. 
This technique doesn’t impose any particular format of the specification, but the popular combination is SBE with BDD and Gherkin language:

~~~gherkin
Scenario: A user can deposit their money in the system.
Given Tom with 100$ in the system
When he deposits 50$ on his personal system account
Then he should have 150$ in the system
~~~

Another example:

~~~gherkin
Scenario: A user CANNOT buy Bitcoins on credit.
Given Lisa with 1000$ and 0 Bitcoins in the system
And exchange rate 600$ for one Bitcoin
When she tries to buy 2 Bitcoins
Then the system should inform the user about insufficient funds
And she should have 1000$ in the system
And she should have 0 Bitcoins in the system
~~~

Quite simple, isn’t it? Some people familiar with BDD may wonder if there is any difference between a simple Gherkin scenario and the one presented above. Personally, I see SBE scenarios as a BDD with some additional rules (providing we use BDD to express SBE). One obvious rule is that we should use examples to describe the feature. Here, we use the user’s balance to present how exactly the system should behave. Second rule is that we should have only one When clause in the scenario to avoid writing test scripts instead of a specification.


## Automation

One of the great benefits of SBE is possibility of running the specification. Scenarios can be automated and check the system behaviour after every change (it works really well when you combine it with Continuous Integration). When you can run the specification against the real system, you can be (almost) sure that:

- the specification doesn't lie;
- the system behaves as expected;
- the specification is always up to date.

None of them are true if your documentation is separated from the code.

##  Tools

As I’ve mentioned before, the Gherking language is only one of the ways the SBE can be presented. If you chose this approach,
you can use [Cucumber](http://cukes.info/) to automate the specification written in Gherkin.
If you prefer tabular data, try [FitNesse](http://fitnesse.org/).
If you want to create really readable documentation, you should definitely see [Concordion](http://concordion.org/).


## Summary

Specification by Example is a great tool that can significantly help you deliver great software and keep your clients happy. I think that SBE should be more popular among developers and business people. The idea is really simple, but to master this technique you need to write many examples and participate in many specification workshops. I think I’m still a beginner and I feel that I can do this much better, even though I currently develop a few products using this technique and have already written, or helped to write (both tech- and business-savvy people) hundreds of scenarios. In the next posts I’ll try to describe some common pitfalls and present good practices that help you avoid mistakes I’ve made and observed in the teams I coach.
