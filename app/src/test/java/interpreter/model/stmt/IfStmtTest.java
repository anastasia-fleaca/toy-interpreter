package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void ifConditionTruePushesThenBranch() throws MyException {
        IStmt thenStmt = new NopStmt();
        IStmt elseStmt = new NopStmt();
        IStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(true)), thenStmt, elseStmt);
        ifStmt.execute(state);
        assertEquals(thenStmt, state.getExeStack().pop());
    }

    @Test
    void ifConditionFalsePushesElseBranch() throws MyException {
        IStmt thenStmt = new NopStmt();
        IStmt elseStmt = new NopStmt();
        IStmt ifStmt = new IfStmt(new ValueExp(new BoolValue(false)), thenStmt, elseStmt);
        ifStmt.execute(state);
        assertEquals(elseStmt, state.getExeStack().pop());
    }

    @Test
    void nonBooleanConditionThrows() {
        IStmt ifStmt = new IfStmt(new ValueExp(new IntValue(10)), new NopStmt(), new NopStmt());
        assertThrows(StatementException.class, () -> ifStmt.execute(state));
    }
}