package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

public class Hoppers {

    /**
     * main method that runs the solver and prints amount of configs
     * @param args
     */
    public static void main(String[] args) {
        int row;
        int column;
        int counter = 0;
        String[][] board;
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }


        try {
            File fileName = new File(args[0]);
            Scanner myReader = new Scanner(fileName);
            String[] boardDim = myReader.nextLine().split(" ");
            row = Integer.parseInt(boardDim[0]);
            column = Integer.parseInt(boardDim[1]);

            board = new String[row][column];

            int currentRow = 0;
            while (myReader.hasNextLine()) {

                String[] data = myReader.nextLine().split(" ");
//                for(int i = 0; i < row; i++){
                for (int j = 0; j < column; j++) {
                    board[currentRow][j] = data[j];

                }
                if(row ==1 ){
                    break;
                }
                currentRow++;

//                }


            }


            HoppersConfig startConfig = new HoppersConfig(row, column, board);

            Solver solver = new Solver(startConfig);


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

        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }


    }

}
