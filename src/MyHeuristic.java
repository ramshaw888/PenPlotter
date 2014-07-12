/**
 * Heuristic function.
 * Analysis
 *
 * A heuristic function must give a value that underestimates the actual cost of the path as close as possible,
 * in doing so, it will always give the optimal solution while minimising the number of 'nodes' that must be
 * considered in the A star search.
 *
 * For this particular problem, the cost is the length of the path until all of the lines have been drawn,
 * from this I have deduced that a very effective and efficient heuristic function uses two parts;
 *
 * The first is the total length of the remaining lines to be drawn, which will always be admissible because
 * it will always be either equal (all pen movements are lines drawn), or less than the actual length.
 *
 * The second part is the length of the closest point to the current line end, this will always admissible
 * because it is the minimum length of the next move in the optimal path.
 *
 * The sum of the total length and distance to closest path is used in my heuristic function below.
 *
 */
public class MyHeuristic implements Heuristic {

    @Override
    public double calculateHeuristic(Path path) {

        double closestPoint = -1;
        for( Line p: path.getToGo() ) {

            if( distance(p.getPointA(),path.getLast().getPointB()) < closestPoint || closestPoint == -1) {
                closestPoint = distance(p.getPointA(),path.getLast().getPointB());
            }

            if( distance(p.getPointB(),path.getLast().getPointB()) < closestPoint || closestPoint == -1 ) {
                closestPoint = distance(p.getPointB(),path.getLast().getPointB());
            }
        }


        double length = 0.0;
        for( Line p : path.getToGo() ) {
            length+=distance(p.getPointA(), p.getPointB());
        }

        return length+closestPoint;
    }

    public double distance(Point a, Point b) {
        return Math.sqrt( (a.getX()-b.getX())*(a.getX()-b.getX()) + (a.getY()-b.getY())*(a.getY()-b.getY()) );
    }
}
