.PHONY : all clean

all: Board Solver

SUBMIT_PACK = Board.java Solver.java
SUBMIT_NAME = 8puzzle

Board: Board.java
	javac-algs4 $<

Solver: Solver.java Board
	javac-algs4 $<

PuzzleChecker: PuzzleChecker.java Solver
	javac-algs4 $<

BoardTests: BoardTests.java Board
	javac-algs4 BoardTests.java
	java-algs4 BoardTests

zip: $(SUBMIT_PACK)
	zip $(SUBMIT_NAME).zip $<

checkstyle: $(SUBMIT_PACK)
	checkstyle-algs4 $<

findbugs: Board.class Solver.class
	findbugs-algs4 $<

clean:
	rm -f $(SUBMIT_NAME).zip
	rm -f *.class
