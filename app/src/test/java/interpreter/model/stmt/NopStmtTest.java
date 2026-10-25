package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void testNopDoesNothing() throws MyException {
        IStmt nop = new NopStmt();
        nop.execute(state);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(0, state.getOut().size());
    }
}