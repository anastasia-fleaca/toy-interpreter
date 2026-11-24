package interpreter.model.stmt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyHeap;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HeapStmtTest {
    private PrgState state;
    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt(new RefType(new IntType()), "v").execute(state); // Ref int v
    }
    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }
    @Test
    void newUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new NewStmt("x", new ValueExp(new IntValue(20))).execute(state));
    }

    @Test
    void newNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt(new IntType(), "n").execute(state);
        assertThrows(StatementException.class,
                () -> new NewStmt("n", new ValueExp(new IntValue(20))).execute(state));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        var exp = new ValueExp(new BoolValue(true));
        assertThrows(StatementException.class, () -> new NewStmt("v", exp).execute(state));
    }

    @Test
    void readHeapReturnsStoredValue() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        var exp = new ReadHeapExp(new VarExp("v"));
        assertEquals(new IntValue(20), exp.eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void readHeapNonReferenceThrows() {
        var exp = new ReadHeapExp(new ValueExp(new IntValue(10)));
        assertThrows(ExpressionException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void readHeapMissingAddressThrows() {
        var exp = new ReadHeapExp(new VarExp("v"));
        assertThrows(ExpressionException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void writeHeapUpdatesHeapLocation() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
    }

    @Test
    void writeHeapUndeclaredVariableThrows() {
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("x", new ValueExp(new IntValue(30))).execute(state));
    }

    @Test
    void writeHeapNonReferenceVariableThrows() throws MyException {
        new VarDeclStmt(new IntType(), "n").execute(state);
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("n", new ValueExp(new IntValue(30))).execute(state));
    }

    @Test
    void writeHeapMissingAddressThrows() {
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state));
    }

    @Test
    void writeHeapWrongTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertThrows(StatementException.class,
                () -> new WriteHeapStmt("v", new ValueExp(new BoolValue(true))).execute(state));
    }
}
