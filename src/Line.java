/**
 * Line class from A to B
 */

public class Line extends Object {

    /**
     * Creates a new line with specified points a and b.
     * @param a
     * @param b
     */
    public Line(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public Point getPointA() {
        return a;
    }

    public Point getPointB() {
        return b;
    }

    /**
     * Considers a line A to B and B to A as being equal.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if( line == null ) return false;

        if( line.getPointA().equals(a) && line.getPointB().equals(b) ) return true;
        if( line.getPointA().equals(b) && line.getPointB().equals(a) ) return true;

        return false;

    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    private Point a;
    private Point b;

}
