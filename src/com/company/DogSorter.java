package com.company;
import java.util.ArrayList;

//Axel Gustafsson axgu8924
public class DogSorter {
    public ArrayList<Dog>sort(ArrayList<Dog> dogs) {
        for (int i = 0; i < dogs.size() - 1; i++) {
            int min = i;

            for (int j = i + 1; j < dogs.size(); j++) {
                if (dogs.get(j).compareTo(dogs.get(min)) == -1) {
                    min = j;
                }
            }

            Dog temp = dogs.get(min);
            dogs.set(min, dogs.get(i));
            dogs.set(i, temp);
        }

        return dogs;
    }
}