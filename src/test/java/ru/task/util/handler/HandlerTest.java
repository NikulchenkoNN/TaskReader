package ru.task.util.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HandlerTest {

    static Handler handler;
    static List<Integer> minMaxList = new ArrayList<>(Arrays.asList(10, 12, 15, 16, 1, 100));;
    static Map<String, Integer> weightMapTest = new HashMap<>();

    @BeforeEach
    void setUp() {
        handler = new Handler();

    }

    @Test
    void minMaxTest() {
        for (int value : minMaxList) {
            handler.handleMinMax(BigInteger.valueOf(value));
        }

        assertEquals("max weight = 100 min weight = 1", handler.printMinMax());
    }

    @Test
    void groupWeightTest() {
        Map<String, Integer> weightMapTest = new HashMap<>();
        weightMapTest.put("group1", 5);
        weightMapTest.put("group2", 3);
        weightMapTest.put("group3", 4);

        Map<String, BigInteger> expected = new HashMap<>();
        expected.put("group1", BigInteger.valueOf(7));
        expected.put("group2", BigInteger.valueOf(9));
        expected.put("group3", BigInteger.valueOf(4));

        for (Map.Entry<String, Integer> e : weightMapTest.entrySet()) {
            handler.handleGroupWeight(e.getKey(), BigInteger.valueOf(e.getValue()));
        }
        handler.handleGroupWeight("group1", BigInteger.valueOf(2));
        handler.handleGroupWeight("group2", BigInteger.valueOf(6));

        assertEquals(3, handler.printGroupWeight().size());
        assertEquals(expected, handler.printGroupWeight());
    }

    @Test
    void duplicateTest() {
        HashMap<String, String> duplicateMapTest = new HashMap<>();
        duplicateMapTest.put("g1", "t1");
        duplicateMapTest.put("g2", "t2");
        duplicateMapTest.put("g3", "t3");

        for (Map.Entry<String, String> e : duplicateMapTest.entrySet()) {
            handler.handleDuplicate(e.getKey(), e.getValue());
        }
        handler.handleDuplicate("g2", "t1");
        handler.handleDuplicate("g1", "t3");
        handler.handleDuplicate("g3", "t1");
        handler.handleDuplicate("g1", "t1");
        handler.handleDuplicate("g1", "t1");
        handler.handleDuplicate("g2", "t2");

        assertEquals(2, handler.printDuplicate().size());
        assertEquals("{g1 t1=3, g2 t2=2}", handler.printDuplicate().toString());
    }
}
