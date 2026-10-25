package interpreter.model.stmt;

import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public record VarDeclStmt(IType type, String id) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws StatementException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        if(symTable.isDefined(id)) throw new StatementException("this variable is already defined");
        symTable.put(id,type.defaultValue());
        return state;
    }
    @Override public String toString() { return type + " " + id; }
}
