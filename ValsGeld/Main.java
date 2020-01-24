import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        FakeMoneyScale fms = new FakeMoneyScale();
        try {
            fms.start();
        } catch (IOException ex) {
            System.err.println("Invalid input");
        }
    }
}

class FakeMoneyScale {
    //Private Variables
        Queue<ScaleMeasurement[]> _qData;

    public FakeMoneyScale() {
        _qData = new LinkedList<>();
    }

public void start() throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // Get the number of tasks
        int _numTasks = Integer.parseInt(br.readLine());
        int _numData;

        for (int i = 0; i < _numTasks; i++){
            _numData = Integer.parseInt(br.readLine());
            ScaleMeasurement[] sm = new ScaleMeasurement[_numData];
            for (int j = 0; j < _numData; j++){
                String[] data = br.readLine().split(" ");
                sm[j] = new ScaleMeasurement(data[0], data[1], data[2]);
            }
            _qData.add(sm);
        }
        while(!_qData.isEmpty()) {
            inspectData();
        }
    }

    private void inspectData() {
        ScaleMeasurement[] data = _qData.poll();

        // With only 1 measurement given, it is impossible to compare the different coins
        if (data.length == 1){
            System.out.println("Te weinig gegevens.");
            return;
        }

        // Make a list of all the coins in the current measurement
        SortedSet<Character> listAll = new TreeSet<>();

        // Make a list of all the coins in the current measurement where the scale is in balance
        SortedSet<Character> listBalance = new TreeSet<>();

        for (int i = 0; i < data.length; i++) {
            boolean inBalance = (data[i].getEquality().equals("evenwicht") ? true : false);
            for (char c : data[i].getAllLetters().toCharArray()) {
                listAll.add(c);
                if (inBalance)
                    listBalance.add(c);
            }
        }

        // Subtract the 2 lists of each other
        SortedSet<Character> listResult = new TreeSet<>(listAll);
        listResult.removeAll(listBalance);

        // If there is only 1 coin left check the value in the measurement
        if (listResult.size() == 1) {
            char c = listResult.first();
            boolean isDone = false;
            for (int i = 0; i < data.length && !isDone; i++){
                // If the coin is on the left and the scale is going up, the coin is heavier
                // Or if the coin is on the right and the scale is going down, the coin is heavier aswell
                if((data[i].getLeft().indexOf(c) >= 0 && data[i].getEquality().equals("omhoog")) || (data[i].getRight().indexOf(c) >= 0 && data[i].getEquality().equals("omlaag"))) {
                    System.out.println("Het valse geldstuk " + c + " is zwaarder.");
                    return;
                    // If the coin is on the right and the scale is going up, the coin is lighter
                    // Or if the coin is on the left and the scale is going up, the coin is lighter
                } else if ((data[i].getLeft().indexOf(c) >= 0 && data[i].getEquality().equals("omlaag")) || (data[i].getRight().indexOf(c) >= 0 && data[i].getEquality().equals("omhoog"))) {
                    System.out.println("Het valse geldstuk " + c + " is lichter.");
                    return;
                }
            }
        } else {
            if(listResult.size() > 1){
                // Check if left and right are the same but with different equality
                for(ScaleMeasurement sm1 : data){
                    for(ScaleMeasurement sm2 : data){
                        if((sm1.compareLeft(sm2) && !sm1.compareEquality(sm2)) || ((!sm1.compareLeft(sm2) && !sm1.compareRight(sm2) && sm1.compareEquality(sm2)))) {
                            System.out.println("Inconsistente gegevens.");
                            return;
                        }
                        if((sm1.getLeft().equals(sm2.getRight()) ^ sm1.getRight().equals(sm2.getLeft())) && !sm1.compareEquality(sm2)){
                            if(sm1.getLeft().length() > 1 || sm1.getRight().length() > 1 || sm2.getLeft().length() > 1 || sm2.getRight().length() > 1){
                                System.out.println("Te weinig gegevens.");
                                return;
                            }
                            System.out.println("Het valse geldstuk " + (sm1.getLeft().equals(sm2.getRight()) ? sm1.getLeft() : sm1.getRight()) + " is " + (!sm1.compareEquality(sm2) ? "lichter" : "zwaarder") + ".");
                            return;
                        }
                        if((sm1.compareLeft(sm2) ^ sm1.compareRight(sm2)) && sm1.compareEquality(sm2)){
                            System.out.println("Het valse geldstuk " + (sm1.getLeft().equals(sm2.getRight()) ? sm1.getRight() : sm1.getLeft()) + " is " + (sm1.compareEquality(sm2) ? "zwaarder" : "lichter") + ".");
                            return;
                        }
                    }
                }
            }
            // If there are no coins or too much to check, there is inconsistent data
            else if(listResult.size() < 1 ){
                System.out.println("Inconsistente gegevens.");
                return;
            }
        }

    }

}

class ScaleMeasurement {
    String _left, _right, _equality;

    public ScaleMeasurement(String left, String right, String equality){
        this._left = left;
        this._right = right;
        this._equality = equality;
    }

    public String getLeft(){
        return this._left;
    }

    public String getRight(){
        return this._right;
    }

    public String getAllLetters(){
        return this._left + this._right;
    }

    public String getEquality(){
        return this._equality;
    }

    public boolean compareLeft(ScaleMeasurement sm){
        return this._left.equals(sm.getLeft());
    }

    public boolean compareRight(ScaleMeasurement sm){
        return this._right.equals(sm.getRight());
    }

    public boolean compareEquality(ScaleMeasurement sm){
        return this._equality.equals(sm.getEquality());
    }
}
