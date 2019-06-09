package airport.simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Helpers {
    public static List getRandomNumbers(Integer min, Integer max, Integer amount){
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, amount);
    }

    public static Integer getRandomNumber(Integer min, Integer max){
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return Integer.parseInt(numbers.get(0).toString());
    }
}
