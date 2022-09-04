package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * model of the hoppers puzzle
 */
public class HoppersModel {
    private int hintCounter = 0;
    /**
     * the collection of observers of this model
     */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /**
     * the current configuration
     */
    private HoppersConfig currentConfig;

    private String fileName;
    private HoppersConfig firstConfig;
    private String startx = null;
    private String starty = null;

    private String endx = null;
    private String endy = null;
    private boolean anotherTurn = true;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    public void load(String fileName) throws FileNotFoundException {
        String msg = "Loaded: " + fileName;
        currentConfig = new HoppersConfig(fileName);
        this.fileName = fileName;
        firstConfig = currentConfig;
        hintCounter = 0;
        alertObservers(msg);

    }

    /**
     * return the current config
     * @return
     */
    public HoppersConfig getCurrentConfig() {
        return currentConfig;
    }

    /**
     * hint functionality of the game
     */
    public void hint() {


        Solver solver = new Solver(currentConfig);
        List<Configuration> path = (List<Configuration>) solver.findPath();

        String msg = "Next Step!";
        //  System.out.println("Hint: " + hintCounter);
        if (path.size() == 1) {
            msg = "Already Solved!";
            currentConfig = (HoppersConfig) path.get(0);
            alertObservers(msg);
        } else if(path.size() == 0){
            msg = "NO solution";
            alertObservers(msg);
        } else  {
            currentConfig = (HoppersConfig) path.get(1);
            alertObservers(msg);
            }

        }




    /**
     * reset functionality of the game
     */
    public void reset() throws FileNotFoundException {
        Solver solver = new Solver(currentConfig);
        List<Configuration> path = (List<Configuration>) solver.findPath();
        this.load(fileName);
        String msg = "Puzzle reset!";
        alertObservers(msg);


    }

    /**
     * checks if the move is allowed
     * @param start1 - start x
     * @param start2 - start y
     * @param end1 - end x
     * @param end2 - end y
     * @return - true or false
     */
    public boolean possibleMove(String start1, String start2, String end1, String end2) {
        String[][] board = currentConfig.getBoard();


        int startx = Integer.parseInt(start1);
        int starty = Integer.parseInt(start2);
        int endx = Integer.parseInt(end1);
        int endy = Integer.parseInt(end2);


        //System.out.println(startx +  " " + starty + " " + endx + " " + endy);

        if(board[startx][starty].equals("G") || board[startx][starty].equals("R")){
            if(board[endx][endy].equals("G")){
                return false;
            }
        }

        try {
            if (board[startx][starty + 2].equals("G") && board[startx][starty + 4].equals(".") && (starty + 4 == endy)) {//right
                return true;
            } else if (board[startx][starty - 2].equals("G") && board[startx][starty - 4].equals(".") && (starty - 4 == endy)) {//left
                return true;
            } else if (board[startx + 2][starty].equals("G") && board[startx + 4][starty].equals(".") && (startx + 4 == endx)) {//down
                return true;
            } else if (board[startx - 2][starty].equals("G") && board[startx - 4][starty].equals(".") && (startx - 4 == endx)) { //up
                return true;
            } else if (board[startx + 1][starty + 1].equals("G") && board[startx + 2][starty + 2].equals(".") && (startx + 2 == endx) && (starty + 2 == endy)) { //bottom right
                return true;
            } else if (board[startx + 1][starty - 1].equals("G") && board[startx + 2][starty - 2].equals(".") && (startx + 2 == endx) && (starty - 2 == endy)) { // bottom left
                return true;
            } else if (board[startx - 1][starty + 1].equals("G") && board[startx - 2][starty + 2].equals(".") && (startx - 2 == endx) && (starty + 2 == endy)) { // top right
                System.out.println("In top right");
                return true;
            } else if (board[startx - 1][starty - 1].equals("G") && board[startx - 2][starty - 2].equals(".") && (startx - 2 == endx) && (starty - 2 == endy)) { // top ;eft
                return true;
            } else {
                return false;
            }
        } catch (IndexOutOfBoundsException e) {
            return true;
        }

    }

    /**
     * actually changing the current config to show the move the user just made
     * @param start1 -start x
     * @param start2 - start y
     * @param end1 - end x
     * @param end2 -end y
     */
    public void makeSelection(String start1, String start2, String end1, String end2) {


        String[][] board = currentConfig.makeCopy(currentConfig.getBoard());

        int startx = Integer.parseInt(start1);
        int starty = Integer.parseInt(start2);

        int endx = Integer.parseInt(end1);
        int endy = Integer.parseInt(end2);

        if (starty + 4 == endy) {
            board[startx][starty + 2] = ".";
            board[startx][starty + 4] = board[startx][starty];
            board[startx][starty] = ".";

        } else if ((startx - 4 == endx)) {
            board[startx][starty - 2] = ".";
            board[startx][starty - 4] = board[startx][starty];
            board[startx][starty] = ".";
        } else if ((startx + 4 == endx)) {
            board[startx + 2][starty] = ".";
            board[startx + 4][starty] = board[startx][starty];
            board[startx][starty] = ".";
        } else if ((startx - 4 == endx)) {
            board[startx - 2][starty] = ".";
            board[startx - 4][starty] = board[startx][starty];
            board[startx][starty] = ".";
        } else if ((startx + 2 == endx) && (starty + 2 == endy)) { // top right

            board[startx + 1][starty + 1] = ".";
            board[startx + 2][starty + 2] = board[startx][starty];
            board[startx][starty] = ".";
        } else if ((startx + 2 == endx) && (starty - 2 == endy)) { // top left
            board[startx + 1][starty + 1] = ".";
            board[startx - 2][starty - 2] = board[startx][starty];
            board[startx][starty] = ".";
        } else if ((startx - 2 == endx) && (starty + 2 == endy)) { // bottom right

            board[startx - 1][starty + 1] = ".";
            board[startx - 2][starty + 2] = board[startx][starty];
            board[startx][starty] = ".";
        } else if ((startx - 2 == endx) && (starty - 2 == endy)) { // bottom right
            board[startx - 1][starty - 1] = ".";
            board[startx - 2][starty - 2] = board[startx][starty];
            board[startx][starty] = ".";
        }

        HoppersConfig selectionConfig = new HoppersConfig(currentConfig.getRow(), currentConfig.getColumn(), board);
        currentConfig = selectionConfig;
        String msg = " ";

        msg = "Jumped from (" + start1 + ", " + start2 + ") to " + "(" + end1 + ", " + end2 + ")";


        alertObservers(msg);


    }

    /**
     * interacts with all the class to make a selection
     * @param i - x chosen
     * @param j - y chosen
     * @param selection - which piece they choose
     */
    public void playGame(String i, String j, int selection) { //we know which piece they choose
        String board[][] = currentConfig.getBoard();
        System.out.println(board[Integer.parseInt(i)][Integer.parseInt(j)]);
        System.out.println("Selection: " + selection);

        if (currentConfig.contains(i, j, selection) && anotherTurn) {

            startx = i;
            starty = j;
            anotherTurn = !anotherTurn;
        }  else if (currentConfig.contains(i, j, selection) && anotherTurn == false){
            endx = i;
            endy = j;
            anotherTurn = !anotherTurn;
        } else if (anotherTurn == false) {
            String msg = ("Can't jump from (" + startx + ", " + starty + ") to " + "(" + endx + ", " + endy + ")");
            alertObservers(msg);
        } else if (anotherTurn == true) {
            String msg = ("No Frog at (" + startx + ", " + starty + ")");
            alertObservers(msg);
        }


        if (startx != null && starty != null && endx != null && endy != null) {
             //System.out.println("all data collected");
             System.out.println(startx + " " + starty + " " + endx + " " + endy );
            if (this.possibleMove(startx, starty, endx, endy)) {
                //System.out.println("In possible move here");
                this.makeSelection(startx, starty, endx, endy);
                startx = null;
                starty = null;
                endx = null;
                endy = null;
            } else {
               // System.out.println("here");
                String msg = ("Can't jump from (" + startx + ", " + starty + ") to " + "(" + endx + ", " + endy + ")");
                alertObservers(msg);
            }
        }

    }

    /**
     * help message
     */
    public void printChoices() {
        System.out.println("h(int)              -- hint next move");
        System.out.println("l(oad) filename     -- load new puzzle file");
        System.out.println("s(elect) r c        -- select cell at r, c");
        System.out.println("q(uit)              -- quit the game");
        System.out.println("r(eset)             -- reset the current game");
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }
}
