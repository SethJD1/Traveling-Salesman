/**
 * A vertex in a graph. Captures the vertex coordinates and the discovery values
 * for the vertex.
 * @author Seth Dovgan
 * @version 18FEB18
 */
public class Vertex implements Comparable<Vertex>{

    private int identifier;
    private Point point;
    private Enum color;
    private Vertex predecessor;
    private int key;

    /**
     * Constructor. Sets the values for the vertex identifier, coordinates and
     * default key value (0, -INF, INF).
     * @param identifier for the vertex.
     * @param point of the vertex location.
     */
    public Vertex(int identifier, Point point) {

        this.identifier = identifier;
        this.point = point;
        this.predecessor = null;
        this.color = Color.WHITE;
        this.key = 0;
    }

    /**
     * Returns the vertex's identifier.
     * @return vertex identifier
     */
    public int getIdentifier() {
        return identifier;
    }

    /**
     * Returns the vertex's x coordinate.
     * @return vertex's coordinate as a point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Returns whether the vertex as been visited in a traversal or not
     * @return white if not visited, grey if not complete and black if visited
     */
    public Enum getColor() {
        return color;
    }

    /**
     * Sets whether the vertex has been visited in a traversal or not.
     * @param color traversal value.
     */
    public void setColor(Enum color) {
        this.color = color;
    }

    /**
     * Returns the current predecessor for the vertex.
     * @return vertex's predecessor.
     */
    public Vertex getPredecessor() {
        return predecessor;
    }

    /**
     * Updates the vertex's predecessor in a search based on the parameter value.
     * @param predecessor of the vertex.
     */
    public void setPredecessor(Vertex predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * Returns the current key value for vertex, which is set during traversals
     * to capture any given value such as distance.
     * @return current key value.
     */
    public int getKey() {
        return key;
    }

    /**
     * Sets the vertex's key value to the parameter.
     * @param key value to set.
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Resets all the vertex's discovery values to their default and sets the
     * key value to the parameter.
     * @param keyValue to set for the vertex.
     */
    public void clearDiscoveryValues(int keyValue){

        this.predecessor = null;
        this.color = Color.WHITE;
        this.key = keyValue;
    }

    /**
     * Returns a string representation of the vertex including its identifier
     * and coordinates.
     * @return string representation of the vertex.
     */
    @Override
    public String toString(){
        return identifier + point.toString();
    }

    /**
     * Compares two vertices based on their key value. The comparison is setup
     * to find the minimum vertex based on the key value.
     * @param vertex to compare
     * @return 1 if greater, 0 if equal and -1 if less than the parameter
     */
    @Override
    public int compareTo(Vertex vertex){

        if(key > vertex.getKey()){
            return 1;
        } else if (key < vertex.getKey()){
            return -1;
        } else {
            return 0;
        }
    }
}
