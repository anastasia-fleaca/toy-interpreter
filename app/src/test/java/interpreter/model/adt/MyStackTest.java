package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MyStackTest {
    private MyStack<Integer> stack;

    @BeforeEach
    public void setUp () {
        stack = new MyStack<>();
    }

    @Test
    void LastInFirstOut () throws ADTException {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3,stack.pop());
        assertEquals(2,stack.pop());
        assertEquals(1,stack.pop());
    }

    @Test
    void popOnEmptyStackThrows () throws ADTException {
        stack.push(1);
        stack.pop();
        assertThrows(ADTException.class, () -> stack.pop());
    }
}
