package puzzles.jam.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class Jam {
    public static void main(String[] args) {
        int counter = 0;
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        }
        else{
            File fileName = new File(args[0]);
            Configuration start = new JamConfig(args[0]);
            System.out.println("File: " + fileName);
            System.out.println(start);
            Solver solver = new Solver(start);
            Collection<Configuration> path = solver.findPath();
            System.out.println("Total Configs: " + solver.getTcounter());
            System.out.println("Unique Configs: " + solver.getPredecessors().size());
            if (path.size() == 0) {
                System.out.println("No solution found");
            } else {
                for (Iterator<Configuration> iterator = path.iterator(); iterator.hasNext(); ) {
                    System.out.println("Step " + counter + ": \n" + iterator.next());
                    counter++;
                }
            }
        }
    }
}