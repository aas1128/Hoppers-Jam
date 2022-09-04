package puzzles.hoppers.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * GUI for the Hoppers Puzzle
 */
public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /**
     * The resources directory is located directly underneath the gui package
     */
    private final static String RESOURCES_DIR = "resources/";

    //images for GUI
    private Image redFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "red_frog.png"));
    private Image greenFrog = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "green_frog.png"));
    private Image lilyPad = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "lily_pad.png"));
    private Image water = new Image(getClass().getResourceAsStream(RESOURCES_DIR + "water.png"));
    //Main play Area
    GridPane playArea = new GridPane();
    //Label for game messages
    Label msg = new Label(" ");
    //model for Hopper
    HoppersModel model;
    BorderPane hoppers = new BorderPane();

    /**
     * initalize the Model for the first time
     */

    public void init() throws FileNotFoundException {

        String filename = getParameters().getRaw().get(0);
        model = new HoppersModel();
        model.load(filename);
        model.addObserver(this);
    }


    /**
     * initalize the GUI for the first time
     */
    @Override
    public void start(Stage stage) throws Exception {
        HBox actions = new HBox();
        Button reset = new Button("Reset");
        Button hint = new Button("Hint");
        Button load = new Button("Load");
        actions.getChildren().addAll(reset, hint ,load);
        reset.setOnAction(event -> {
            try {
                model.reset();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        hint.setOnAction(event -> model.hint());
        load.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File file = fileChooser.showOpenDialog(stage);
            try {
                model.load(file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });

       // actions.getChildren().addAll(guess, mess ,cheat);
      //  System.out.println(model.getCurrentConfig());
        playArea = setPlayArea(model);
        hoppers.setCenter(playArea);
        msg = new Label("Loaded: " + model.getCurrentConfig().getFileName());
        hoppers.setTop(msg);
        actions.setAlignment(Pos.CENTER);
        hoppers.setBottom(actions);


        Scene scene = new Scene(hoppers);
        stage.setScene(scene);
        stage.setTitle("Hoppers GUI");
        stage.show();
    }

    /**
     * sets the main area of buttons
     * @param model - the game state
     * @return - a gridpane of the current area
     */
    private GridPane setPlayArea(HoppersModel model) {
        //System.out.println(model.getCurrentConfig());
        String[][] board = model.getCurrentConfig().makeCopy(model.getCurrentConfig().getBoard());
        for (int i = 0; i < model.getCurrentConfig().getRow(); i++) {
            for (int j = 0; j < model.getCurrentConfig().getColumn(); j++) {

                //System.out.println(board[i][j]);
                if (board[i][j].equals(".")) {
                    Button button = new Button();
                    button.setGraphic(new ImageView(lilyPad));
                    int finalI = i;
                    int finalJ = j;
                    button.setOnAction(event -> {
                        String s = String.valueOf(finalI);
                        String l = String.valueOf(finalJ);
                        model.playGame(s, l, 1);
                    });
                    playArea.add(button, j, i);


                } else if (board[i][j].equals("G")) {
                    Button button = new Button();
                    button.setGraphic(new ImageView(greenFrog));
                    int finalI = i;
                    int finalJ = j;
                    button.setOnAction(event -> {
                        String s = String.valueOf(finalI);
                        String l = String.valueOf(finalJ);
                        model.playGame(s, l, 0);
                    });
                    playArea.add(button, j, i);
                } else if (board[i][j].equals("R")) {
                    //System.out.println("here");
                    Button button = new Button();
                    button.setGraphic(new ImageView(redFrog));
                    int finalI = i;
                    int finalJ = j;
                    button.setOnAction(event -> {
                        String s = String.valueOf(finalI);
                        String l = String.valueOf(finalJ);
                        model.playGame(s, l, 0);
                    });
                    playArea.add(button, j, i);
                } else if (board[i][j].equals("*")) {
                    Button button = new Button();
                    button.setGraphic(new ImageView(water));
                    playArea.add(button, j, i);
                }


            }
            System.out.println();


        }
        return playArea;
    }

    /**
     * updates the view to the 
     * @param hoppersModel
     * @param msg
     */
    @Override
    public void update(HoppersModel hoppersModel, String msg) {
           this.msg = new Label(msg);
        System.out.println(hoppersModel.getCurrentConfig());
           playArea = setPlayArea(hoppersModel);
           hoppers.setTop(this.msg);



    }


    /**
     * Launches the application
     * @param args - file Name
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}

