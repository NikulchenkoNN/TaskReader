package ru.task;

import ru.task.util.Reader;
import ru.task.util.impl.ReaderFromCSV;
import ru.task.util.impl.ReaderFromJSON;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Reader reader;
        Scanner scanner = new Scanner(System.in);
        String path;
        while (!"exit".equals(path = scanner.next())) {
            if (path.endsWith(".json")) {
                reader = new ReaderFromJSON();
                reader.readFromFile(path);
            } else if (path.endsWith(".csv")) {
                reader = new ReaderFromCSV();
                reader.readFromFile(path);
            } else {
                System.out.println("Wrong file type");
            }
        }
    }
}