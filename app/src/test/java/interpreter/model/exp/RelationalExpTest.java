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

public class RelationalExpTest {
    private MyDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void nonIntegerOperandThrowsException() {
        IExp exp = new RelationalExp(RelOp.EQ,new ValueExp(new BoolValue(true)), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> exp.eval(symTable));
    }

    @Test
    void greaterThanIntegerOperandWorksCorrect() throws MyException {
        IExp exp1 = new RelationalExp(RelOp.GT,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(true),exp1.eval(symTable));
        IExp exp2 = new RelationalExp(RelOp.GT,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(11)));
        assertEquals(new BoolValue(false),exp2.eval(symTable));
        IExp exp3 = new RelationalExp(RelOp.GT,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(8)));
        assertEquals(new BoolValue(false),exp3.eval(symTable));
    }

    @Test
    void lessThanIntegerOperandWorksCorrect() throws MyException {
        IExp exp1 = new RelationalExp(RelOp.LT,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(12)));
        assertEquals(new BoolValue(true),exp1.eval(symTable));
        IExp exp2 = new RelationalExp(RelOp.LT,new ValueExp(new IntValue(14)), new ValueExp(new IntValue(11)));
        assertEquals(new BoolValue(false),exp2.eval(symTable));
        IExp exp3 = new RelationalExp(RelOp.LT,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(8)));
        assertEquals(new BoolValue(false),exp3.eval(symTable));
    }

    @Test
    void equalsIntegerOperandWorksCorrect() throws MyException {
        IExp exp1 = new RelationalExp(RelOp.EQ,new ValueExp(new IntValue(6)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(true),exp1.eval(symTable));
        IExp exp2 = new RelationalExp(RelOp.EQ,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(11)));
        assertEquals(new BoolValue(false),exp2.eval(symTable));
        IExp exp3 = new RelationalExp(RelOp.EQ,new ValueExp(new IntValue(11)), new ValueExp(new IntValue(8)));
        assertEquals(new BoolValue(false),exp3.eval(symTable));
    }

    @Test
    void greaterThanOrEqualIntegerOperandWorksCorrect() throws MyException {
        IExp exp1 = new RelationalExp(RelOp.GE,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(true),exp1.eval(symTable));
        IExp exp2 = new RelationalExp(RelOp.GE,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(11)));
        assertEquals(new BoolValue(false),exp2.eval(symTable));
        IExp exp3 = new RelationalExp(RelOp.GE,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(8)));
        assertEquals(new BoolValue(true),exp3.eval(symTable));
    }

    @Test
    void lessThanOrEqualIntegerOperandWorksCorrect() throws MyException {
        IExp exp1 = new RelationalExp(RelOp.LE,new ValueExp(new IntValue(4)), new ValueExp(new IntValue(6)));
        assertEquals(new BoolValue(true),exp1.eval(symTable));
        IExp exp2 = new RelationalExp(RelOp.LE,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(7)));
        assertEquals(new BoolValue(false),exp2.eval(symTable));
        IExp exp3 = new RelationalExp(RelOp.LE,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(8)));
        assertEquals(new BoolValue(true),exp3.eval(symTable));
    }

    @Test
    void notEqualIntegerOperandWorksCorrect() throws MyException {
        IExp exp1 = new RelationalExp(RelOp.NE,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(8)));
        assertEquals(new BoolValue(false),exp1.eval(symTable));
        IExp exp2 = new RelationalExp(RelOp.NE,new ValueExp(new IntValue(8)), new ValueExp(new IntValue(9)));
        assertEquals(new BoolValue(true),exp2.eval(symTable));
        IExp exp3 = new RelationalExp(RelOp.NE,new ValueExp(new IntValue(9)), new ValueExp(new IntValue(8)));
        assertEquals(new BoolValue(true),exp3.eval(symTable));
    }
}
