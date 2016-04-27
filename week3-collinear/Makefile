.PHONY : all clean

all: BruteCollinearPoints FastCollinearPoints

Point: Point.java
	javac-algs4 Point.java

LineSegment: LineSegment.java
	javac-algs4 LineSegment.java

BruteCollinearPoints: BruteCollinearPoints.java
	javac-algs4 BruteCollinearPoints.java

FastCollinearPoints: FastCollinearPoints.java
	javac-algs4 FastCollinearPoints.java

zip: Point.java BruteCollinearPoints.java FastCollinearPoints.java
	zip collinear.zip Point.java BruteCollinearPoints.java FastCollinearPoints.java

clean:
	rm -f collinear.zip *.class