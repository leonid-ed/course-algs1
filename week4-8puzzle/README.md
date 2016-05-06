## Helpful links

  * [Programming Assignment 4: 8 Puzzle: Instructions](https://class.coursera.org/algs4partI-010/assignment/view?assignment_id=5)

  * [A* search algorithm for newbies (rus.)](http://www2.in.tu-clausthal.de/~zach/teaching/info_literatur/A_Star/A_star_tutorial/aStarTutorial_rus.htm.html)

## TODO List

* implement class Solver
  * ~~an additional class is necessary? (class Case)~~
  * figure out how to print out result sequence of boards
  * figure out how to detect unsolvable cases

## Notes

Solver doesn't work properly:
```sh
$ make PuzzleChecker
$ java-algs4 PuzzleChecker puzzle4x4-??.txt
puzzle4x4-00.txt: 0
puzzle4x4-01.txt: 1
puzzle4x4-02.txt: 2
puzzle4x4-03.txt: 3
puzzle4x4-04.txt: 4
puzzle4x4-05.txt: 5
puzzle4x4-06.txt: 6
puzzle4x4-07.txt: 7
puzzle4x4-08.txt: 8
puzzle4x4-09.txt: 9
puzzle4x4-10.txt: 10
puzzle4x4-11.txt: 11
puzzle4x4-12.txt: 12
puzzle4x4-13.txt: 13
puzzle4x4-14.txt: 14
puzzle4x4-15.txt: 15
puzzle4x4-16.txt: 16
puzzle4x4-17.txt: 17
puzzle4x4-18.txt: 18
puzzle4x4-19.txt: 19
puzzle4x4-20.txt: 20
puzzle4x4-21.txt: 21
puzzle4x4-22.txt: 22
puzzle4x4-23.txt: 23
puzzle4x4-24.txt: 24
puzzle4x4-25.txt: 25
puzzle4x4-26.txt: 26
puzzle4x4-27.txt: 27
puzzle4x4-28.txt: 28
puzzle4x4-29.txt: 29
puzzle4x4-30.txt: 30
puzzle4x4-31.txt: 31
puzzle4x4-32.txt: 26
puzzle4x4-33.txt: 33
puzzle4x4-34.txt: 34
puzzle4x4-35.txt: 35
puzzle4x4-36.txt: 36
puzzle4x4-37.txt: 37
```
It has a problem with "No solution possible" so this checking is disabled.