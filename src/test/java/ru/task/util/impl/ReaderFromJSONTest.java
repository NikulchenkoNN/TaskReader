package ru.task.util.impl;

import org.junit.jupiter.api.Test;
import ru.task.util.Reader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReaderFromJSONTest {
    @Test
    void readerShouldNotThrowExceptionWithWrongFileType() {
        Reader reader = new ReaderFromJSON();
        assertDoesNotThrow(() -> reader.readFromFile("wrong file.txt"));
    }

    @Test
    void readerShouldNotThrowExceptionWithCorrectFileType() {
        Reader reader = new ReaderFromJSON();
        assertDoesNotThrow(() -> reader.readFromFile("src/test/resources/test.json"));
    }
}