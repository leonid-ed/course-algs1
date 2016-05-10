## Helpful links

  * [Programming Assignment 4: 8 Puzzle: Instructions](https://class.coursera.org/algs4partI-010/assignment/view?assignment_id=5)

  * [A* search algorithm for newbies (rus.)](http://www2.in.tu-clausthal.de/~zach/teaching/info_literatur/A_Star/A_star_tutorial/aStarTutorial_rus.htm.html)

## TODO List

* implement class Solver
  * ~~an additional class is necessary? (class Case)~~
  * ~~figure out how to print out result sequence of boards~~
  * figure out how to detect unsolvable cases
  * figure out how to decrease memory usage

## Notes

Solver doesn't work properly:
```sh
$ make PuzzleChecker
$ java-algs4 PuzzleChecker puzzle47.txt
Error: Java Memory Exception
```
It has a problem with "No solution possible" so this checking is disabled.
