package com.limewire.game.unit_tests;

import com.limewire.game.helpers.MapReader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MapReaderTest {

    @Test
    void getRawMapData() {
        MapReader mr = new MapReader();
        String[][] rawMap;
        rawMap = mr.getRawMapData("/16x16Map.txt");

        String[][] expectedMap;
        expectedMap = new String[][] {{"w", "w", "w", "w", "w", "l", "l", "w", "w", "w", "w", "w", "w", "w", "w", "w"},
                {"w", "l", "w", "w", "w", "l", "l", "w", "w", "w", "w", "w", "l", "l", "l", "w"},
                {"w", "l", "l", "w", "w", "l", "w", "w", "l", "l", "w", "w", "w", "l", "w", "w"},
                {"w", "w", "w", "w", "w", "w", "w", "w", "l", "w", "w", "l", "w", "w", "w", "w"},
                {"w", "w", "w", "l", "w", "w", "l", "w", "w", "w", "l", "l", "w", "w", "w", "w"},
                {"w", "w", "w", "l", "l", "w", "w", "w", "w", "w", "w", "l", "w", "w", "l", "w"},
                {"w", "w", "w", "w", "l", "l", "w", "w", "l", "w", "w", "w", "w", "l", "l", "w"},
                {"w", "w", "w", "w", "w", "w", "w", "l", "l", "l", "w", "w", "w", "w", "w", "w"},
                {"w", "w", "l", "w", "w", "w", "w", "w", "w", "l", "l", "w", "w", "w", "w", "w"},
                {"w", "w", "l", "w", "w", "w", "w", "w", "w", "l", "w", "w", "w", "l", "w", "w"},
                {"w", "w", "w", "w", "w", "l", "w", "l", "w", "w", "w", "w", "l", "l", "w", "w"},
                {"w", "w", "w", "l", "w", "w", "w", "w", "w", "w", "w", "l", "l", "l", "w", "w"},
                {"w", "l", "w", "w", "w", "w", "l", "w", "w", "w", "w", "l", "l", "l", "w", "w"},
                {"w", "w", "w", "l", "w", "w", "w", "w", "w", "w", "w", "l", "l", "w", "w", "w"},
                {"w", "w", "w", "w", "w", "l", "w", "l", "w", "w", "w", "w", "w", "w", "w", "w"},
                {"w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w", "w"}};

        assertTrue(Arrays.equals(expectedMap, rawMap));
    }

    @Test
    void convertMap() {
    }

    @Test
    void getMap() {
    }
}