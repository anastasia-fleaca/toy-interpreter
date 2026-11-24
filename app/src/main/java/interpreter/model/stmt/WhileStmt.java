package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record WhileStmt(IExp cond, IStmt body) implements IStmt { // S13
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        IValue val = cond.eval(symTable,heap);
        if(!(val instanceof BoolValue b)) {
            throw new StatementException(cond+" is not a boolean value");
        }
        var exestack = state.getExeStack();
        if(b.val()) {
            exestack.push(this);
            exestack.push(body);
        }
        return state;
    }
    @Override public String toString() { return "(while (" + cond + ") " + body + ")"; }
}
