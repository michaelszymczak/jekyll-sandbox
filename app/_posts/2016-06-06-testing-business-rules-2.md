---
layout: post
title:  "Testing Business Rules - part 2"
category: software
tags: ["concordion", "behavior-driven-development", "specification-by-example"]
---

<p class="excerpt">
Every time some part of the world that we care about (i.e. the domain around which we build the system) becomes well-defined, it is not a bad idea to write our findings down. By creating a clear rules describing business, our small world has been divided into two areas: one that complies with the newly discovered business rule and one that violates it. In order to make sure the cut is precise and the system evolves without breaking that constrains, you need a tool: a business rule test. This article shows how to run business tests efficiently.
</p>
<span class="readmore"/>

## Write or ask for specification

Write the specification based on your understanding or take the specification from the business and refine it. Either way, discuss it with business. Keep in mind that this kind of specification describes business rules; not a user interface, not an unnecessary technical terminology.

 Imagine that you have received the following scenario:

~~~gherkin
Scenario: A user cancels the deposit.
Given a year long time deposit
And the initial amount of 100 pounds
And the interest rate of 2% a year
When I cancel the deposit after <MONTHS> months and <DAYS> days
Then I should have <GBP> pounds transferred to my account
~~~

<pre>
MONTHS | DAYS | GBP
0      | 6    | X
0      | 7    | 100
6      | 0    | 100
6      | 1    | 101
12     | 1    | 102
18     | 1    | 103
24     | 1    | 104
</pre>

## Identify business rules

We can use that step by step description and use it as our test, but we should always try to find underlying rules. There are two ways we can learn how that rules look like: we can be told what the rule is (easy), or we can be given a list of requirements and infer the underlying rules from them.

The rules behind the figures above becomes pretty clear after looking at them for a few seconds. For a one year long deposit:

- One receives the same amount as deposited if the deposit cancelled within first 6 months
- One receives the deposited amount plus half of the interest if the deposit cancelled after 6 months and within the first year
- The deposit is automatically renewed if not cancelled and the rules 2 and 3 apply
- One cannot cancel the deposit before 7th day

We have identified business rules, used examples supporting them and confirmed our assumptions with business. Now it’s time to make that specification runnable. The tool you pick depends mainly on your organization (i.e. what happens when the change or new feature is requested). 

## Pick the tool

If business wants to own the specification you should use a tool that allows modifying specification even by people that can’t code. (On JVM it would be Cucumber, Fitnesse, Concordion...). You should also appreciate their willingness to participate in the process and spend enough time pairing with them on improving the quality of the specification. On the other hand, if devs are the only ones that maintain the specification (i.e. if business wants to change something, asks devs to do so), you should probably continue using what you already know - some kind of an xUnit library, Spock Framework etc. There is no point paying the price of  additional layers of indirection and shared artifacts such as *.feature files if there is no one that we can share the artifacts with.
Let’s assume we are lucky enough to work in an environment in which all the parties collaborate to create and automate the specification.
We picked [Concordion](http://concordion.org/) as it makes editing specification pretty straightforward and it also provides nice-looking reports.

## Adapt specification
By adapt I mean modify it so that the tool of your choice can make use of it. You may need to add some markdown, tags, keywords, format it etc.
As we use Concordion to run our specification, we can instrument a markdown, which is the least verbose way of preparing it.

I have created a sample project that shows how you can do that and make it a part of your continuous integration pipeline
[https://github.com/michaelszymczak/blog-testing-business-rules-2 - step 1, instrumenting the specification](https://github.com/michaelszymczak/blog-testing-business-rules-2/tree/bfee066df1a21e3343ff6e0e8441a2cde7f10688)

In the next posts I will explain it in details, but for the time being let's focus of the outcome, which is a runnable specification verifying business rules.

If you try to run it

~~~bash
git clone --branch v1-instrumented https://github.com/michaelszymczak/blog-testing-business-rules-2
cd blog-testing-business-rules-2
./gradlew clean build
~~~

the project will be built and the link to the report will be displayed:

```
com.michaelszymczak.blog.testingbusinessrules2.CancellingDepositFixture STANDARD_OUT

    file:///some/path/blog-testing-business-rules-2/build/reports/spec/com/michaelszymczak/blog/testingbusinessrules2/CancellingDeposit.html
    Successes: 0, Failures: 10
```

The output of the report:

<iframe src="{{site.baseurl}}/blog/sourcecode/testingbusinessrules2/CancellingDepositReport-instrumented.html" sandbox  width="100%" height="500"></iframe>
 

## Progress update

It may not be obvious at first sight, but have we achieved a lot so far:

- Subject matter expert, QA tester and developer discussed various scenarios and identified business rules of the new functionality.
- QA tester with the developer's help adapted the specification so that it can be run
- We run the specification and marked all the cases as failing. Failure is expected at this stage as nothing is implemented yet.


In the next post I will focus on the technical side. I will explain how I created the specification,
how I was able to run it, verify the result and generate the report. It should give you enough confidence to be able
to try this approach in you project.

