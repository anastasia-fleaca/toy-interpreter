package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogicExpTest {
    private MyDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void testAndCondition() throws MyException {
        IExp exp1 = new LogicExp(LogicOp.AND,new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false),exp1.eval(symTable));
        IExp exp2 = new LogicExp(LogicOp.AND,new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true),exp2.eval(symTable));
        IExp exp3 = new LogicExp(LogicOp.AND,new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false),exp3.eval(symTable));
    }

    @Test
    void testOrCondition() throws MyException {
        IExp exp1 = new LogicExp(LogicOp.OR,new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(true),exp1.eval(symTable));
        IExp exp2 = new LogicExp(LogicOp.AND,new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertEquals(new BoolValue(true),exp2.eval(symTable));
        IExp exp3 = new LogicExp(LogicOp.AND,new ValueExp(new BoolValue(false)), new ValueExp(new BoolValue(false)));
        assertEquals(new BoolValue(false),exp3.eval(symTable));
    }

    @Test
    void testNonBooleanOperandThrowsException() throws MyException {
        IExp exp = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(10)), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }
}
