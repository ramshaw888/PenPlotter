import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * @author Aaron Ramshaw (z3461407 / aram150)
 * Pen Plotter
 * COMP2911 Assignment 2
 */
public class PenPlotter {


    /**
     *
     * @param args Input
     */
    public static void main(String args[]) {
        Scanner sc = null;
        Heuristic h = new MyHeuristic();
        initialise();
        // Read input
        try {
            sc = new Scanner(new FileReader( "input1.txt" ));
            while( sc.hasNext() ) {
                addLine(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            sc.close();
        }
        AStar(h);
        print();

    }

    /**
     * Initialises comparator and various objects.
     */

    public static void initialise() {
        lines = new ArrayList<Line>(1);

        class PathComparator implements Comparator<Path> {
            @Override
            public int compare(Path a, Path b) {
            // F values, as stored in each Path instance.
                if( a.getF() < b.getF() ) {
                    return -1;
                }

                if( a.getF() > b.getF() ) {
                    return 1;
                } else return 0;

            }
        }

        comparator = new PathComparator();
        queue = new PriorityQueue<Path>(1,comparator);

    }

    /**
     * Processes each line from input and add data.
     * @param line String of each line in input.
     */

    public static void addLine(String line) {

        if( line.startsWith("Line")) {
            String[] inputArray = line.split(" ");
            int x1 = Integer.parseInt(inputArray[2]);
            int y1 = Integer.parseInt(inputArray[3]);
            int x2 = Integer.parseInt(inputArray[5]);
            int y2 = Integer.parseInt(inputArray[6]);

            Point a = new Point(x1,y1);
            Point b = new Point(x2,y2);

            Line newLine = new Line(a,b);
            lines.add(newLine);
        }
    }

    /**
     * A* search algorithm. Takes in Heuristic h as specified.
     * @param h Specified heuristic class
     */

    public static void AStar(Heuristic h) {

        // Add origin line to the queue, with full lines (Lines to be drawn) array.
        Path init = new Path(new Point(0,0), new Point(0,0), lines);
        queue.add(init);
        int count = 0;
        Path curr = queue.peek();

        // Loop until a Path instance (curr) is found that has an empty ToGo, this means it has found the optimal path.
        while( !curr.getToGo().isEmpty() ) {
            count++;
            curr = queue.poll();

            for( Line l: curr.getToGo() ) {

                // Adding both the line A to B, and B to A to the queue.
                // A to B and B to A lines are recognised as the same as per the Line .equals method.

                Path path = curr.duplicate();
                if( path.addLine(l.getPointA(), l.getPointB(), l ) ) {
                    path.setParent(curr);
                    path.setF();
                    queue.add(path);
                }

                path = curr.duplicate();
                if( path.addLine(l.getPointB(), l.getPointA(), l ) ) {
                    path.setParent(curr);
                    path.setF();
                    queue.add(path);
                }

            }
        }
        optimalPath = curr;
        System.out.println(count+" nodes expanded");

    }

    public static void print() {

        // Takes the optimal path instance and reconciles it into the specified output.

        System.out.println("cost = "+Math.round(optimalPath.getG() * 100.0) / 100.0);

        ArrayList<Point> finalPath = new ArrayList<Point>(1);
        while( optimalPath.getParent() != null ) {
            finalPath.add(0,optimalPath.getLast().getPointB());
            finalPath.add(0,optimalPath.getLast().getPointA());
            optimalPath = optimalPath.getParent();
        }
        finalPath.add(0,new Point(0,0));

        Point last = new Point(0,0);
        boolean start = false;
        for( Point p: finalPath ) {

            if( !start ) {
                last = new Point(p.getX(),p.getY());
                start = true;
            }

            else if( lines.contains(new Line(last,p)) ){
                if( !last.equals(p) ) {
                    System.out.printf("Draw from %d %d to %d %d\n", last.getX(), last.getY(), p.getX(), p.getY());
                    last = new Point(p.getX(),p.getY());
                }
            }

            else {
                if( !last.equals(p) ) {
                    System.out.printf("Move from %d %d to %d %d\n", last.getX(), last.getY(), p.getX(), p.getY());
                    last = new Point(p.getX(),p.getY());
                }
            }

        }
    }

    private static ArrayList<Line> lines;
    private static PriorityQueue<Path> queue;
    private static Comparator<Path> comparator;
    private static Path optimalPath;
}
