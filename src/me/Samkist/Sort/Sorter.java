package me.Samkist.Sort;

import java.util.ArrayList;
import java.util.Arrays;

public class Sorter<T extends Comparable<T>> {
    private ArrayList<T> list;

    public Sorter(ArrayList<T> list) {
        this.list = (ArrayList<T>) list.clone();
        sort();
    }

    public Sorter(T[] list) {
        this.list = new ArrayList<>(Arrays.asList(list));
        sort();
    }

    private void sort() {
        for(int i = 0; i < list.size(); i++) {
            int min = i;
            for(int j = i +1; j < list.size(); j++) {
                if(list.get(j).compareTo(list.get(min)) < 0)
                    min = j;
            }
            if(min != i) {
                T a = list.get(min);
                T b = list.get(i);
                list.set(min, b);
                list.set(i, a);
            }
        }
    }

    public ArrayList<T> get() {
        return list;
    }
}
