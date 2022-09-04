package puzzles.common.solver;

import java.util.Collection;

/**
 * Author: Aayan Sayed
 */

/**
 * Interface for the Puzzle Configs
 */
public interface Configuration {
    boolean isSolution();
    Collection<Configuration> getNeighbors();
    boolean equals(Object other);
    int hashCode();
    String toString();
}