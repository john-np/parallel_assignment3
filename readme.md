
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

It will not compile because i


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
Utilizes almost the same LL as problem 1, with a few attempted modifications.
Under a nested loop, outer loop cycles between minutes in an hour (each iteration of the loop is another
minute). Inner for loop inside the TemperatureThread class is supposed to hit
a CyclicBarrier every 10 iterations. With this, the threads wait for each other
to get to the same area before calling an unfinished function that would have
calculated the 10 minute intervals.