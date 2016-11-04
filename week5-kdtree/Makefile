.PHONY : all clean

all: PointSET

SUBMIT_PACK = PointSET.java KdTree.java
SUBMIT_NAME = kdtree

BIN=$(shell ls -Q --quoting-style=shell PointSET\$$*.class PointSET.class 2>/dev/null)

BruteRangeSearchVisualizer: BruteRangeSearchVisualizer.java
	javac-algs4 -Xlint $<

BruteNearestNeighborVisualizer: BruteNearestNeighborVisualizer.java
	javac-algs4 -Xlint $<

KdTreeVisualizer: KdTreeVisualizer.java
	javac-algs4 -Xlint $<

PointSET: PointSET.java
	javac-algs4 -Xlint $<

KdTree: KdTree.java
	javac-algs4 -Xlint $<

testBrute: PointSET.class BruteRangeSearchVisualizer.class BruteNearestNeighborVisualizer.class
	java-algs4 BruteNearestNeighborVisualizer input20K.txt

testKdTree: KdTree.class KdTreeVisualizer.class
	java-algs4 KdTreeVisualizer

zip: $(SUBMIT_PACK)
	zip $(SUBMIT_NAME).zip $(SUBMIT_PACK)

checkstyle: $(SUBMIT_PACK)
	checkstyle-algs4 $(SUBMIT_PACK)

findbugs: Board Solver
	findbugs-algs4 $(BIN)

clean:
	rm -f $(SUBMIT_NAME).zip
	rm -f *.class
