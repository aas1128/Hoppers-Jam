package puzzles.crossing;

import jdk.swing.interop.SwingInterOpUtils;
import puzzles.common.solver.Configuration;
import puzzles.strings.StringsConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;


/**
 * Configuration for the Crossing Puzzle
 */
public class CrossingConfig implements Configuration {
    private int leftPup;
    private int leftWolf;
    private int rightPup;
    private int rightWolf;
    private boolean boatState = true;

    // Collection of all the neighbors for every config
    private Collection<Configuration> neighbors;

    /**
     * Constructor for the Crossing COnfig
     * @param p - number of pups on the left side
     * @param w - number of wolves on the left side
     */
    public CrossingConfig(int p, int w){
        leftPup = p;
        leftWolf = w;
    }

    /**
     * Copy Contructor that is used to generate neighbors
     * @param other - previous Crossing Config
     * @param p - how the number of pups is changing
     * @param w - how the number of Wolves if changing
     */
    private CrossingConfig(CrossingConfig other, int p, int w){
        this.boatState = !other.boatState;

        if (other.boatState) {
            rightPup = other.rightPup + p;
            leftPup = other.leftPup - p;
            rightWolf = other.rightWolf + w;
            leftWolf = other.leftWolf - w;
        } else {
            leftPup = other.leftPup + p;
            rightPup = other.rightPup - p;
            leftWolf = other.leftWolf + w;
            rightWolf = other.rightWolf - w;
        }
    }

    @Override

    /**
     * Checks if 2 Crossing Configs are equal
     */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrossingConfig that = (CrossingConfig) o;
        return leftPup == that.leftPup && leftWolf == that.leftWolf && boatState == that.boatState;
    }

    /**
     * Generates Hashcode for variables
     * @return - hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(leftPup, leftWolf, boatState);
    }

    /**
     * Checks if the current config is the solution
     * @return is the solution is true
     */
    @Override
    public boolean isSolution() {
        if (leftWolf == 0 && leftPup == 0) {
            return true;
        }
        return false;
    }

    /**
     * Generates Neighbors for the current configuration
     * @return - collection of all the neighbors
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        neighbors = new ArrayList<>();

        //check if
        CrossingConfig moveWolf = new CrossingConfig(this,  0, 1);
        CrossingConfig moveMaxPup = new CrossingConfig(this, 2,  0 );
        CrossingConfig moveMinPup = new CrossingConfig(this, 1,  0);

        if (this.boatState && leftWolf >= 1 || !boatState && rightWolf >= 1)
            neighbors.add(moveWolf);

        if (this.boatState && leftPup>= 2 || !boatState && rightPup >= 2)
            neighbors.add(moveMaxPup);

        if (this.boatState && leftPup>= 1 || !boatState && rightPup >= 1)
            neighbors.add(moveMinPup);

        return  neighbors ;
    }

    /**
     * Outputs information for the current config
     * @return - information for the current config
     */
    public String toString() {
        String fin = "";
        if(boatState){
            fin = "(BOAT) left=[" + leftPup + ", " + leftWolf + "], right=[" + rightPup + ", " + rightWolf + "]";
        } else {
            fin = "\t  left=[" + leftPup + ", " + leftWolf + "], right=[" + rightPup + ", " + rightWolf + "] (BOAT)";
        } return fin;
    }
}
