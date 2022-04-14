
# Assignment 3



## Acknowledgements

 - Art of Multiprocessor Programming, utilized and modified book's lock-free list
## Compile Instructions
### Problem 1:

javac Party.javac

java Party

There are user editable variables on the top.
Change printToggle to true in order to see operations conducted on the linked list.

### Problem 2:

javac Party.javac

java Party

### Disclaimer: 
Problem 2 was left mostly unfinished, I'll sketch out my attempted solution, but the code
ultimately does NOT work beyond multiple threads generating random temperatures.


## Solution Sketches

### Problem 1
Utilized a lock-free list as the textbook emphasized its ability to avoid potential
contention issues by not using locks.
Solution functions by first utilizing an ArrayBlockingQueue that is fed a
shuffled ArrayList<Integer>.

8 threads run the problem by rolling a die with a RNG in order to determine
if they should either make a call to contains or add or remove. Add function
is immediately followed by remove in order to simulate alternating requirement in the problem.

Threads alternate between adding and removing, with a contains call occassionally being thrown in
by the minotaur.



### Problem 2

