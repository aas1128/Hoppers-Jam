package puzzles.crossing;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;
import java.util.Iterator;

/**
 * Main class for the Crossing Puzzle
 */

public class Crossing {

    /**
     * Main method that calls all others
     * @param args - Puzzle Parameters
     */
    public static void main(String[] args) {
        int counter = 0;
        if (args.length < 2) {
            System.out.println(("Usage: java Crossing pups wolves"));
        } else {
           int startP = Integer.parseInt(args[0]);
           int startW = Integer.parseInt(args[1]);

           CrossingConfig start = new CrossingConfig(startP, startW);

           Solver solver = new Solver(start);
           Collection<Configuration> path = solver.findPath();
            if (path.size() == 0){
                System.out.println("No solution found");
            } else {
                for (Iterator<Configuration> iterator = path.iterator(); iterator.hasNext(); ) {
                    System.out.println("Step " + counter + ": " + iterator.next());
                    counter++;
                }
            }
           
        }
    }
}
