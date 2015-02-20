run-all: 
	sbt startScript
	tracks/target/start &
	users/target/start &
	presentation/target/start &

clean:
	sbt clean
	rm -rf target/
	rm -rf users/target/
	rm -rf users/project/
	rm -rf tracks/target/
	rm -rf tracks/project/
	rm -rf presentation/target/
	rm -rf presentation/project/