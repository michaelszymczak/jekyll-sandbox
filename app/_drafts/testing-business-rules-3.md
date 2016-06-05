---
layout: post
title:  "Testing Business Rules - part 3"
category: software
tags: ["behavior-driven-development", "specification-by-example"]
---


## Adapt specification
By adapt I mean modify it so that the tool of your choice can make use of it. You may need to add some markdown, tags, keywords, format it etc.
As we use Concordion to run our specification, we can instrument a markdown, which is the least verbose way of preparing it.

To understand how to create such specification and how to make it part of you continuous integration pipeline,
you may have a look at the sample project I have created:

[Sample project: https://github.com/michaelszymczak/blog-testing-business-rules-2 - step 1, instrumenting the specification](https://github.com/michaelszymczak/blog-testing-business-rules-2/tree/34d8610abd98ad871c5489e3818bee6ccddf65af)

The file CancellingDeposit.md is a instrumented markdown file that contains the specification. The structure may look odd
 
{% highlight md linenos=table %}
{% include sourcecode/testingbusinessrules2/CancellingDeposit.md %}
{% endhighlight %}

{% gist michaelszymczak/facdbb93d1598c6c14792085200afbc5 CancellingDepositFixture.java %}
