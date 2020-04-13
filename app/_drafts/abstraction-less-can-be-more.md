---
layout: post
title:  "Abstraction - less can be more"
category: software
tags: ["software", "design", "software-design", "java"]
---

```bash
$ echo "foo" > input.txt && \
  less input.txt > a && \
  more input.txt > b && \
  diff a b && \
  echo "same" || echo "different"

same
```

&#8718; Q.E.D.

On a more serious note, 
