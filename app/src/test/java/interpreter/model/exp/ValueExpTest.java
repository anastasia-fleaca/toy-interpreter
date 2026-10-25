package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValueExpTest {
    @Test
    void evalReturnsValue() throws MyException {
        ValueExp exp = new ValueExp(new IntValue(10));
        assertEquals(new IntValue(10), exp.eval(new MyDictionary<>()));
    }
}