package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws ADTException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void addsDefaultValuesToSymTable() throws MyException {
        new VarDeclStmt(new IntType(), "v").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
        new VarDeclStmt(new BoolType(), "b").execute(state);
        assertEquals(new BoolValue(false), state.getSymTable().lookup("b"));
    }

    @Test
    void redeclaredVariableThrows() throws MyException {
        new VarDeclStmt(new IntType(), "v").execute(state);
        assertThrows(StatementException.class, () -> {
            new VarDeclStmt(new IntType(), "v").execute(state);
        });
    }
}