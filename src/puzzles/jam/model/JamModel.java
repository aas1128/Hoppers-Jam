package puzzles.jam.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class JamModel {
    /** the collection of observers of this model */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private JamConfig currentConfig;

    private int hintCounter;
    private String fileName;
    private JamConfig start;
    private List path;
    private boolean secondSelect = false;
    private String selection;
    private int[] prevSelection;


    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, String> observer) {
        this.observers.add(observer);
    }

    public void load(String fileName) throws FileNotFoundException {
        String msg = "Loaded: " + fileName;
        this.fileName = fileName;
        currentConfig = new JamConfig(fileName);
        this.fileName = fileName;
        start = currentConfig;
        hintCounter =0;
        alertObservers(msg);
        Solver solver = new Solver(currentConfig);
        this.path = (List) solver.findPath();
    }

    public void select(int r, int c){
        String msg;
        if(!secondSelect) {
            String current = currentConfig.getElement(r, c);
            if (current.equals(".")) {
                msg = "No car at (" + r +", " + c + ")";
                alertObservers(msg);
            } else {
                selection = current;
                secondSelect = true;
                prevSelection = new int[]{r, c};
                msg = "Selected (" + r +", " + c + ")";
                alertObservers(msg);
            }
        } else{
            if(currentConfig.getCarRot(selection).equals("V")){
                if(c == currentConfig.getCarPos(selection)[1]){
                    if(prevSelection[0] - r > 0){
                        if(prevSelection[0] - r <= currentConfig.canMoveUp(selection)){
                            //valid move
                            currentConfig.moveCar(selection, r - prevSelection[0], 0);
                            msg = "Moved from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                            alertObservers(msg);
                        } else{
                            msg = "Can't move from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                            alertObservers(msg);
                        }
                        secondSelect = false;
                    } else if(prevSelection[0] - r < 0){
                        if(r - prevSelection[0] <= currentConfig.canMoveDown(selection)){
                            //valid move
                            currentConfig.moveCar(selection, r - prevSelection[0], 0);
                            msg = "Moved from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                            alertObservers(msg);
                        } else{
                            msg = "Can't move from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                            alertObservers(msg);                        }
                        secondSelect = false;
                    } else {
                        msg = "Can't move from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                        alertObservers(msg);
                        secondSelect = false;
                    }
                } else{
                    msg = "Can't move from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                    alertObservers(msg);
                    secondSelect = false;
                }
            } else{
                if(r == currentConfig.getCarPos(selection)[0]){
                    if(prevSelection[1] - c > 0){
                        if(prevSelection[1] - c <= currentConfig.canMoveLeft(selection)){
                            //valid move
                            currentConfig.moveCar(selection, 0, c - prevSelection[1]);
                            msg = "Moved from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                            alertObservers(msg);
                        } else {
                            System.out.println("Invalid move, ending selection...");
                        }
                        secondSelect = false;
                    } else if(prevSelection[1] - c < 0){
                        if(c - prevSelection[1] <= currentConfig.canMoveRight(selection)){
                            //valid move
                            currentConfig.moveCar(selection, 0, c - prevSelection[1]);
                            msg = "Moved from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                            alertObservers(msg);
                        } else{
                            msg = "Can't move from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                            alertObservers(msg);                        }
                        secondSelect = false;
                    } else{
                        msg = "Can't move from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                        alertObservers(msg);
                        secondSelect = false;
                    }
                } else{
                    msg = "Can't move from (" + prevSelection[0] + ", " + prevSelection[1] + ") to (" + r + ", " + c + ")";
                    alertObservers(msg);
                    secondSelect = false;
                }
            }
        }
    }

    public void reset() throws FileNotFoundException {
        this.load(this.fileName);
    }

    public void hint(){
        String msg = "Next Step!";
        if (path.size() == 1) {
            msg = "Already Solved!";
            currentConfig = (JamConfig) path.get(0);
            alertObservers(msg);
        } else if(path.size() == 0){
            msg = "No solution";
            alertObservers(msg);
        } else  {
            currentConfig = (JamConfig) path.get(1);
            alertObservers(msg);
        }
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

    public void printChoices() {
        System.out.println("h(int)              -- hint next move");
        System.out.println("l(oad) filename     -- load new puzzle file");
        System.out.println("s(elect) r c        -- select cell at r, c");
        System.out.println("q(uit)              -- quit the game");
        System.out.println("r(eset)             -- reset the current game");
    }

    public Configuration getCurrentConfig(){
        return currentConfig;
    }
}
