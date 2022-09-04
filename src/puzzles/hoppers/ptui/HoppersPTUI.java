package puzzles.hoppers.ptui;

import ptui.ConsoleApplication;
import puzzles.common.Observer;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersModel;


import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Hoppers PTUI class
 */

public class HoppersPTUI implements Observer<HoppersModel, String>  {
    private HoppersModel model;

    /**
     * Main function and event handler
     * @param fileName - fileName
     */
    public void init(String fileName)  {
        this.model = new HoppersModel();
        this.model.addObserver(this);


            try {
               // System.out.println("Loaded: " + in);
                model.load(fileName);

                model.printChoices();
                Scanner inp = new Scanner(System.in);
               // System.out.print("\n>  ");
                String input = " ";
                while(true){
                   //
                    System.out.print("\n>  ");
                    String[] data = inp.nextLine().split(" ");
                    //System.out.println("here");

                    //System.out.println(data[0]);
                    if(data[0].equals("q") || data[0].equals("quit")){
                        System.out.println("Thanks for playing!");
                        System.exit(0);

                    } else if(data[0].equals("l") || data[0].equals("load")){
                        try{
                            BufferedReader br = new BufferedReader(new FileReader(data[1]));
                           // System.out.println(br.readLine());
                            model.load(data[1]);
                            System.out.print("\n>  ");
                        } catch (FileNotFoundException e){
                            System.out.println("Failed to load: " + data[1]);
                        } catch (IndexOutOfBoundsException e){
                            System.out.println("Please include file name.");
                        }
                    } else if(data[0].equals("h") || data[0].equals("hint")){
                        model.hint();
                    } else if (data[0].equals("r") || data[0].equals("reset")){
                        model.reset();
                    } else if (data[0].equals("s")){
                       // System.out.println("in select");

                       String start1 = data[1];
                       String start2 = data[2];
                       if(model.getCurrentConfig().contains(start1,start2, 0)){
                           System.out.println("Selected (" + start1 + ", " + start2 + ")");

                       } else {
                           System.out.println("No Frog at (" + start1 + ", " + start2 + ")");
                           System.out.println(model.getCurrentConfig());
                           continue;
                       }

                       System.out.println(model.getCurrentConfig());
                       System.out.print("\n>  ");
                       String[] data2 = inp.nextLine().split(" ");
                       String end1 = data2[1];
                       String end2 = data2[2];
                        //System.out.println(end1 + " " + end2);
                       // System.out.println(end1 );// contains works for first point but not second
                        if(model.getCurrentConfig().contains(end1,end2, 1) ){
                            //System.out.println("Contains");
                            if(model.possibleMove(start1,start2,end1,end2)){
                               // System.out.println("Is possible move");
                                model.makeSelection(start1,start2,end1,end2);
                            } else {

                                System.out.println("Can't jump from (" + start1 + ", " + start2 + ") to " + "(" + end1 + ", " + end2 + ")");
                                System.out.println(model.getCurrentConfig());
                                continue;
                            }
                            //System.out.println("Jumped from (" + start1 + ", " + start2 + ") to " + "(" + end1 + ", " + end2 + ")"  );


                        } else {

                            System.out.println("Can't jump from (" + start1 + ", " + start2 + ") to " + "(" + end1 + ", " + end2 + ")");
                            System.out.println(model.getCurrentConfig());
                            continue;
                        }





                    } else {
                        System.out.println("Please choose valid option:");
                        model.printChoices();
                        continue;
                    }
                        if(!inp.hasNextLine()){
                            break;
                        }

                }


            } catch (FileNotFoundException e) {
                System.out.println("Sorry, the file you entered didnt exist");
                System.exit(0);
            }


    }


    /**
     * updates the view of the PTUI
     * @param model - model of the hoppers config
     * @param msg - game state message
     */
    @Override
    public void update(HoppersModel model, String msg) {
        System.out.println(msg);
        System.out.println(model.getCurrentConfig());

    }

    /**
     * main method that calls init
     * @param args - file name
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
           HoppersPTUI ptui = new HoppersPTUI();
           ptui.init(args[0]);
        }
    }
}
