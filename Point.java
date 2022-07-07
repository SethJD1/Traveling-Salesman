/**
 * A point with x and y coordinates.
 * @author Seth Dovgan
 * @version 18FEB18
 */
public class Point {

    private int x;
    private int y;

    /**
     * Constructor. Assigns the point a location with x and y coordinates.
     * @param x coordinate to assign to the point.
     * @param y coordinate to assign to the point.
     */
    public Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate for the point.
     * @return x coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate for the point.
     * @return y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the edge weight based on the coordinates for each of the vertices.
     * @return edge weight
     */
    public long distance(Point point) {

        long x = (long) Math.pow(this.x - point.getX(), 2);
        long y = (long) Math.pow(this.y - point.getY(), 2);

        return Math.round(Math.sqrt(x + y));
    }

    /**
     * Returns a string representation of the point.
     * @return string representation of a point.
     */
    @Override
    public String toString() {
        return "("
                + x + ", "
                + y +
                ')';
    }
}
