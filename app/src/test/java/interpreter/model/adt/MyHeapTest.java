package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyHeapTest {
    private MyHeap heap;

    @BeforeEach
    void setUp() {
        heap = new MyHeap();
    }

    @Test
    void allocateReturnsConsecutiveIntegerAddresses() throws ADTException {
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
        assertEquals(new IntValue(20), heap.get(2));
    }

    @Test
    void getMissingAddressThrows () {
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    void updateMissingAddressThrows () {
        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(10)));
    }
}
