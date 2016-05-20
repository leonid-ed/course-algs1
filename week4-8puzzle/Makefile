.PHONY : all clean

all: Board Solver

SUBMIT_PACK = Board.java Solver.java
SUBMIT_NAME = 8puzzle

BIN=$(shell ls -Q --quoting-style=shell Solver\$$*.class Solver.class Board.class Board\$$*.class 2>/dev/null)

Board: Board.java
	javac-algs4 $<

Solver: Solver.java Board
	javac-algs4 $<

PuzzleChecker: PuzzleChecker.java Solver
	javac-algs4 PuzzleChecker.java

BoardTests: BoardTests.java Board
	javac-algs4 BoardTests.java
	java-algs4 -enableassertions BoardTests

zip: $(SUBMIT_PACK)
	zip $(SUBMIT_NAME).zip $(SUBMIT_PACK)

checkstyle: $(SUBMIT_PACK)
	checkstyle-algs4 $(SUBMIT_PACK)

findbugs: Board Solver
	findbugs-algs4 $(BIN)

clean:
	rm -f $(SUBMIT_NAME).zip
	rm -f *.class
