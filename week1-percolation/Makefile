.PHONY : all clean

all: Percolation PercolationStats

SUBMIT_PACK = Percolation.java PercolationStats.java
SUBMIT_NAME = percolation

BIN=$(shell ls -Q --quoting-style=shell Percolation\$$*.class Percolation.class PercolationStats.class PercolationStats\$$*.class 2>/dev/null)

InteractivePercolationVisualizer: InteractivePercolationVisualizer.java
	javac-algs4 InteractivePercolationVisualizer.java

Percolation: Percolation.java
	javac-algs4 Percolation.java

PercolationStats: PercolationStats.java Percolation.java
	javac-algs4 PercolationStats.java

zip: $(SUBMIT_PACK)
	zip $(SUBMIT_NAME).zip $(SUBMIT_PACK)

checkstyle: $(SUBMIT_PACK)
	checkstyle-algs4 $(SUBMIT_PACK)

findbugs: Percolation PercolationStats
	findbugs-algs4 $(BIN)

clean:
	rm -f $(SUBMIT_NAME).zip
	rm -f *.class
