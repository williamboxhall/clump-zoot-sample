run-all: 
	(cd tracks/; make run)
	(cd users/; make run)

clean-all:
	(cd tracks/; make clean)
	(cd users/; make clean)