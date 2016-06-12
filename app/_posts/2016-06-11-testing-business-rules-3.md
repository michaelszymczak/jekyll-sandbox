---
layout: post
title:  "Testing Business Rules - part 3"
category: software
tags: ["concordion", "behavior-driven-development", "specification-by-example"]
---

<p class="excerpt">
In the previous post I created and instrumented the specification that will guide us while implementing the feature.
In this post I am going to explain how it is possible that one can run such specification and check if the functionality works as expected.
</p>
<span class="readmore"/>

## Does it work in my context?

Whenever I read a tutorial or a blog post that explains how to do something, the first things
I want to know is if it works, if I can use in in the context of my particular project and if I can easily modify it later.
Otherwise, I have no idea if I should invest my time.
 
If you ask the same set of question this time, I want to assure you that:

- this is a working example - every time I change something in the project I build it to see if it still works
- it can be a part of you continuous integration workflow - I use Travis CI to build it for me every time I push changes to github
- you can adapt it and use it in your project - I will give you all the knowledge required to do so

However, don't take my word for it, build it yourself.

~~~bash
git clone --branch v2-instrumented https://github.com/michaelszymczak/blog-testing-business-rules-example
cd blog-testing-business-rules-example
./gradlew clean build
~~~


## Syntax
 
Picking a new tool or practice is a mixture or what you known already and what is new for you.
The syntax and keywords are no exception. Below you will find the content of
the [CancellingDeposit.md](https://github.com/michaelszymczak/blog-testing-business-rules-example/blob/master/src/test/resources/com/michaelszymczak/blog/testingbusinessrules2/CancellingDeposit.md)
file, that is the specification of the cancelling deposit functionality.
It looks like plain English, but has some funny brackets and tables.

```markdown
{% include sourcecode/testingbusinessrules2/CancellingDeposit.md %}
```

## Markdown 

First thing you need to know is that this is a [markdown file](https://daringfireball.net/projects/markdown/syntax).
You probably know the markdown syntax already as it is a quite popular format (e.g. You use it whenever you edit a README.md file on github).
However, if you are not familiar with the syntax, you should definitely spend some time and get used to it.

Let's jump to line 32 of the file:

```markdown
For [1][length] year long deposit # ...
```

In Markdown this is called a 'reference style link'. You are probably familiar with the notion of the 'note' (as in 'footnote' or 'endnote')
that is used in documents and papers - a number or a character placed next to the sentence that required further explanation etc. In this context, it is used
not to repeat yourself and remove duplication. Whenever you see ```[X][foo]```, X is some value that is displayed and
```foo``` is a reference. This reference is then replaced with the real content that can be found elsewhere in the document.
In our case, the explanation can be found in the line 2.

```markdown
[length]: - "#lengthInYears"
```

If you didn't use the reference link, you would have to type:

```markdown
For [1](- "#lengthInYears") year long deposit # ...
```

You can also use implicit link names omitting the content of the second bracket if the reference is the same as the displayed value.

```markdown
[foo]: - "#bar"

[foo][foo] 
[foo][]
```

Out of curiosity, I have transformed the line above from Markdown to HTML using one of the available Markdown parsers and got:

```html
<a href="-" title="#bar">foo</a>
```

You can conclude that the structure of the markdown link is:

```markdown
[WHAT YOU CAN SEE](LINK "TILE")
```

Everything I explained so far is not a concordion or the specification-related syntax.
It is merely the syntax of the markdown.

## Concordion syntax

Let's now focus on the concordion (the tool that transforms and executes our specification).
It has an [easy to follow documentation](http://concordion.org/instrumenting/java/markdown/) that you should read
if you want to use the tool in your next project.

In the meantime, I will explain a couple of things that you can find in the specification I created.

 - Lines 1-7: Reference notes. They are used to replace the reference links as already described.
 - Lines 9-26: Plain markdown, no logic. Used by humans to understand the context.
 - Lines 28-END Business rules with logic and example figures.
 
Take the first business rule, line 30:
 
```markdown
### [One receives the same amount as deposited if the deposit cancelled during the first half](- "first half c:status=ExpectedToFail")
```

```###``` denotes the header of the section that follows. The sections spans to the next header and marks the
logical boundary. All the values and logic is kept within that boundary.

```markdown
[One receives the same amount as deposited if the deposit cancelled during the first half](- "first half c:status=ExpectedToFail")
```

This is the header's title. It is used to display the title of the section, obviously, but this is also a markdown link.
If we didn't use concordion and render it using some Markdown parser, it would probably produce the following HTML code.

```html
<h3><a href="-" title="first half c:status=ExpectedToFail">One receives the same amount as deposited if the deposit cancelled during the first half</a></h3>
```
 
Concordion, however, does not treat links with the URL "-" as a regular links. They have special meaning.
The ```c:status=ExpectedToFail``` means that at least one test in this section should fail. At the moment all of the tests
fail as the feature is not implemented yet. This is quite important, as the specification
can be checked in and run by you continuous integration tool as soon as is created. Developers can pick it up and implement
the functionality, removing ```c:status=ExpectedToFail``` scenario by scenario, whenever they're ready.
It also encourages developers to slice the work along business requirements, not technical layers.

> ***Slicing work along business requirements, as opposed to technical layers,***
> ***improves visibility, predictability and gives a sense of progress.***

Line 32 and 33

```markdown
For [1][length] year long deposit with [100][initial] initial amount and [2][interestrate]% interest rate,
when I cancel it after MONTHS and DAYS:
```

I think you already recognize reference style links. If you scroll the document, you will find:
 
```markdown
[length]: - "#lengthInYears"
[initial]: - "#initialAmount"
[interestrate]: - "#interestRate"
```

Let's "mentally" inline the references:
 
```markdown
For [1](- "#lengthInYears") year long deposit with [100](- "#initialAmount") initial amount and [2](- "#interestRate")% interest rate,
when I cancel it after MONTHS and DAYS:
``` 

As we know, although it could be transformed into the HTML document with links, that's not the point. We know that the links with "-"
  URL have a special meaning. In this case, Concordion interprets them as a [set command](http://concordion.org/instrumenting/java/markdown/#set-command)
  The syntax of the set command is the following.
   
```markdown
[VALUE](- #VARIABLE_NAME)
# e.g.
[5](- #lengthInYears)
```

It assigns VALUE to variable VARIABLE_NAME, e.g. ```lengthInYears = 5;```
We can refer to that variable later in the specification, within the section it has been declared in.

So far we have assigned values of 3 variables.

```java
lengthInYears = 1
initialAmount = 100
interestRate = 2
```

Let's move to the table below.

```markdown
| [cancel][][MONTHS][months] | [DAYS][days] | [TRANSFERRED][transferred] ||
| :------------------------: | :----------: | :-------------------------: |
| 0                          | 7            | 100                         |
| 6                          | 0            | 100                         |
```

Again, this is a Markdown table syntax, but instead of creating links in the header, we process them.
The values in the body of the table are used to replace the values in the header. Thus, the table
 above is similar to (not exactly, but it is used only to visualize the concept):
 
```markdown
[cancel][] [0][months] [7][days] [100][transferred] 
[cancel][] [6][months] [0][days] [100][transferred]
```

As you can see, we have here our old friends, reference style links. Let's find the corresponding notes and expand the first line.

```markdown
[cancel](- "#result = cancel(#lengthInYears, #initialAmount, #interestRate, #months, #days)")
[0](- "#months") 
[7](- "#days") 
[100](- "?=#result.amountTransferred")
```

For clarity I split the example into separate lines. We already know the syntax of the second and third command - they're
 *set commands*. The first one is of a new type - an [execute command](http://concordion.org/instrumenting/java/markdown/#execute-command)
It should look familiar as it resembles invoking a function with some parameters and storing the result in a variable.

The last command is a [assert equals command](http://concordion.org/instrumenting/java/markdown/#assert-equals-command)
It verifies that variable/object field value equals the one provided.

If you paid attention you may have noticed that the order in which they occur in the specification is not the right one.
The execute command invoking cancel function relies on the value of the #months and #days, which are assigned *after*
the command is executed. Don't you worry though - Concordion reorders them so that assignments are executed first, execute commands next
and after that assertions. The fact that I put the execute command ```[cancel][]``` in the front of the header line makes no difference.
The real order is:

```markdown
[0](- "#months") 
[7](- "#days")
[cancel](- "#result = cancel(#lengthInYears, #initialAmount, #interestRate, #months, #days)")
[100](- "?=#result.amountTransferred")
```

In pseudocode, it may look as follows.

```java
months = 0;
days = 7;
result = cancel(lengthInYears, initialAmount, interestRate, months, days); 
assert result.amountTransferred == 100;
```

Some variables have been already assigned above the table. Let's replace the parameters with the assigned values.

```java
// above the table:
// lengthInYears = 1
// initialAmount = 100
// interestRate = 2
// in the table:
// months = 0;
// days = 7;

result = cancel(1, 100, 2, 0, 7); 
assert result.amountTransferred == 100;
```

## Where is the code?

All good, but one question remained unanswered: where is the definition of the cancel function?
Well, there is no magic here, we have to [create that code ourselves](https://github.com/michaelszymczak/blog-testing-business-rules-example/blob/v2-instrumented/src/test/java/com/michaelszymczak/blog/testingbusinessrules2/CancellingDepositFixture.java).

```java
{% include sourcecode/testingbusinessrules2/CancellingDepositFixture.java %}
```

I think the code is self explanatory. I use the cancel method that is invoked by concordion passing all the arguments I provided in the specification.
Then I return result of type Result that contains amountTransferred field that is used to compare it with the actual value. At the moment,
 the "TO BE IMPLEMENTED" result has been hardcoded so that we know that we have no implementation in place yet.
 
## Summary

I believe I convinced you that this is a working example that can be a part of you continuous integration workflow
 and you can adapt it and use it in your project. If you are curious how you can do that taking Behaviour Driven Development,
 and Test Driven Development approach, wait for the next posts!
