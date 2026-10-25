package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompStmtTest {

    @Test
    void compStmtPushesInCorrectOrder() throws MyException {
        IStmt stmt1 = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt stmt2 = new PrintStmt(new ValueExp(new IntValue(2)));
        IStmt comp = new CompStmt(stmt1, stmt2);
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
        comp.execute(state);
        assertEquals(stmt1, state.getExeStack().pop());
        assertEquals(stmt2, state.getExeStack().pop());
    }
}