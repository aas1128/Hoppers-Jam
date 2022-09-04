package puzzles.jam.model;

import puzzles.common.solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class JamConfig implements Configuration {
    private HashMap<String, int[]> cars;
    private HashMap<String, String> carProp;
    private final String VERT = "V";
    private final String HORZ = "H";
    private String[][] board;
    private int carNum = 0;
    public JamConfig(String file){
        String[] dims;
        String[] rawCar;
        int[] carPosTemp = new int[4];
        try {
            File fileName = new File(file);
            cars = new HashMap<>();
            Scanner myReader = new Scanner(fileName);
            dims = myReader.nextLine().split(" ");
            board = new String[Integer.parseInt(dims[0])][Integer.parseInt(dims[1])];
            carNum = Integer.parseInt(myReader.nextLine());
            for (int i = 0; i < carNum; i++) {
                rawCar = myReader.nextLine().split(" ");
                for (int j = 1; j < 5; j++) {
                    carPosTemp[j - 1] = Integer.parseInt(rawCar[j]);
                }
                cars.put(rawCar[0], new int[]{carPosTemp[0], carPosTemp[1], carPosTemp[2], carPosTemp[3]});
            }
            this.boardBuilder();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
        }
    }

    public JamConfig(JamConfig that, String carName, int[] carPos){
        this.board = new String[that.board.length][that.board[0].length];
        this.carNum = that.carNum;

        this.cars = new HashMap<>();
        for(String car: that.cars.keySet()){
            this.cars.put(car, that.cars.get(car));
        }
        this.cars.put(carName, carPos);
        this.boardBuilder();
    }

    @Override
    public boolean isSolution() {
        return cars.get("X")[3] == board[0].length - 1;
    }

    @Override
    public Collection<Configuration> getNeighbors() {
        Collection<Configuration> neighbors = new ArrayList<>();
        int move = 0;
        //Iterate through car names and therefore able to iterate through both hashMaps simultaneously
        for(String car: cars.keySet()){
            //Check if its vertical
            if(carProp.get(car).equals(VERT)){
                if(cars.get(car)[0] == 0){
                    //only neighbors down
                    move = canMoveDown(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0] + move, cars.get(car)[1], cars.get(car)[2] + move, cars.get(car)[3]}));

                } else if(cars.get(car)[2] == board.length-1){
                    //only neighbors up
                    move = canMoveUp(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0] - move, cars.get(car)[1], cars.get(car)[2] - move, cars.get(car)[3]}));

                } else{
                    //neighbors up and down
                    move = canMoveDown(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0] + move, cars.get(car)[1], cars.get(car)[2] + move, cars.get(car)[3]}));
                    move = canMoveUp(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0] - move, cars.get(car)[1], cars.get(car)[2] - move, cars.get(car)[3]}));
                }

            //otherwise, its horizontal
            } else{
                if(cars.get(car)[1] == 0){
                    //only neighbors to the right
                    move = canMoveRight(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0], cars.get(car)[1] + move, cars.get(car)[2], cars.get(car)[3] + move}));

                } else if (cars.get(car)[3] == board[0].length - 1){
                    //only neighbors to the left
                    move = canMoveLeft(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0], cars.get(car)[1] - move, cars.get(car)[2], cars.get(car)[3] - move}));

                } else {
                    //neighbors to the left and right
                    move = canMoveRight(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0], cars.get(car)[1] + move, cars.get(car)[2], cars.get(car)[3] + move}));
                    move = canMoveLeft(car);
                    neighbors.add(new JamConfig(this, car, new int[]{cars.get(car)[0], cars.get(car)[1] - move, cars.get(car)[2], cars.get(car)[3] - move}));
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof JamConfig otherConfig) {
            for(int k = 0; k < this.board.length; k++){
                for(int l = 0; l < this.board[0].length; l++){
                    if(!this.board[k][l].equals(otherConfig.board[k][l])){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

    @Override
    public String toString(){
        String fin = "  ";
        for(int i = 0; i < board[0].length; i++){
            fin += " " + i;
        }
        fin += "\n  ";
        for(int i = 0; i < board[0].length; i++){
            fin += "--";
        }
        fin += "\n";
        for(int i = 0; i < board.length; i++){
            fin += i + "|";
            for(int j = 0; j < board[0].length; j++){
                fin += " " + board[i][j];
            }
            if(!(i == board.length - 1)) {
                fin += "\n";
            }
        }
        return fin;
    }

    /**
     * A helper function to construct the board including the cars
     */
    public void boardBuilder(){
        carProp = new HashMap<>();
        int[] temp;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                board[i][j] = ".";
            }
        }
        for(String car: cars.keySet()){
            temp = cars.get(car);
            if(temp[2] - temp[0] == 0){
                carProp.put(car, HORZ);
            } else carProp.put(car, VERT);
            for(int i = temp[0]; i <= temp[2]; i++){
                for(int j = temp[1]; j <= temp[3]; j++){
                    board[i][j] = car;
                }
            }
        }
    }

    public int canMoveDown(String car){
        int[] pos = cars.get(car);
        int moveCounter = 0;
        for(int i = pos[2]+1; i < board.length; i++){
            if(!(board[i][pos[1]].equals("."))){
                return moveCounter;
            } else {
                moveCounter++;
            }
        }
        return moveCounter;
    }

    public int canMoveUp(String car){
        int[] pos = cars.get(car);
        int moveCounter = 0;
        for(int i = pos[0]-1; i >= 0; i--){
            if(!(board[i][pos[1]].equals("."))){
                return moveCounter;
            } else {
                moveCounter++;
            }
        }
        return moveCounter;
    }

    public int canMoveRight(String car){
        int[] pos = cars.get(car);
        int moveCounter = 0;
        for(int i = pos[3]+1; i < board[0].length; i++){
            if(!(board[pos[0]][i].equals("."))){
                return moveCounter;
            } else {
                moveCounter++;
            }
        }
        return moveCounter;
    }

    public int canMoveLeft(String car){
        int[] pos = cars.get(car);
        int moveCounter = 0;
        for(int i = pos[1]-1; i >= 0; i--){
            if(!(board[pos[0]][i].equals("."))){
                return moveCounter;
            } else {
                moveCounter++;
            }
        }
        return moveCounter;
    }

    public String getElement(int r, int c){
        return board[r][c];
    }

    public String getCarRot(String car){
        return carProp.get(car);
    }

    public int[] getCarPos(String car){
        return cars.get(car);
    }

    public void moveCar(String car, int rChange, int cChange){
        int[] oldPos = cars.get(car);
        cars.put(car, new int[]{oldPos[0]+rChange, oldPos[1]+cChange, oldPos[2]+rChange, oldPos[3]+cChange});
        boardBuilder();
    }
}
