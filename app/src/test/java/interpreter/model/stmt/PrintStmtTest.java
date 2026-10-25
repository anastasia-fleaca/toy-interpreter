package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void printAppendsToOut() throws MyException {
        IStmt printStmt = new PrintStmt(new ValueExp(new IntValue(42)));
        printStmt.execute(state);
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(42), state.getOut().get(0));
    }
}