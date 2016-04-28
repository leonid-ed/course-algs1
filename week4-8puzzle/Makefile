.PHONY : all clean

all: Board Solver

SUBMIT_PACK = Board.java Solver.java
SUBMIT_NAME = 8puzzle

Board: Board.java
	javac-algs4 Board.java

Solver: Solver.java
	javac-algs4 Solver.java

PuzzleChecker: PuzzleChecker.java
	javac-algs4 PuzzleChecker.java

zip: $(SUBMIT_PACK)
	zip $(SUBMIT_NAME).zip $(SUBMIT_PACK)

checkstyle: $(SUBMIT_PACK)
	checkstyle-algs4 $(SUBMIT_PACK)

findbugs: Board.class Solver.class
	findbugs-algs4 *.class

clean:
	rm -f $(SUBMIT_NAME).zip
	rm -f *.class
