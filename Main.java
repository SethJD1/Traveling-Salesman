import java.text.DecimalFormat;

/**
 * Runs the Traveling Salesman Problem (TSP) algorithm on a set of given
 * locations. A file is read in from the command line arguments containing
 * the locations to find the best tour length. Additionally, various options
 * can be given in the command line to alter execution variables
 * such as executing the algorithm in an set amount of time,
 * which algorithm(s) to use and ratio to optimal calculations.
 * After the TSP algorithm runs, the results are printed to a file with the
 * .tour extension added to the original file name.
 */
public class Main {

    private static FileInputProcessor file;
    private static Graph graph;
    private static final String[] TSP_ALGORITHMS = new String[]
            {"NN", "NNR", "NN2OPT", "NNR2OPT"};
    private static final String[] MAX_RUN_TIME = new String[]
            {"NONE", "MTS", "MTM", "MTH"};
    private static Timer timer;

    /**
     * Executes the TSP algorithm with the given options. The first command
     * line argument must be the file name for the location, the second
     * is the algorithm to use, the third option is the amount of time to run
     * the algorithm before it exits and the fourth is the optimal solution.
     * Below is the list of options and how they must be entered:
     *
     * Arg[0] - file name for the locations.
     * Arg[1-3] - Additional execution options.
     *
     * Algorithm to use
     *      NN - Nearest neighbor
     *      NNR - Nearest Neighbor repetition
     *      NN2OPT - Nearest neighbor with 2OPT Swap
     *      NNR2OPT - Nearest neighbor repetition with 2-optimal Swaps
     *
     * Max time to run the algorithm follow by its value
     *      MTS-seconds maximum time in seconds
     *      MTM-minutes maximum time in minutes
     *      MTH-hours maximum time in hours
     *      NONE
     *
     * Optimal tour length for the given file
     *      integer
     *      NONE
     *
     * A complete command line examples would be:
     *      TSP_loc.txt NNR2OPT MTH-2.0 10953
     *      TSP_loc.txt NN NONE NONE
     *      TSP_loc.txt NNR MTM-3.5 NONE
     *      Tsp_loc.txt
     *
     * If nothing but a filename is provided, the program defaults to the
     * NNR2OPT options without a maximum time limit or optimal tour length.
     *
     * @param args file name and options to run the TSP algorithm.
     */
    public static void main(String[] args) {

        timer = new Timer();
        timer.startTimer(); // Starts the timer for the maximum time limit

        System.out.println("\n--------------- Traveling Salesman Problem ----" +
                "-----------\n");

        if(hasArguments(args)){ // Check if user provided a file

            if(isValidFile(args[0])){ // Check if file is valid

                graph = new Graph();
                graph.fillGraphContentsFromFile(file);

                if(hasOptionsToAdd(args)){ // Execute with options

                    executeWithOptions(args);

                } else { // Execute default options
                    executeDefaultOptions(args[0]);
                }

            } else { // Print an error message since the file is invalid

                System.out.println("The file provided is NOT valid...");
                System.out.println("Please rerun the application with a valid file.");
            }

        } else { // Print an error message since no file has been provided

            System.out.println("NO file was provided to process...");
            System.out.println("Please rerun the application with a valid file.");
        }

        System.out.println("\n-----------------------------------------------" +
                "-----------\n");
    }

    /**
     * Executes the the TSP algorithm with default options which entails running
     * the repetitive nearest neighbor algorithm with 2-optimal tour improvement,
     * no time limit and no details on the ratio to optimal.
     * @param fileName to read in the tour values from.
     */
    private static void executeDefaultOptions(String fileName){

        NearestNeighbor nn = new NearestNeighbor(graph);
        nn.setAlgorithmOptions(true, true);
        nn.printAlgorithmDetails();

        System.out.println("\nDisplaying current best distances...\n");

        nn.executeNearestNeighbor();
        nn.outputPathToFile(fileName + ".tour");

        System.out.println("\nBest Distance: " + nn.getDistance());

        timer.stopTimer(); // Stops the total execution time
        System.out.println("Total Execution Time: " + timer.getElapsedTime());
    }

    /**
     * Executes the TSP algorithm with the options provided by the user.
     * @param options to use for the TSP algorithm execution.
     */
    private static void executeWithOptions(String[] options){

        final int ALGORITHM = 1;
        final int MAX_TIME = 2;
        final int OPTIMAL = 3;

        DecimalFormat format = new DecimalFormat("#.####");

        NearestNeighbor nn = new NearestNeighbor(graph);

        String[] timeValues = options[MAX_TIME].split("-");
        boolean[] algorithms = algorithmChoice(options[ALGORITHM]);

        // Check if the maximum run time values are valid
        if(areTimeOptionsValid(timeValues)){
            nn.setAlgorithmOptions(algorithms[0], algorithms[1],
                    maxTimeUnit(timeValues[0]), maxTime(timeValues[1]));
        } else { // If not run without maximum run time
            nn.setAlgorithmOptions(algorithms[0], algorithms[1]);
            System.out.println("\n# Provided maximum run time is none.");
        }

        nn.printAlgorithmDetails();

        System.out.println("\nDisplaying current best distances...\n");

        nn.executeNearestNeighbor(); // Execute the algorithm

        if(nn.timeLimitHasBeenReached()){
            System.out.println("\nUsing best result within time limit...");
        }

        nn.outputPathToFile(options[0] + ".tour"); // Print the results

        System.out.println("\nBest Distance: " + nn.getDistance());

        if(isOptimalTourLengthValid(options[OPTIMAL])){ // Print ratio if provided
            System.out.println("Ratio to Optimal: "
                    + format.format(nn.getOptimalSolutionRatio(optimalTourLength(options[OPTIMAL]))));
        }

        timer.stopTimer();

        if(maxTimeUnit(timeValues[0]) == UnitOfTime.NONE){
            System.out.println("Total Execution Time: "
                    + timer.getElapsedTime());
        } else {
            System.out.println("Total Execution Time: "
                    + timer.getElapsedTime(maxTimeUnit(timeValues[0]))
                    + " " + maxTimeUnit(timeValues[0]).toString().toLowerCase());
        }
    }

    /**
     * Checks if the optimal tour length is valid.
     * @param option to check for the optimal tour length
     * @return true if is a number and false otherwise
     */
    private static boolean isOptimalTourLengthValid(String option){

        try { // Check if number

            Long.parseLong(option);
            return true;

        } catch(NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the value of the optimal tour length from the command line
     * argument.
     * @param option in the command line arguments.
     * @return optimal tour length as a long.
     */
    private static long optimalTourLength(String option){

        final String NONE = "NONE";
        long optimalTourLength;

        if(option.compareTo(NONE) != 0){

            optimalTourLength = Long.parseLong(option);

        } else {
            optimalTourLength = 0;
        }

        return optimalTourLength;
    }

    /**
     * Returns the options for the algorithm choice.
     * @param option in the command line argument.
     * @return boolean array of selected options.
     */
    private static boolean[] algorithmChoice(String option){

        boolean twoOptimal = false;
        boolean repetitive = false;

        if(option.compareTo(TSP_ALGORITHMS[1]) == 0){
            repetitive = true;
        } else if(option.compareTo(TSP_ALGORITHMS[2]) == 0){
            twoOptimal = true;
        } else if(option.compareTo(TSP_ALGORITHMS[3]) == 0){
            repetitive = true;
            twoOptimal = true;
        }

        return new boolean []{repetitive, twoOptimal};
    }

    /**
     * Checks if the time options are valid parameters
     * @param timeOptions to check for validity.
     * @return true if both options are valid and false otherwise.
     */
    private static boolean areTimeOptionsValid(String[] timeOptions){

        // Check if both options are present
        if(timeOptions.length != 2){

            return false;

        } else { // Check the validity of both

            try { // Check if number, then if not none

                Long.parseLong(timeOptions[1]);
                return maxTimeUnit(timeOptions[0]) != UnitOfTime.NONE;

            } catch(NumberFormatException e) {
                return false;
            }
        }
    }

    /**
     * Returns the max time to run the TSP algorithm.
     * @param option in the command line argument.
     * @return max time
     */
    private static long maxTime(String option){

        return Long.parseLong(option);
    }

    /**
     * Returns the maximum time unit to run the TSP algorithm.
     * @param option in the command line argument.
     * @return max time unit
     */
    private static UnitOfTime maxTimeUnit(String option){

        UnitOfTime selected = UnitOfTime.NONE;

        if(option.compareTo(MAX_RUN_TIME[1]) == 0){
            selected = UnitOfTime.SECONDS;
        } else if(option.compareTo(MAX_RUN_TIME[2]) == 0){
            selected = UnitOfTime.MINUTES;
        } else if(option.compareTo(MAX_RUN_TIME[3]) == 0){
            selected = UnitOfTime.HOURS;
        }

        return selected;
    }


    /**
     * Checks if the user provided options to run in the command line.
     * @param options currently provided by the user.
     * @return true if the user provided options and false otherwise.
     */
    private static boolean hasOptionsToAdd(String[] options){

        if(options.length != 4){

            System.out.println("\n# Invalid options,running default configuration");
            return false;
        }
        return true;
    }

    /**
     * Check is the given file name command line argument is a valid file.
     * @param fileName to check its validity
     * @return true if the file is valid and false otherwise.
     */
    private static boolean isValidFile(String fileName){

        file = new FileInputProcessor(fileName);
        return file.isValidFile();
    }

    /**
     * Checks if the command line has arguments.
     * @param args to check.
     * @return true if there are the specified number of arguments or false
     * otherwise.
     */
    private static boolean hasArguments(String[] args){

        return args.length >= 1;
    }
}
