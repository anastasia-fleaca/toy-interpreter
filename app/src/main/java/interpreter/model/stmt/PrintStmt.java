package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IList;
import interpreter.model.exp.IExp;
import interpreter.model.value.IValue;

public record PrintStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IList<IValue> out = state.getOut();
        IValue val = exp.eval(symTable);
        out.add(val);
        return state;
    }
    @Override public String toString() { return "print "+exp; }
}
