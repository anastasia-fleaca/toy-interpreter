package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {
    private MyDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void evalDefinedVariableReturnsValue() throws MyException {
        symTable.put("x", new IntValue(10));
        VarExp varExp = new VarExp("x");
        assertEquals(new IntValue(10), varExp.eval(symTable));
    }

    @Test
    void evalUndefinedVariableThrowsException() {
        VarExp varExp = new VarExp("y");
        assertThrows(MyException.class, () -> varExp.eval(symTable));
    }
}