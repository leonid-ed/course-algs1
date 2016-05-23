## Helpful links

  * [Programming Assignment 4: 8 Puzzle: Instructions](https://class.coursera.org/algs4partI-010/assignment/view?assignment_id=5)

  * [A* search algorithm for newbies (rus.)](http://www2.in.tu-clausthal.de/~zach/teaching/info_literatur/A_Star/A_star_tutorial/aStarTutorial_rus.htm.html)

## TODO List

* implement class Solver
  * ~~an additional class is necessary? (class Case)~~
  * ~~figure out how to print out result sequence of boards~~
  * ~~figure out how to detect unsolvable cases~~
  * figure out how to decrease memory usage
    ```
    How can I reduce the amount of memory a Board uses?
    For starters, recall that an N-by-N int[][] array in Java uses about 24 + 32N + 4N^2 bytes; when N equals 3, this is 156 bytes. To save memory, consider using an N-by-N char[][] array or a length N^2 char[] array. In principle, each board is a permutation of size N^2, so you need only about lg ((N^2)!) bits to represent it; when N equals 3, this is only 19 bits.
    ```

## Notes

Assignment evaluation is located in text file 'assessment_summary'
