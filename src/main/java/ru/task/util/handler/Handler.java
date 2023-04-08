package ru.task.util.handler;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler {

    private final Map<String, Integer> duplicateMap;
    private final Map<String, BigInteger> weightMap;
    private BigInteger max;
    private BigInteger min;

    public Handler() {
        duplicateMap = new HashMap<>();
        weightMap = new HashMap<>();
        max = BigInteger.ZERO;
        min = BigInteger.ZERO;
    }

    public void handleMinMax(BigInteger weight) {
        if (min.compareTo(BigInteger.ZERO) == 0) {
            min = weight;
        } else if (weight.compareTo(min) < 0) {
            min = weight;
        }
        if (weight.compareTo(max) > 0) {
            max = weight;
        }
    }

    public void handleDuplicate(String group, String type) {
        String unit = group.concat(" ").concat(type);
        if (!duplicateMap.containsKey(unit)) {
            duplicateMap.put(unit, 1);
        } else {
            int count = duplicateMap.get(unit);
            duplicateMap.put(unit, count + 1);
        }
    }

    public void handleGroupWeight(String group, BigInteger weight) {
        if (!weightMap.containsKey(group)) {
            weightMap.put(group, weight);
        } else {
            BigInteger summaryWeight = weightMap.get(group);
            weightMap.put(group, summaryWeight.add(weight));
        }
    }

    public Map<String, Integer> printDuplicate() {
        return duplicateMap.entrySet().stream()
                .filter(l -> l.getValue() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, BigInteger> printGroupWeight() {
        return weightMap;
    }

    public String printMinMax() {
        return "max weight = " + max + " " + "min weight = " + min;
    }
}
