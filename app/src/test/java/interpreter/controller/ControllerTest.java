package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyHeap;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo,false).allStep(); // display off
        return state;
    }

    @Test
    void example1PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX1);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void example2PrintsEighteen() throws MyException {
        PrgState state = run(Examples.EX2);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    void example3PrintsTwo() throws MyException {
        PrgState state = run(Examples.EX3);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    void oneStepOnEmptyStackThrowsException() throws MyException {
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), new NopStmt());
        state.getExeStack().pop();
        Controller controller = new Controller(new Repository(state, dir.resolve("empty.log").toString()), false);
        assertThrows(MyException.class, () -> {
            controller.oneStep(state);
        });
    }

    @Test
    void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);
        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
                c = log.indexOf("Out:"), d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");
        PrgState s = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    void fileExampleWithEmptyFileReadsZerosAndClosesFileTable() throws Exception {
        Path in = dir.resolve("empty.in");
        Files.writeString(in, "");
        PrgState state = run(Examples.fileExample(in.toString()));
        assertEquals(new IntValue(0), state.getOut().get(0));
        assertEquals(new IntValue(0), state.getOut().get(1));
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void ex5EvaluatesRelationalExpressionAndPrintsFive() throws MyException {
        PrgState state = run(Examples.EX5);
        assertEquals(1, state.getOut().toList().size());
        assertEquals(new IntValue(5), state.getOut().get(0));
    }

    @Test
    void ex6AllocatesAndReadsHeap() throws MyException {
        PrgState state = run(Examples.EX6);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().toList().size());
        assertEquals(new IntValue(20), state.getOut().get(0));
    }

    @Test
    void ex7PrintsNestedReferenceValues() throws MyException {
        PrgState state = run(Examples.EX7);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(2, state.getOut().toList().size());
        assertEquals(new RefValue(1, new IntType()), state.getOut().get(0));
        assertEquals(new RefValue(2, new RefType(new IntType())), state.getOut().get(1));
    }

    @Test
    void ex8ReadsAndUpdatesHeap() throws MyException {
        PrgState state = run(Examples.EX8);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(2, state.getOut().toList().size());
        assertEquals(new IntValue(20), state.getOut().get(0));
        assertEquals(new IntValue(35), state.getOut().get(1));
    }

    @Test
    void ex9PreservesTransitivelyReferencedHeapValue() throws MyException {
        PrgState state = run(Examples.EX9);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().toList().size());
        assertEquals(new IntValue(20), state.getOut().get(0));
    }

    @Test
    void ex10WhileLoopCountsDownToZero() throws MyException {
        PrgState state = run(Examples.EX10);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(5, state.getOut().toList().size());
        assertEquals(new IntValue(4), state.getOut().get(0));
        assertEquals(new IntValue(3), state.getOut().get(1));
        assertEquals(new IntValue(2), state.getOut().get(2));
        assertEquals(new IntValue(1), state.getOut().get(3));
        assertEquals(new IntValue(0), state.getOut().get(4));
    }
}

