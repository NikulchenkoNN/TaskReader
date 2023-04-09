package ru.task.util.impl;

import org.junit.jupiter.api.Test;
import ru.task.util.Reader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReaderFromCSVTest {
    @Test
    void readerShouldNotThrowExceptionWithIncorrectFileType() throws IOException {
        Reader reader = new ReaderFromCSV();
        assertDoesNotThrow( () -> reader.readFromFile("wrong file.txt"));
    }

    @Test
    void readerShouldNotThrowExceptionWithCorrectFileType() throws IOException {
        Reader reader = new ReaderFromCSV();
        assertDoesNotThrow(() -> reader.readFromFile("src/test/resources/test.csv"));
    }
}