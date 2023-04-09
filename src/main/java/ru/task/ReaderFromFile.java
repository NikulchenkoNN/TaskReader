package ru.task;

import com.opencsv.CSVReader;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ReaderFromFile {
    private final Map<String, Integer> duplicateMap;
    private final Map<String, BigInteger> weightMap;
    private BigInteger max;
    private BigInteger min;

    public ReaderFromFile() {
        duplicateMap = new HashMap<>();
        weightMap = new HashMap<>();
        max = BigInteger.ZERO;
        min = BigInteger.ZERO;
    }

    public void readFromFile(String pathFile) throws Exception {
        if (pathFile.endsWith(".json")) {
            readFromJSON(pathFile);
        } else if (pathFile.endsWith(".csv")) {
            readFromCSV(pathFile);
        } else {
            System.out.println("Wrong file type");
        }
    }

    private void readFromJSON(String path) throws IOException {
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
                handler(group, type, weight);
            }
        } catch (Exception e) {
            System.out.println("Some problems "+ e.getMessage());
        }
        printData();
    }

    private void readFromCSV(String pathFile) {
        CSVReader reader;
        String[] r;
        try {
            reader = new CSVReader(new FileReader(Paths.get(pathFile).toFile()));
            while ((r = reader.readNext()) != null) {
                String group;
                String type;
                BigInteger weight;
                try {
                    group = r[0];
                    type = r[1];
                    weight = BigInteger.valueOf(Long.parseLong(r[3]));
                } catch (NumberFormatException e) {
                    System.out.println("Skip string " + Arrays.toString(r));
                    continue;
                }
                handler(group, type, weight);
            }
        } catch (Exception e) {
            System.out.println("Some problems " + e.getMessage());
        }
        printData();
    }

    private void handler(String group, String type, BigInteger weight) {
        if (min.compareTo(BigInteger.ZERO) == 0) {
            min = weight;
        } else if (weight.compareTo(min) < 0) {
            min = weight;
        }
        if (weight.compareTo(max) > 0) {
            max = weight;
        }
        if (!duplicateMap.containsKey(group.concat(" ").concat(type))) {
            duplicateMap.put(group + type, 1);
        } else {
            int aLong = duplicateMap.get(group.concat(" ").concat(type));
            duplicateMap.put(group + type, aLong + 1);
        }
        if (!weightMap.containsKey(group)) {
            weightMap.put(group, weight);
        } else {
            BigInteger integer = weightMap.get(group);
            weightMap.put(group, integer.add(weight));
        }
    }

    private void printData() {
        duplicateMap.entrySet().stream().filter(l -> l.getValue() > 1).forEach(System.out::println);
        weightMap.forEach((s, i) -> System.out.println("type '" + s + "' summary weight = " + i));
        System.out.println("max weight = " + max);
        System.out.println("min weight = " + min);
    }
}
