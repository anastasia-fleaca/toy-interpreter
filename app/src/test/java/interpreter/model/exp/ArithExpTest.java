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

public class ArithExpTest {
    private MyDictionary<String, IValue> symTable;

    private static IExp num(int n) { return new ValueExp(new IntValue(n)); }

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void testAddition() throws MyException {
        IExp exp = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(15), exp.eval(symTable));
    }

    @Test
    void testSubtraction() throws MyException {
        IExp exp = new ArithExp(ArithOp.SUB, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(5), exp.eval(symTable));
    }

    @Test
    void testMultiplication() throws MyException {
        IExp exp = new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(50), exp.eval(symTable));
    }

    @Test
    void testDivision() throws MyException {
        IExp exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(5)));
        assertEquals(new IntValue(2), exp.eval(symTable));
    }

    @Test
    void testDivisionByZeroThrowsException() throws  MyException {
        IExp exp = new ArithExp(ArithOp.DIV, new ValueExp(new IntValue(10)), new ValueExp(new IntValue(0)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }

    @Test
    void testNonIntegerOperandThrowsException() throws MyException {
        IExp exp = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(10)), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }

    @Test
    void respectsOperatorPrecedenceEncodedInTheTree() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, num(2),
                new ArithExp(ArithOp.MUL, num(3), num(5)));
        assertEquals(new IntValue(17), e.eval(symTable));
    }
}
