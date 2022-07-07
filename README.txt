——————————————————————————————————————————--------------------------------------
                        Running Program
——————————————————————————————————————————--------------------------------------

1. Load the following files in the same directory:

	Main.java
	Color.java
	Graph.java
	FileInputProcessor.java
	FileOutputProcessor.java
	NearestNeighbor.java
	Point.java
	Timer.java
	TSP.java
	UnitOfTime.java
	Vertex.java

	Include file(s) that will be used to run the tests, i.e.
	tsp_example_1.txt tsp_example_2.txt, test-input-1.txt, etc. in the same
	directory as the java files above.

2. Command Line Argument Details

    This application uses command line arguments to get the tsp file name,
    select which algorithm to use, specify a maximum run time and set the
    optimal tour length calculation for the given tsp file. The following
    format MUST be used to run the application unless the default values are
    what's desired:

    Algorithm choices: (Command line argument - description)
    NN - Nearest Neighbor
    NNR - Nearest Neighbor Repetitive
    NN2OPT - Nearest Neighbor with 2-opt tour improvement
    NNR2OPT - Nearest Neighbor Repetitive with 2-opt tour improvement

    Maximum Program Run Time (Command line argument - integer - description)
    MTS-integer : maximum run time with the given value in seconds
    MTM-integer : maximum run time with the given value in minutes
    MTH-integer : maximum run time with the given value in hours
    NONE : do not include a maximum run time

    Optimal tour length: (Command line argument)
    integer
    NONE

    Examples of valid input:

    tsp_example_1.txt NNR MTS-180 NONE
    tsp_example_2.txt NNR2OPT MTH-2 10982
    tsp_example_3.txt NNR MTM-3 NONE
    tsp_example_1.txt

    Examples of invalid input:

    tsp_example_1.txt NNR 1098
    tsp_example_1.txt NONE MTS-1000 NNR

    ### If just the file name is included in the command line arguments, the
    application will run the NNR2OPT algorithm with no maximum run time
    and no optimal tour length.

3. Run the following commands in SSH:

	javac Main.java
	java Main command line arguments as noted above

4. Command line arguments for the tsp_example_#.txt and test-input-#.txt files

    Example Test Cases

    tsp_example_1.txt NNR2OPT NONE 108159
    tsp_example_1.txt NNR2OPT NONE 2579
    tsp_example_1.txt NN2OPT NONE 1573804

    Competition Unlimited Test Cases

    test-input-1.txt NNR2OPT NONE NONE
    test-input-2.txt NNR2OPT NONE NONE
    test-input-3.txt NNR2OPT NONE NONE
    test-input-4.txt NNR2OPT NONE NONE
    test-input-5.txt NNR2OPT NONE NONE
    test-input-6.txt NN2OPT MTH-12 NONE
    test-input-7.txt NNR MTH-16 NONE

    Competition Test Cases

    test-input-1.txt NNR2OPT MTS-180 NONE
    test-input-2.txt NNR2OPT MTS-180 NONE
    test-input-3.txt NNR2OPT MTS-180 NONE
    test-input-4.txt NNR2OPT MTS-180 NONE
    test-input-5.txt NNR2OPT MTS-180 NONE
    test-input-6.txt NN2OPT MTS-180 NONE
    test-input-7.txt NNR MTS-180 NONE