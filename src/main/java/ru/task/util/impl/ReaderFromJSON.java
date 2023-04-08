package ru.task.util.impl;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import ru.task.util.Reader;
import ru.task.util.handler.Handler;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

public class ReaderFromJSON implements Reader {

    @Override
    public void readFromFile(String path) throws IOException {
        Handler handler = new Handler();
        JsonFactory jsonFactory = new MappingJsonFactory();
        JsonParser parser = jsonFactory.createJsonParser(new File(path));
        JsonToken current;
        current = parser.nextToken();
        try {
            if (current != JsonToken.START_ARRAY) {
                System.out.println("Нет начала массива данных");
                return;
            }
            while (parser.nextToken() != JsonToken.END_ARRAY) {
                JsonNode jsonNode = parser.readValueAsTree();
                String group = jsonNode.get("group").getTextValue();
                String type = jsonNode.get("type").getTextValue();
                BigInteger weight = BigInteger.valueOf(jsonNode.get("weight").getLongValue());
                handler.handleGroupWeight(group, weight);
                handler.handleDuplicate(group, type);
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
