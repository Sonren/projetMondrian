JAVA_VERSION = 20
JFLAGS = -g
JC = javac
JAVA_MAIN_CLASS = src.Main

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		  Main.java \
		  Centre.java \
		  Image.java \
		  Plan.java\
		  Point.java\
		  Qtree.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
	
run:
	java main