package puzzles.strings;

import jdk.swing.interop.SwingInterOpUtils;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;
import java.util.Iterator;


/**
 * Main class for the Strings Puzzle
 */
public class Strings  {

    /**
     * Main method that runs the Strings Puzzle
     * @param args
     */
    public static void main(String[] args) {
        int counter = 0;
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            String start = args[0];
            String end = args[1];
            System.out.println("Start: " + start + " , End: " + end);


            StringsConfig startConf = new StringsConfig(start);
            startConf.setFinish(end);



            Solver solver = new Solver(startConf, startConf.getFinish() );

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



