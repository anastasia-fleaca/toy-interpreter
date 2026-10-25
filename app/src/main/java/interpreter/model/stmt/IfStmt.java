package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.IStack;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements  IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDictionary<String, IValue> symTable = state.getSymTable();
        IStack<IStmt> exeStack = state.getExeStack();
        IValue c = cond.eval(symTable);
        if (!(c instanceof BoolValue(boolean isTrue))) throw new StatementException("condition in if statement is not boolean");
        if(isTrue) exeStack.push(thenS);
        else exeStack.push(elseS);
        return state;
    }
    @Override public String toString() { return "if "+cond.toString()+" then "+thenS.toString()+" else "+elseS.toString(); }
}
