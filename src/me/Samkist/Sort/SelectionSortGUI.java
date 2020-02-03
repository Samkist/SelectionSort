package me.Samkist.Sort;

import BreezySwing.GBFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class SelectionSortGUI extends GBFrame {

    private static JFrame frame = new SelectionSortGUI();
    private JTextField inputField = addTextField("", 0, 0, 1, 1);
    private JButton calculate = addButton("Calculate", 0, 1, 1, 1);
    private JTextArea outputField = addTextArea("", 1, 0, 1,1);
    private JButton reset = addButton("Reset", 1, 1, 1,1);
    private Sorter s;

    public static void main(String[] args) {
        frame.setSize(400, 400);
        frame.setTitle("Selection Sort");
        frame.setVisible(true);
    }

    @Override
    public void buttonClicked(JButton jButton) {
        if(jButton.equals(calculate)) {
            String rawString = inputField.getText();
            rawString = rawString.replace("\t", "").trim().replaceAll("^ +| +$|( )+", "");
            String[] split = rawString.split(",");
            Integer[] numbers = new Integer[split.length];
            for(int i = 0; i < split.length; i++) {
                numbers[i] = Integer.parseInt(split[i]);
            }
            s = new Sorter(numbers);
            StringBuilder builder = new StringBuilder();
            builder.append("Sorted List: ");
            s.get().forEach(i -> builder.append(i + " "));
            builder.append("\n");
            builder.append("Mean: " + getMean(s.get()) + "\n");
            builder.append("Median: " + getMedian(s.get()) + "\n");
            builder.append("Mode(s): ");
            try {
                getModes(s.get()).forEach(i -> builder.append(i + " "));
            } catch(NoModeException e) {
                builder.append("No Mode");
            }
            builder.append("\n");
            builder.append("Standard Deviation: " + getStandardDeviation(s.get()));
            outputField.setText(builder.toString());
        }
        if(jButton.equals(reset)) {
            outputField.setText("");
            inputField.setText("");
        }
    }

    public SelectionSortGUI() {
        outputField.setEditable(false);
    }

    private double getStandardDeviation(ArrayList<Integer> arr) {
        double mean = getMean(arr);
        double total = 0;
        for(int i = 0; i < arr.size(); i++) {
            total += Math.pow((arr.get(i) - mean), 2);
        }
        double mean2 = total/arr.size();
        return Math.sqrt(mean2);
    }

    private double getMean(ArrayList<Integer> arr) {
        double total = 0;
        for(Integer i : arr)
            total += i;
        return total / arr.size();
    }

    private double getMedian(ArrayList<Integer> arr) {
        int middleIndex = arr.size()/2;
        if(arr.size() % 2 != 0)
            return arr.get(middleIndex);
        return (arr.get(middleIndex) + arr.get(middleIndex+1))/2;
    }

    private ArrayList<Integer> getModes(ArrayList<Integer> arr) throws NoModeException {
        HashMap<Integer, Integer> occ = new HashMap<>();
        for(Integer i : arr)
            occ.put(i, 0);
        for(Integer i : arr) {
            occ.put(i, occ.get(i) + 1);
        }
        AtomicReference<Integer> highestValue = new AtomicReference<>(0);
        ArrayList<Integer> modes = new ArrayList<>();
        occ.entrySet().forEach(i -> {
            if(i.getValue().compareTo(highestValue.get()) > 0) {
                modes.clear();
                highestValue.set(i.getValue());
                modes.add(i.getKey());
            }
            if(i.getValue().compareTo(highestValue.get()) == 0 && !modes.contains(i.getKey())) {
                modes.add(i.getKey());
            }
        });
        if(modes.containsAll(s.get()))
            throw new NoModeException("No Mode");
        return modes;
    }
}
