package interpreter.repository;

import interpreter.model.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> prgStates;

    public Repository(PrgState initialPrgState) {
        this.prgStates = new ArrayList<PrgState>();
        this.prgStates.add(initialPrgState);
    }

    @Override
    public PrgState getPrgState() {
        return this.prgStates.get(0);
    }
}
