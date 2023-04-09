package ru.task.util.impl;

import com.opencsv.CSVReader;
import ru.task.util.Reader;
import ru.task.util.handler.Handler;

import java.io.FileReader;
import java.math.BigInteger;
import java.nio.file.Paths;

public class ReadFromCSV implements Reader {
    @Override
    public void readFromFile(String path) {
        CSVReader reader;
        String[] r;
        Handler handler = new Handler();
        try {
            reader = new CSVReader(new FileReader(Paths.get(path).toFile()));
            while ((r = reader.readNext()) != null) {
                String group;
                String type;
                BigInteger weight;
                try {
                    group = r[0];
                    type = r[1];
                    weight = BigInteger.valueOf(Long.parseLong(r[3]));
                } catch (NumberFormatException e) {
                    continue;
                }
                handler.handleDuplicate(group, type);
                handler.handleGroupWeight(group, weight);
                handler.handleMinMax(weight);
            }
        } catch (Exception e) {
            System.out.println("Some problems " + e.getMessage());
            return;
        }

        System.out.println(handler.printDuplicate());
        System.out.println(handler.printMinMax());
        System.out.println(handler.printGroupWeight());
    }
}
