package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RefTypeTest {
    @Test
    public void nestedRefTypeIsEqual(){
        assertEquals(new RefType(new RefType(new IntType())),new RefType(new RefType(new IntType())));
    }

    @Test
    public void nestedRefTypeIsNotEqualToUnnested(){
        assertNotEquals(new RefType(new RefType(new IntType())),new RefType(new IntType()));
    }

    @Test
    public void variableGetsDefaultRefValue() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        IStmt decl = new VarDeclStmt(new RefType(new IntType()), "v");
        decl.execute(state);
        assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("v"));
    }
}
