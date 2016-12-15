all: src
	find . -name "*.java" -print | xargs javac -d bin
	jar cvf import.jar bin
outsiterz: import.jar
	java -Dfile.encoding=UTF-8 -classpath bin:mysql-connector-java-5.1.24.jar:import.jar engine.OutsiterzMigracia
saf: import.jar
	java -Dfile.encoding=UTF-8 -classpath bin:mysql-connector-java-5.1.24.jar:import.jar engine.SafMigracia saf_data.csv
