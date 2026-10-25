package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyDictionaryTest {
    private MyDictionary<String, Integer> dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new MyDictionary<>();
    }

    @Test
    void putAddsKeyToDictionary(){
        assertFalse(dictionary.isDefined("key1"));
        dictionary.put("key1",1);
        assertTrue(dictionary.isDefined("key1"));
    }

    @Test
    void lookupReturnsCorrectValue() throws MyException {
        dictionary.put("key1",5);
        assertEquals(5,dictionary.lookup("key1"));
    }

    @Test
    void lookupThrowsExceptionWhenKeyNotFound() throws MyException {
        assertThrows(ADTException.class, () -> dictionary.lookup("key"));
    }

    @Test
    void updateChangesValue() throws MyException {
        dictionary.put("x", 10);
        dictionary.update("x", 20);
        assertEquals(20, dictionary.lookup("x"));
    }
}
