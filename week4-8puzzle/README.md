## Helpful links

  * [Programming Assignment 4: 8 Puzzle: Instructions](https://class.coursera.org/algs4partI-010/assignment/view?assignment_id=5)

  * [A* search algorithm for newbies (rus.)](http://www2.in.tu-clausthal.de/~zach/teaching/info_literatur/A_Star/A_star_tutorial/aStarTutorial_rus.htm.html)

## TODO List

* implement class Solver
  * ~~an additional class is necessary? (class Case)~~
  * figure out how to detect unsolvable cases

## Notes

Solver doesn't work properly:
```sh
$ make PuzzleChecker
$ java-algs4 PuzzleChecker puzzle??.txt
puzzle00.txt: 0
puzzle01.txt: 1
puzzle02.txt: 2
puzzle03.txt: 3
puzzle04.txt: 4
puzzle05.txt: 5
puzzle06.txt: 6
puzzle07.txt: 7
puzzle08.txt: 8
puzzle09.txt: 9
puzzle10.txt: 10
puzzle11.txt: -1
puzzle12.txt: 12
puzzle13.txt: 13
puzzle14.txt: -1
puzzle15.txt: 15
puzzle16.txt: -1
puzzle17.txt: -1
puzzle18.txt: -1
puzzle19.txt: -1
puzzle20.txt: -1
puzzle21.txt: -1
puzzle22.txt: -1
puzzle23.txt: -1
puzzle24.txt: -1
puzzle25.txt: -1
puzzle26.txt: -1
puzzle27.txt: -1
puzzle28.txt: -1
puzzle29.txt: -1
puzzle30.txt: -1
puzzle31.txt: -1
puzzle32.txt: -1
puzzle33.txt: -1
puzzle34.txt: -1
puzzle35.txt: -1
puzzle36.txt: -1
puzzle37.txt: -1
puzzle38.txt: -1
puzzle39.txt: -1
puzzle40.txt: -1
puzzle41.txt: -1
puzzle42.txt: -1
puzzle43.txt: -1
puzzle44.txt: -1
puzzle45.txt: -1
puzzle46.txt: -1
puzzle47.txt: -1
puzzle48.txt: -1
puzzle49.txt: -1
puzzle50.txt: -1
```
It has a problem with false "No solution possible".