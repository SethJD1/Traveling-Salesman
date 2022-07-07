import java.util.ArrayList;

/**
 * Generates an adjacency matrix of a complete weighted graph.
 */
public class Graph {

    private ArrayList<Vertex> adjacencyList;
    private Vertex source;

    /**
     * Constructor. Creates an empty graph.
     */
    public Graph() {

        adjacencyList = new ArrayList<Vertex>();
        source = null;
    }

    /**
     * Returns the size of the graph which represents the number of vertices.
     * @return size of the graph.
     */
    public int size() {
        return adjacencyList.size();
    }

    /**
     * Returns whether the graph is empty or not.
     * @return true if the graph is empty and false otherwise.
     */
    public boolean isEmpty() {

        return adjacencyList.isEmpty();
    }

    /**
     * Returns the graph adjacency list.
     * @return adjacency list of vertices.
     */
    public ArrayList<Vertex> getAdjacencyList(){

        return adjacencyList;
    }

    /**
     * Returns the edge with the minimum distance from the source vertex to
     * any of destinations that have not already be visited.
     * @param source to get it edge from
     * @return Edge with minimum distance from the source.
     */
    public Vertex getMinimumEdge(Vertex source){

        Vertex destination = null;

        long minDistance = Integer.MAX_VALUE;

        // Loop through adjacency list get the minimum edge distance
        for(Vertex v: adjacencyList){

            if(source.getPoint().distance(v.getPoint()) < minDistance
                    && v.getColor() != Color.BLACK){
                minDistance = source.getPoint().distance(v.getPoint());
                destination = v;
            }
        }

        return destination;
    }

    /**
     * Returns the currently set source for the graph.
     * @return current source.
     */
    public Vertex getSource(){
        return source;
    }

    /**
     * Sets the source identified by its identifier.
     * @param identifier for the source.
     */
    public void setSource(int identifier){

        source = adjacencyList.get(identifier);
    }

    /**
     * Resets all the discovery values in the graph vertices.
     * @param keyValue to set the key to
     */
    public void resetAllDiscoveryValues(int keyValue){

        for(Vertex u: adjacencyList) {
            u.clearDiscoveryValues(keyValue);
        }
    }

    /**
     * Fills the graph with content from a file.
     * @param file to read the data from.
     */
    public void fillGraphContentsFromFile(FileInputProcessor file) {

        System.out.println("Filling Graph Content from " + file.getFileName()
                + "...");

        file.setup();

        ArrayList<Vertex> vertices = file.getVerticesFromFile();
        fillVertices(vertices);

        System.out.println("Reading In file Complete");
        setSource(vertices.get(0).getIdentifier());

        file.teardown();
    }

    /**
     * Fills the vertices in the graph from an ArrayList of vertices.
     * @param vertices to add to the graph.
     */
    private void fillVertices(ArrayList<Vertex> vertices){

        adjacencyList = vertices;
    }
}
