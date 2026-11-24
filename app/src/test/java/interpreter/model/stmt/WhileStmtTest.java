package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.*;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WhileStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
    }

    private void run(IStmt program) throws MyException {
        state = PrgState.of(program);
        while (!state.getExeStack().isEmpty()) {
            state.getExeStack().pop().execute(state);
        }
    }

    @Test
    void whileRunsZeroTimes() throws MyException {
        IStmt program = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(1))),
                        new WhileStmt(
                                new RelationalExp(RelOp.LT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))
                        )
                )
        );
        run(program);
        assertEquals(new IntValue(1), state.getSymTable().lookup("v"));
    }

    @Test
    void whileRunsOneTime() throws MyException {
        IStmt program = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(1))),
                        new WhileStmt(
                                new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))
                        )
                )
        );
        run(program);
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void whileRunsManyTimes() throws MyException {
        IStmt program = new CompStmt(
                new VarDeclStmt(new IntType(), "v"),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(5))),
                        new WhileStmt(
                                new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1))))
                        )
                )
        );
        run(program);
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    void whileWithANonBooleanConditionThrows() {
        var loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        assertThrows(StatementException.class, () -> loop.execute(state));
    }
}
