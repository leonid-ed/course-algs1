.PHONY : all clean

all: Percolation PercolationStats

InteractivePercolationVisualizer: InteractivePercolationVisualizer.java
	javac-algs4 InteractivePercolationVisualizer.java

Percolation: Percolation.java
	javac-algs4 Percolation.java

PercolationStats: PercolationStats.java Percolation.java
	javac-algs4 PercolationStats.java

zip: PercolationStats.java Percolation.java
	zip percolation.zip Percolation.java PercolationStats.java

clean:
	rm -f percolation.zip Percolation.class PercolationStats.class
