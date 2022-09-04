package puzzles.jam.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;
import puzzles.jam.model.JamModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class JamPTUI implements Observer<JamModel, String> {
    private JamModel model;

    private void init(String in){

    }

    @Override
    public void update(JamModel jamModel, String msg) {
        System.out.println(msg);
        System.out.println(model.getCurrentConfig());
    }

    public static void main(String[] args){
        if (args.length != 1) {
            System.out.println("Usage: java JamPTUI filename");
        } else{
            JamPTUI that = new JamPTUI();
            that.model = new JamModel();
            that.model.addObserver(that);
                try{
                    that.model.load(args[0]);
                    that.model.printChoices();
                    Scanner inp = new Scanner(System.in);
                    while(inp.hasNextLine()) {
                        System.out.print("\n>  ");
                        String[] data = inp.nextLine().split(" ");
                        if (data[0].equals("q") || data[0].equals("quit")) {
                            System.exit(0);
                        }
                        if (data[0].equals("l") || data[0].equals("load")) {
                            try {
                                BufferedReader br = new BufferedReader(new FileReader(data[1]));
                                that.model.load(data[1]);
                                System.out.print("\n>  ");
                            } catch (FileNotFoundException e) {
                                System.out.println("Failed to load: " + data[1]);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Please include file name.");
                            }
                        } else if (data[0].equals("h") || data[0].equals("hint")) {
                            that.model.hint();
                        } else if (data[0].equals("r") || data[0].equals("reset")) {
                            that.model.reset();
                        } else if (data[0].equals("s")) {
                            that.model.select(Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File does not exist");
                    System.exit(0);
                }
        }
    }
}
