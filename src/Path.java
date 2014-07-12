import java.util.ArrayList;

/**
 * Path class,
 * Stores list of lines to be drawn, the last line drawn, f and g values, and parent path.
 */
public class Path extends Object {

    /**
     * Initialise blank path instance.
     */
    public Path() {
        this.toGo = new ArrayList<Line>(1);
        this.g = 0.0;
        this.f = 0.0;
        this.last = new Line(new Point(0,0),new Point(0,0));
        this.parent = null;
    }

    /**
     * Initialises new Path instance, with specified start line (includes line from origin), and lines "toGo" array.
     * @param a Start point of line.
     * @param b End point of line.
     * @param lines toGo array (List of lines still to be drawn).
     */

    public Path(Point a, Point b, ArrayList<Line> lines) {
        this.toGo = new ArrayList<Line>(1);
        this.g = 0.0;
        g+= distance(new Point(0,0),a);
        g+= distance(a,b);
        toGo = new ArrayList<Line>(1);
        toGo.addAll(lines);
        this.last = new Line(a,b);
        toGo.remove(this.last);
        this.parent = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (!last.equals(path.last)) return false;
        if (!toGo.equals(path.toGo)) return false;

        return true;
    }



    @Override
    public int hashCode() {

        Path curr = parent;
        int result = 0;
        while( curr != null ) {
            result = 31 * result + last.hashCode();
            curr = curr.getParent();
        }

        return result;

    }

    /**
     * Adds a new line to the path.
     * @param a Line start point.
     * @param b Line end point.
     * @param l The line from a to b (to remove from the toGo list).
     * @return True if line has been added, false if line already exists.
     */
    public boolean addLine(Point a, Point b, Line l) {

        if( !last.getPointB().equals(b) ) {

            g+= distance(a,b);
            g+= distance(last.getPointB(), a);
            if( l != null ) {
                last = new Line( a,b );
                toGo.remove(l);
            }
            return true;
        }

        else return false;
    }

    /**
     * Sets the F value (g+h). Calls the heuristic class.
     */

    public void setF() {
        MyHeuristic h = new MyHeuristic();
        f = g+h.calculateHeuristic(this);
    }

    /**
     *
     * @return A duplicate of this instance.
     */

    public Path duplicate() {
        Path dupe = new Path();
        dupe.setToGo(toGo);
        dupe.setG(g);
        dupe.setLast(last);
        return dupe;
    }

    /**
     * Distance from a to b
     * @param a
     * @param b
     * @return double value of the distance.
     */

    public double distance(Point a, Point b) {
        return Math.sqrt( (a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()) );
    }

    public double getG() {
        return g;
    }

    public ArrayList<Line> getToGo() {
        return toGo;
    }

    public Line getLast() {
        return last;
    }

    public Path getParent() {
        return this.parent;
    }

    public void setParent(Path p) {
        this.parent = p;
    }

    public void setToGo(ArrayList<Line> toGo) {
        this.toGo = new ArrayList<Line>(1);
        this.toGo.addAll(toGo);
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setLast(Line last) {
        this.last = last;
    }

    public double getF() {
        return f;
    }

    private ArrayList<Line> toGo;
    private Line last;
    private double g;
    private double f;
    private Path parent;
}
