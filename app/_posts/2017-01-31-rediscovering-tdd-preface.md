---
layout: post
title:  "Rediscovering TDD - Preface"
category: software
tags: ["software-craftsmanship", "tdd", "test-driven-development"]
---

<p class="excerpt">
Almost every software developer at some point comes across Test Driven Development.
As far as I remember I was lucky enough to try it just after I started my professional career.
I remember my first interview and my first day at work. I remember how surprised and happy I was that some company
trusted me enough to allow me to change their codebase - and to be rewarded for it. 
Thus, soon after I joined, I spent almost all my free time learning about the framework I used and about
all these practices every developer should do. Proper testing, better even, Test Driven Development, was one of them. 
</p>
<span class="readmore"/>

<p>
Being a freshman, surrounded by seasoned developers is a good place to be. It marks the beginning of your journey.
It's like the last position in a race - can be only better. As some say - try to be the most stupid person in a room, or something similar.
Well, as long as you try to catch up of course. So I tried. Obviously, I had never worked with such amount of legacy code before.
By legacy I mean easy to break, hard to develop. Especially for a rookie. At some point it started occurring to me that even if I had been
as careful as possible, I wouldn't have been able to prove that I had not broken anything. It was partially because I was't clear what the
previous expected behaviour was and how to find an answer for that question.
</p>

<p>
It wasn't that applications didn't work - they did. It was just this feeling that we can do better than this.
I felt bad that I could not deliver more value. I also had to prove to me and the whole company that it wasn't a mistake to hire me.
I had to act as a software developer, not as an amateur, before my employer and colleagues wise up to the fact that I was a student in disguise,
pretending to be a real software developer. I had to act quickly. Test driven development, proper design, maintainability, zero-bugs policy, modularity
were the bare-minimum. If you want to be paid for it, all of the stuff mentioned above is a prerequisite.
It's like driving licence for a cab driver. This was my idea of the software industry.
</p>

<p>
I will probably never forget this day. I was almost a software developer, with 4 to 6 months of experience with a sandwich bough on my way to work and a mug
of hot tea, perched as always close enough to the keyboard to surely violate some health and safety rules.
Sipping the tea, still a bit sleepy, I started the day as usual.
Fetch new changes from the central repo.
Check if all tests still pass and the app still works.
Check which requirements from the TODO list are not implemented yet.
Take the highest on the list. Write failing test.
Out of the corner of the eye observe that second screen turns red.
Write implementation. Second screen turns green.
Refactor a bit. Rinse and repeat.
</p>

<p>
You know when someone observes what you do and looks over your shoulder on your screen.
You just do. Especially when one of your screens is glossy.
</p>

-- *Guys..., this guy writes tests.*


It was one of the most senior developers in the company talking to few other guys
that as it turned out, had been gathering behind my desk for a while.


-- *Hmm, red, green, they must be some tests. Are you writing tests?* - continued another when noticed that I am aware of their presence.


-- *Yeah, this is a brand new part of the app so I though it's good idea to test drive it to have a good...*
 
 
-- *Guys seriously, come here! He. Is. Writing. TESTS!* - I was outshouted by my senior colleague.
 
 
At that point I noticed people coming from different rooms in the company. The commotion was easily noticeable.


-- *Who asked you to write tests?* - the senior dev continued, ignoring the fact that the room became bit too crowded.

 
-- *Well, nobody, but truth to be told, nobody asked me not to do so either. Is something wrong?*
 
 
Couple of days later I helped other teams to install and run xUnit libraries in a couple of projects, as at that point everybody in the company knew that
I knew how to do it. I was the change I sought. Unintentionally. It was long long time ago, but to this day I can't figure out what happened that morning.
I simply didn't know what the status quo was. As of today, the company has, as far as I know, transformed completely, improving many processes
and engineering practices and has become one of the most prominent players in their field in this part of the continent. Was it me that ignited this
"software craftsmanship" transformation that helped them achieve it? I doubt, and definitely not alone. Nevertheless, it's a funny story.

## Where is TDD?

Dear reader, I owe you apologies. After reaching the end of the article you probably realised that it wasn't a lot about of TDD. It was about how my
TDD journey began. In the next one, I will try to write almost exclusively about TDD and how I understand it. You may have read a lot articles about TDD,
but I hope you have never read the same as the one I will publish soon. 
