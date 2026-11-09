package interpreter.model.stmt;

import static org.junit.jupiter.api.Assertions.*;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
// + imports of the interpreter classes
class FileStmtTest {
    @TempDir
    Path dir; // a new empty folder for every test
    private PrgState state;
    private ValueExp file; // the expression "<dir>/numbers.in"
    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        file = new ValueExp(new StringValue(f.toString()));
        state = PrgState.of(new NopStmt());
        new VarDeclStmt(new IntType(), "x").execute(state);
    }
    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }
    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(file).execute(state));
    }
    @Test
    void openAddsFileToTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        StringValue fileVal = (StringValue) file.eval(state.getSymTable());
        assertTrue(state.getFileTable().isDefined(fileVal));
    }
    @Test
    void openingNonStringFileThrows() {
        ValueExp fakefile = new ValueExp(new IntValue(8));
        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(fakefile).execute(state));
    }
    @Test
    void openingMissingFileThrows() {
        ValueExp missingFile = new ValueExp(new StringValue(dir.resolve("missing.in").toString()));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(missingFile).execute(state));
    }
    @Test
    void readFileVariableNotDeclaredThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () ->
                new ReadFileStmt(file, "undeclaredVar").execute(state)
        );
    }
    @Test
    void variableNonIntegerVariableThrows() throws MyException {
        new VarDeclStmt(new BoolType(), "b").execute(state);
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () ->
                new ReadFileStmt(file, "b").execute(state)
        );
    }
    @Test
    void readingFromNonOpenFileThrows() throws IOException {
        Path f = dir.resolve("fakenumbers.in");
        Files.writeString(f, "7\n");
        ValueExp fakefile = new ValueExp(new StringValue(f.toString()));
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(fakefile, "x").execute(state));
    }
    @Test
    void readingNonIntegerVariableThrows() throws MyException, IOException {
        Path badFile = dir.resolve("bad_number.in");
        Files.writeString(badFile, "not_a_number\n");
        ValueExp badFileExp = new ValueExp(new StringValue(badFile.toString()));
        new OpenRFileStmt(badFileExp).execute(state);
        assertThrows(StatementException.class, () ->
                new ReadFileStmt(badFileExp, "x").execute(state)
        );
    }
    @Test
    void closingFileRemovesFromFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);
        StringValue fileVal = (StringValue) file.eval(state.getSymTable());
        assertFalse(state.getFileTable().isDefined(fileVal));
    }
    @Test
    void closingNotOpenFileThrows() throws MyException, IOException {
        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(file).execute(state));
    }
}