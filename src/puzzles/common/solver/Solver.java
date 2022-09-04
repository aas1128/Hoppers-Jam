package puzzles.common.solver;

import puzzles.strings.StringsConfig;

import java.util.*;

/**
 * Class used to Solve Puzzles using BFS
 */

public class Solver {

    //Configs for the BFS
    private Configuration start,end;
    //Counter for the Total Amount of Configs
    int Tcounter = 0;
    HashMap<Configuration, Configuration> predecessors;

    /**
     * Contructor for the solver
     * @param in - Start Node for BFS
     * @param out - End Node for BFS
     */
    public Solver(Configuration in , Configuration out){
        start = in;
        end = out;
    }

    public int getTcounter(){
        return Tcounter;
    }

    public  HashMap<Configuration, Configuration> getPredecessors(){
        return predecessors;
    }


    /**
     * Copy Constructor that allows BFS without an End Node
     * @param in - Start Node for BFS
     */
    public Solver(Configuration in){
        start = in;
    }

    /**
     * BFS Algorithm
      * @return path -  Shortest Path
     */
    public Collection<Configuration> findPath(){
        List<Configuration> queue = new LinkedList<>();
        queue.add(start);
        predecessors = new HashMap<>();
        predecessors.put(start, start);
        while (!queue.isEmpty()) {
            Configuration current = queue.remove(0);
           //System.out.println(current);
            if (current.isSolution()) {
                //System.out.println("here");
                end = current;
                Tcounter++;
                break;
            }
            ArrayList<Configuration> nbrs = (ArrayList<Configuration>) current.getNeighbors();
//            System.out.println("numchildren " + nbrs.size());
            for (Configuration nbr : nbrs) {
                Tcounter++;
                if(!predecessors.containsKey(nbr)) {
                    predecessors.put(nbr, current);
                    queue.add(nbr);
                }
            }
        }
        List<Configuration> path = new LinkedList<>();
        if(predecessors.containsKey(end)) {
            Configuration current = end;
            while (!current.equals(start) ) {
                path.add(0, current);
                current = predecessors.get(current);
            }
            path.add(0, current);
        }



        return path;
    }
}
