package ru.task;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String path;
        while (!"exit".equals(path = scanner.next())) {
            new ReaderFromFile().readFromFile(path);
        }
    }
}