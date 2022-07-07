import java.util.ArrayList;

/**
 * Nearest Neighbor TSP algorithm for the TSP. The algorithm includes three
 * different variations, the base heuristic version that starts from the
 * first vertex in the given graph, a repetitive version that runs
 * the algorithm on all the vertices as the starting point, and a third version
 * that uses the repetition, as well, as a 2OPT swap on each of the versions.
 * Each of these variations has a length of time / better solution trade off.
 */
public class NearestNeighbor extends TSP{

    private boolean repetitive;
    private boolean twoOptimal;

    /**
     * Default Constructor. Sets the graph to run the TSP algorithm on and
     * initializes the tour solution variables.
     * @param graph used in the TSP algorithm.
     */
    public NearestNeighbor(Graph graph){

        super(graph);
        this.algorithmName = "NN";
        this.repetitive = false;
        this.twoOptimal = false;
    }

    /**
     * Set which options to execute with the nearest neighbor algorithm.
     * @param repetitive nearest neighbor option
     * @param twoOptimal tour improvement
     */
    public void setAlgorithmOptions(boolean repetitive, boolean twoOptimal){

        this.repetitive = repetitive;
        this.twoOptimal = twoOptimal;

        // Set the title based on what algorithm options are chosen
        if(repetitive){ algorithmName += "R"; }
        if(twoOptimal){ algorithmName += "2OPT"; }
    }

    /**
     * Set which options to execute with the nearest neighbor algorithm.
     * @param repetitive nearest neighbor option
     * @param twoOptimal tour improvement
     */
    public void setAlgorithmOptions(boolean repetitive, boolean twoOptimal,
                                    UnitOfTime unit, long limit){

        this.unit = unit;
        this.limit = limit;
        this.hasTimeLimit = true;
        this.repetitive = repetitive;
        this.twoOptimal = twoOptimal;

        // Set the title based on what algorithm options are chosen
        if(repetitive){ algorithmName += "R"; }
        if(twoOptimal){ algorithmName += "2OPT"; }
    }

    /**
     * Executes the Nearest Neighbor Algorithm with the given parameter options
     * Three options can be executed, a single nearest neighbor run, repeatedly
     * running the nearest neighbor algorithm on all the vertices as the source
     * and a 2-OPT swap. These options are based on the parameters values.
     */
    public void executeNearestNeighbor(){

        timer.startTimer();

        ArrayList<Vertex> currentTour;
        long currentDistance;

        if(repetitive){ // Use repetitive nearest neighbor algorithm

            // Conduct a nearest neighbor search from each of the vertices
            // as the source vertex.
            for(Vertex vertex: graph.getAdjacencyList()){

                currentTour = nearestNeighbor(vertex);
                currentDistance = calculateTotalDistance(currentTour);

                if(twoOptimal){ // Use 2-opt if option was selected
                    currentTour = twoOptNeighborSwap(currentTour, currentDistance);
                    currentDistance = calculateTotalDistance(currentTour);
                }

                // Save the current tour if its better than the last
                if(currentDistance < distance){

                    distance = currentDistance;
                    tour = new ArrayList<Vertex>(currentTour);
                    printSolution(vertex.getIdentifier(), algorithmName);
                }

                // Exits the algorithm with the best route given a specified time
                if(timeLimitHasBeenReached()){
                    return;
                }
            }

        } else { // Use only nearest neighbor on source vertex

            currentTour = nearestNeighbor(graph.getSource());
            currentDistance = calculateTotalDistance(currentTour);

            if(twoOptimal){ // Use 2-opt if option was selected
                currentTour = twoOptNeighborSwap(currentTour, currentDistance);
                currentDistance = calculateTotalDistance(currentTour);
            }

            tour = currentTour;
            distance = currentDistance;

            printSolution(graph.getSource().getIdentifier(),
                    algorithmName);
        }

        timer.stopTimer();
    }

    /**
     * Runs the nearest neighbor algorithm given a source vertex.
     * @return algorithm's solution to the TSP.
     */
    private ArrayList<Vertex> nearestNeighbor(Vertex source){

        ArrayList<Vertex> tour = new ArrayList<Vertex>(graph.size());

        if(graph.size() > 1){   // Base Case

            // Reset the discovery values for all the vertices
            graph.resetAllDiscoveryValues(0);

            // Add the source to the path and set discovery values
            source.setColor(Color.BLACK);
            tour.add(source);

            Vertex u;
            Vertex v;

            // Continue to the next vertex, with the shortest distance while
            // there are still vertices that are not visited
            while(tour.size() < graph.size()){

                // Add the vertex to the path
                u = tour.get(tour.size() - 1);
                v = graph.getMinimumEdge(u);
                tour.add(v);

                // Mark vertices as visited
                v.setColor(Color.BLACK);
                u.setColor(Color.BLACK);
            }
        }

        return tour;
    }

    /**
     * 2-Opt tour improvement algorithm. Improves the current tour by
     * swapping edges (pairs of vertices) that improve the total distance.
     */
    private ArrayList<Vertex> twoOptNeighborSwap(ArrayList<Vertex> tour, long distance){

        int improve = 0;

        ArrayList<Vertex> newTour = new ArrayList<Vertex>(tour);
        long newTourDistance;

        // Continually improve until no improvements can be made
        while(improve < 2){

            // Iterate through for every valid point swap
            for(int i = 1; i < tour.size() - 1; i++){

                for(int j = i + 1; j < tour.size(); j++){

                    swap(tour, newTour, i, j); // Swap edges

                    newTourDistance = calculateTotalDistance(newTour);

                    // Keep the route changes if they are better after the swap
                    if(newTourDistance < distance){

                        tour = new ArrayList<Vertex>(newTour);
                        distance = newTourDistance;
                        improve = 0;
                    }

                    // Maximum time limit exit if reached
                    if(timeLimitHasBeenReached()){
                        return tour;
                    }
                }
            }

            improve ++;
        }

        return tour;
    }

    /**
     * Swaps the two edges (pair of vertices) in the tour
     * @param tour that current stands as the best tour
     * @param newTour that will have the edges swapped
     * @param i index for the tour swap vertex
     * @param j index for the tour swap vertex
     */
    private void swap(ArrayList<Vertex> tour, ArrayList<Vertex> newTour, int i, int j){

        // Copy lower tour portion before the edge swap
        for(int v = 0; v <= i - 1; v++){
            newTour.set(v, tour.get(v));
        }

        // Swap the edges
        int decrement = 0;
        for(int v = i; v <= j; v++){
            newTour.set(v, tour.get(j - decrement));
            decrement++;
        }

        // Copy upper tour portion after the edge swap
        for(int v = j + 1; v < tour.size(); v++){
            newTour.set(v, tour.get(v));
        }
    }

    /**
     * Calculates the total tour distance.
     * @param tour in order of travel.
     * @return total tour distance.
     */
    public long calculateTotalDistance(ArrayList<Vertex> tour){

        long distance = 0;

        // Calculates the distance between all the adjacent cities in the tour
        for(int i = 1; i < tour.size(); i++){
            distance += tour.get(i - 1).getPoint().distance(tour.get(i).getPoint());
        }

        // Add the return route to the distance
        distance += tour.get(0).getPoint().distance(tour.get(tour.size() - 1).getPoint());

        return distance;
    }

    /**
     * Prints the algorithm details to the console.
     */
    public void printAlgorithmDetails(){

        System.out.println("\nAlgorithm: " + algorithmName);

        if(hasTimeLimit){
            System.out.println("Max run time: "
                    + limit + " "
                    + unit.toString().toLowerCase());
        } else {
            System.out.println("Max run time: NONE");
        }
    }
}
