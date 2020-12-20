package test;

import gameClient.util.Range;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Range_Test {
    @Test
    void isEmpty() {
        Range r1 = new Range(0,-9);
        Range r2 = new Range(0,7);
        assertTrue(r1.isEmpty());
        assertFalse(r2.isEmpty());
    }


    @Test
    void get_length()
    {
        Range r2 = new Range(0,3);
        assertEquals(3,r2.get_length());
    }


    @Test
    void getPortion()
    {
        Range r2 = new Range(0,2);
        assertEquals(0.25, r2.getPortion(0.5));
    }

    @Test
    void fromPortion() {
        Range r2 = new Range(0,4);
        assertEquals(2, r2.fromPortion(0.5));
    }
}