package ru.task.util;

import java.io.IOException;

public interface Reader {
    void readFromFile(String path) throws IOException;
}
