package interpreter.view;

import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;

public class Examples {

    public static final IStmt EX1 = new CompStmt(new VarDeclStmt(new IntType(), "v"),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));

    public static final IStmt EX2 = new CompStmt(new VarDeclStmt(new IntType(), "a"),
            new CompStmt(new VarDeclStmt(new IntType(), "b"),
                    new CompStmt(new AssignStmt("a",
                            new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                                    new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)),
                                            new ValueExp(new IntValue(5))))),
                            new CompStmt(new AssignStmt("b",
                                    new ArithExp(ArithOp.ADD, new VarExp("a"),
                                            new ValueExp(new IntValue(1)))),
                                    new PrintStmt(new VarExp("b"))))));

    public static final IStmt EX3 = new CompStmt(new VarDeclStmt(new BoolType(), "a"),
            new CompStmt(new VarDeclStmt(new IntType(), "v"),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(new IfStmt(new VarExp("a"),
                                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                    new PrintStmt(new VarExp("v"))))));

    public static IStmt fileExample(String fileName) {
        return new CompStmt(new VarDeclStmt(new StringType(), "varf"),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue(fileName))),
                        new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt(new IntType(), "varc"),
                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFileStmt(new VarExp("varf"))))))))));
    }
    public static final IStmt EX4 = fileExample("test4.in");

    public static final IStmt EX5 = new CompStmt(
            new VarDeclStmt(new IntType(), "a"),
            new CompStmt(
                    new VarDeclStmt(new IntType(), "b"),
                    new CompStmt(
                            new AssignStmt("a", new ValueExp(new IntValue(5))),
                            new CompStmt(
                                    new AssignStmt("b", new ValueExp(new IntValue(10))),
                                    new IfStmt(
                                            new RelationalExp(RelOp.LT, new VarExp("a"), new VarExp("b")),
                                            new PrintStmt(new VarExp("a")),
                                            new PrintStmt(new VarExp("b"))
                                    )
                            )
                    )
            )
    );

    public static final IStmt EX6 = new CompStmt(
            new VarDeclStmt(new RefType(new IntType()), "v"),
            new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new PrintStmt(new ReadHeapExp(new VarExp("v")))
            )
    );

    public static final IStmt EX7 = new CompStmt(
            new VarDeclStmt(new RefType(new IntType()), "v"),
            new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(
                            new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                            new CompStmt(
                                    new NewStmt("a", new VarExp("v")),
                                    new CompStmt(
                                            new PrintStmt(new VarExp("v")),
                                            new PrintStmt(new VarExp("a"))
                                    )
                            )
                    )
            )
    );

    public static final IStmt EX8 = new CompStmt(
            new VarDeclStmt(new RefType(new IntType()), "v"),
            new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(
                            new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                            new CompStmt(
                                    new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                    new PrintStmt(new ArithExp(
                                            ArithOp.ADD,
                                            new ReadHeapExp(new VarExp("v")),
                                            new ValueExp(new IntValue(5))
                                    ))
                            )
                    )
            )
    );

    public static final IStmt EX9 = new CompStmt(
            new VarDeclStmt(new RefType(new IntType()), "v"),
            new CompStmt(
                    new NewStmt("v", new ValueExp(new IntValue(20))),
                    new CompStmt(
                            new VarDeclStmt(new RefType(new RefType(new IntType())), "a"),
                            new CompStmt(
                                    new NewStmt("a", new VarExp("v")),
                                    new CompStmt(
                                            new NewStmt("v", new ValueExp(new IntValue(30))),
                                            new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
                                    )
                            )
                    )
            )
    );

    public static final IStmt EX10 =
            new CompStmt(new VarDeclStmt(new IntType(), "v"),
                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                            new CompStmt(new WhileStmt(
                                    new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                    new CompStmt(new PrintStmt(new VarExp("v")),
                                            new AssignStmt("v", new ArithExp(ArithOp.SUB,
                                                    new VarExp("v"), new ValueExp(new IntValue(1)))))),
                                    new PrintStmt(new VarExp("v")))));
}