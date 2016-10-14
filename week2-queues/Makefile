.PHONY : all clean

all: Deque DequeTests RandomizedQueue RandomizedQueueTests Subset


Deque: Deque.java
	javac-algs4 Deque.java

DequeTests: DequeTests.java
	javac-algs4 DequeTests.java

RandomizedQueue: RandomizedQueue.java
	javac-algs4 RandomizedQueue.java

RandomizedQueueTests: RandomizedQueueTests.java
	javac-algs4 RandomizedQueueTests.java

Subset: Subset.java
	javac-algs4 Subset.java

zip: RandomizedQueue.java Deque.java Subset.java
	zip queues.zip Deque.java RandomizedQueue.java Subset.java

clean:
	rm -f queues.zip *.class
