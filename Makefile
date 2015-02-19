all:
	sbt clean test startScript

build:
	sbt clean startScript

test:
	sbt clean test

clean:
	sbt clean

run: build
	target/start