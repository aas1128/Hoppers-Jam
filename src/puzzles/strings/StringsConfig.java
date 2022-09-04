package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * Class to represent a String Configuration
 */
public class StringsConfig implements Configuration {

    //Strings to represent the starting input and temp variables for the neighbors
    private String input, Ltemp, Rtemp;
    // Collection of all the neighbors for every config
    private ArrayList<Configuration> neighbors;
    // variable that holds the end goal
    private static StringsConfig finish;
    //ints to represent the neighbors of the config
    private int left, current, right;
    // char representation of the neighbors of the config
    private char currentLetter, leftLetter, rightLetter;


    /**
     * Constructor for the Strings config
     * @param in - Starting config
     */
    public StringsConfig(String in) {
        input = in;

    }

    /**
     * returns the finish Config
     * @return - retirns the finish Config
     */
    public StringsConfig getFinish() {
        return finish;
    }

    /**
     * returns the input
     * @return - returns input
     */
    public String getInput() {
        return input;
    }

    /**
     * sets the finish config
     * @param in - reads in the value for the finish config
     */
    public void setFinish(String in) {
        finish = new StringsConfig(in);
    }


    /**
     * checks if the current config is the solution
     * @return - true if is solution
     */
    @Override
    public boolean isSolution() {
        if (this.equals(finish)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Generates Neighbors for the current configuration
     * @return - the collection of neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        neighbors = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            currentLetter = (input.charAt(i));
            //System.out.println(currentLetter);
            current = currentLetter; //converting a char to int

            if (currentLetter == 'A') {
                left = 90;
                right = 66;
            } else if (currentLetter == 'Z') {
                left = 89;
                right = 65;
            } else {
                left = current - 1;
                right = current + 1;
            }

            leftLetter = (char) left;
            rightLetter = (char) right;


            StringBuilder p = new StringBuilder(input);
            p.setCharAt(i, leftLetter);

            StringBuilder q = new StringBuilder(input);
            q.setCharAt(i, rightLetter);

            StringsConfig leftNeighbor = new StringsConfig(p.toString());
            StringsConfig rightNeighbor = new StringsConfig(q.toString());

            neighbors.add(leftNeighbor);
            neighbors.add(rightNeighbor);

        }

        return neighbors;
    }


    /**
     * Checks if 2 Strings Configs are equal
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof StringsConfig) {
            StringsConfig otherConfig = (StringsConfig) other;
            if (input.equals(otherConfig.getInput())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Generates Hashcode for variables
     * @return - hashcode
     */
    public int hashCode() {
       // System.out.println(input + " " + input.hashCode());
        return input.hashCode();
    }

    /**
     * Outputs information for the current config
     * @return - information for the current config
     */
    public String toString() {

        return input;
    }
}
