import java.util.ArrayList;

/**
 * Traveling Salesman Problem. Gives a solution to TSP problem given a graph
 * with vertex identifiers, x-coordinates, and y-coordinates for each city.
 */
public class TSP {

    protected Graph graph;
    protected ArrayList<Vertex> tour;
    protected long distance;
    protected Timer timer;
    protected String algorithmName;
    protected boolean hasTimeLimit;
    protected UnitOfTime unit;
    protected long limit;

    /**
     * Default Constructor. Setups the Graph to run the TSP algorithm on and
     * initializes the remaining class variables to default values.
     * @param graph used in the TSP algorithm.
     */
    public TSP(Graph graph){

        this.graph = graph;
        this.tour = new ArrayList<Vertex>();
        this.distance = Long.MAX_VALUE;
        this.timer = new Timer();
        this.hasTimeLimit = false;
        this.limit = 0;
        this.unit = UnitOfTime.MINUTES;
    }

    /**
     * Returns the tour distance derived from the TSP algorithm.
     * @return tour distance.
     */
    public long getDistance(){
        return distance;
    }

    /**
     * Returns the tour derived from the TSP algorithm.
     * @return tour.
     */
    public ArrayList<Vertex> getTour(){
        return tour;
    }

    /**
     * Returns whether the algorithm has a an optimal solution annotated for it.
     * @return true if it has an optimal solution and false otherwise.
     */
    public double getOptimalSolutionRatio(long optimalSolution){

        return getDistance() / (double) optimalSolution;
    }

    /**
     * Returns the total executing time for the TSP algorithm in the specified
     * unit of time.
     * @param unit of time to return the execution time in.
     * @return total execution time to derive the TSP solution
     */
    public double getExecutionTime(UnitOfTime unit){
        return timer.getElapsedTime(unit);
    }

    /**
     * Returns the total executing time for the TSP algorithm as a string
     * representation.
     * @return total execution time to derive the TSP solution
     */
    public String getExecutionTime(){
        return timer.getElapsedTime();
    }

    /**
     * Writes the tour of the TSP to a file at the specified filename.
     * @param fileName to write the TSP results to.
     */
    public void outputPathToFile(String fileName){

        FileOutputProcessor file = new FileOutputProcessor(fileName);
        file.setup();
        file.writeDistanceToFile(distance);
        file.writePathToFile(tour);
        file.teardown();
    }

    /**
     * Prints the current solution distance and execution time to the console.
     * @param identifier for the source vertex
     */
    public void printSolution(int identifier, String algorithmUsed){

        System.out.println(identifier + " " + algorithmUsed
                + " - Tour Dist: " + distance
                + ", Time = " + timer.getElapsedTime());
    }

    /**
     * Checks if the time limit has been reached or not. If it has, a message
     * is printed to the console and the timer is stopped.
     */
    protected boolean timeLimitHasBeenReached(){

        if(hasTimeLimit){

            if(timer.getElapsedTime(unit) >= (limit * 0.99)){
                timer.stopTimer();
                return true;
            }
        }
        return false;
    }
}
