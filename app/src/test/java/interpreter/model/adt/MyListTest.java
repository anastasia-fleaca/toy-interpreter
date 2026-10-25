package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyListTest {
    private MyList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void addAndGetTest () throws MyException {
        list.add(10);
        list.add(20);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void testSize () {
        assertEquals(0, list.size());
        list.add(100);
        assertEquals(1, list.size());
    }

    @Test
    void testGetOutOfBoundsThrowsException () throws MyException {
        list.add(5);
        assertThrows(ADTException.class, () -> list.get(10));
    }
}
