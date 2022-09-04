package puzzles.hoppers.model;

import puzzles.common.solver.Configuration;
import puzzles.hoppers.solver.Hoppers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// TODO: implement your HoppersConfig for the common solver

/**
 * Class thats holds the game config
 */
public class HoppersConfig implements Configuration {

    //total rows
    private int row;
    //total columns
    private int column;
    //the actual board
    private String[][] board;
    //List of all the neighbors of the current config
    private ArrayList<Configuration> neighbors;
    //file name of the the current config
    private String fileName;


    /**
     * Constructor for the hoppers config
     * @param row - number of rows
     * @param column - number of columns
     * @param board - board
     */
    public HoppersConfig(int row, int column, String[][] board) {
        this.row = row;
        this.column = column;
        this.board = board;
    }


    /**
     * Copy Constructor which takes in a file name
     * @param file - filename
     * @throws FileNotFoundException
     */
    public HoppersConfig(String file) throws FileNotFoundException {
            this.fileName = file;
            File fileName = new File(file);
            Scanner myReader = new Scanner(fileName);
            String[] boardDim = myReader.nextLine().split(" ");
            row = Integer.parseInt(boardDim[0]);
            column = Integer.parseInt(boardDim[1]);
            board = new String[row][column];
            int currentRow = 0;
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(" ");
//
                for (int j = 0; j < column; j++) {
                    board[currentRow][j] = data[j];

                }
                if (row == 1) {
                    break;
                }
                currentRow++;

//                }


            }


        /**
         * getters for hoppers config
          */
    }
    public String getFileName(){
        return fileName;
    }

    public int getRow(){
        return row;

    }

    public int getColumn(){
        return column;
    }
    public String[][] getBoard(){
            return board;
    }


    /**
     * checks if 2 configs are equal to one another
     * @param other - other config
     * @return - true or false
     */
    public boolean equals(Object other) {
        if (other instanceof HoppersConfig) {
            HoppersConfig otherConfig = (HoppersConfig) other;
            for(int k = 0; k < row; k++){
                for(int l = 0; l < column; l++){
                   if(!this.board[k][l].equals(otherConfig.board[k][l])){
                       return false;
                   }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * checks to see if the current config is the actual solution to the puzzle
     * @return - true or false
     */
    @Override
    public boolean isSolution() {
        int counter = 0;
        for(int i = 0 ; i < row; i++){
            for(int j = 0; j < column; j++){
                if(board[i][j].equals("G")){
                    return false;
                } else if(board[i][j].equals("R")){
                    counter++;
                }
            }
        }

        if(counter == 1){
            return true;
        } else {
            return false;
        }

    }

    /**
     * checks to see if a coordinate exists on the
     * @param one - x val
     * @param two - y val
     * @param selection -whether its the first or second choice
     * @return - true or false
     */
    public boolean contains(String one, String two, int selection){
        int modelRow = Integer.parseInt(one);
        int modelColumn = Integer.parseInt(two);

        boolean bRow = false;
        boolean bColumn = false;
        boolean bSelection = false;

        if( modelRow >= 0 && modelRow < row){
            bRow = true;

        }

        if (modelColumn >= 0 && modelColumn < column){
            bColumn = true;
        }


        if(selection == 0){
            if(bRow && bColumn && (board[modelRow][modelColumn].equals("G") || board[modelRow][modelColumn].equals("R") )){
                return true;
            } else {
                return false;
            }

        } else {

            if(bRow && bColumn && (board[modelRow][modelColumn].equals("."))){
                return true;
            } else {
                return false;
            }
        }


    }

    /**
     * gets all the neighbors for the current config
     * @return - collection of all the neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {

        neighbors = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (board[i][j].equals("G") || board[i][j].equals("R")) { //checks for frog

                    //
                    if(j + 4 < column && board[i][j + 2].equals("G") && board[i][j + 4].equals(".")){ // right config // declare static variables
                       String[][] copyBoard = makeCopy(board);
                       copyBoard[i][j] = ".";  // G
                       copyBoard[i][j+2] = ".";
                       copyBoard[i][j+4] = new String (board[i][j]);
                       HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                       neighbors.add(config);

                    }

                    if(j - 4  >= 0 && board[i][j - 2].equals("G") && board[i][j - 4].equals(".")){ // left config
                        String[][] copyBoard = makeCopy(board);
                        copyBoard[i][j] = ".";
                        copyBoard[i][j-2] = ".";
                        copyBoard[i][j-4] = new String (board[i][j]);
                        HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                        neighbors.add(config);

                    }

                    if(i + 4  < row && board[i + 2][j].equals("G") && board[i  + 4 ][j].equals(".")){ // down config
                        String[][] copyBoard = makeCopy(board);
                        copyBoard[i][j] = ".";
                        copyBoard[i + 2][j] = ".";
                        copyBoard[i + 4][j] =new String (board[i][j]);
                        HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                        neighbors.add(config);

                    }

                    if(i - 4  >= 0 && board[i - 2][j].equals("G") && board[i  - 4 ][j].equals(".")){ // up config
                        String[][] copyBoard = makeCopy(board);
                        copyBoard[i][j] = ".";
                        copyBoard[i - 2][j] = ".";
                        copyBoard[i - 4][j] = new String (board[i][j]);
                        HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                        neighbors.add(config);
                    }


                    if(i - 2 >= 0 && j + 2 < column && board[i-1][j+1].equals("G") && board[i-2][j+2].equals(".") ){ // top right config
                        String[][] copyBoard = makeCopy(board);
                        copyBoard[i][j] = ".";
                        copyBoard[i-1][j+1] = ".";
                        copyBoard[i-2][j+2] = new String (board[i][j]);
                        HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                        neighbors.add(config);

                    }

                    if(i - 2 >= 0 && j - 2 >= 0 && board[i-1][j-1].equals("G") && board[i-2][j-2].equals(".") ){ // top left config
                        String[][] copyBoard = makeCopy(board);
                        copyBoard[i][j] = ".";
                        copyBoard[i-1][j-1] = ".";
                        copyBoard[i-2][j-2] = new String (board[i][j]);
                        HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                        neighbors.add(config);

                    }

                    if(i + 2 < row && j + 2 < column && board[i+1][j+1].equals("G") && board[i+2][j+2].equals(".") ){ // bottom right config
                        String[][] copyBoard = makeCopy(board);
                        copyBoard[i][j] = ".";
                        copyBoard[i+1][j+1] = ".";
                        copyBoard[i+2][j+2] = new String (board[i][j]);
                        HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                        neighbors.add(config);

                    }

                    if(i + 2 < row && j - 2 >= 0  && board[i+1][j-1].equals("G") && board[i+2][j-2].equals(".") ){ // bottom left config
                        String[][] copyBoard = makeCopy(board);
                        copyBoard[i][j] = ".";
                        copyBoard[i+1][j-1] = ".";
                        copyBoard[i+2][j-2] = new String (board[i][j]);
                        HoppersConfig config = new HoppersConfig(row, column, copyBoard);
                        neighbors.add(config);

                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * makes a copty of the 2d including the pointers
     * @param board - board too be copied
     * @return - copyBoard
     */
    public String[][] makeCopy(String[][] board){
        String[][] copyBoard = new String[row][column];

        for(int k = 0; k <row; k++){
            for(int l = 0; l <column; l++){
                copyBoard[k][l] = board[k][l];
            }
        }
        return copyBoard;
    }

    /**
     * return the hashcode of the 2d array
     * @return - hashcode
     */
    @Override
    public int hashCode() {
//        int result = Objects.hash(row, column, neighbors);
        return Arrays.deepHashCode(board);
    }

    /**
     * prints out the current config
     * @return
     */
    public String toString() {
        int count = 0;
        StringBuilder output = new StringBuilder();
        output.append("   ");
        for (int i = 0; i < column; i++) {
            output.append(i).append(" ");
        }
        output.append("\n");
        output.append("  ");
        output.append("--".repeat(Math.max(0, column)));
        output.append("\n");
        for (String[] s : board) {
            output.append(count).append("| ");
            count++;
            for (String str : s) {
                output.append(str);
                output.append(" ");
            }
            output.append("\n");
        }
        return output.toString();
    }

}


