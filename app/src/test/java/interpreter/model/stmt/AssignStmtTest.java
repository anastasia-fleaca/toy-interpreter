package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void assignValueToDeclaredVariable() throws MyException {
        new VarDeclStmt(new IntType(), "v").execute(state);
        IStmt assign = new AssignStmt("v", new ValueExp(new IntValue(10)));
        assign.execute(state);
        assertEquals(new IntValue(10), state.getSymTable().lookup("v"));
    }

    @Test
    void assignToUndeclaredVariableThrows() {
        IStmt assign = new AssignStmt("x", new ValueExp(new IntValue(5)));
        assertThrows(MyException.class, () -> assign.execute(state));
    }

    @Test
    void assignTypeMismatchThrows() throws MyException {
        new VarDeclStmt(new IntType(), "v").execute(state);
        IStmt assignBoolToInt = new AssignStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> assignBoolToInt.execute(state));
    }
}