package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.model.stmt.NopStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.Scanner;

public class View {
    public void start() {
        Scanner scanner = new Scanner(System.in);
        Controller currentController = null;

        label:
        while (true) {
            System.out.println("\ntoy interpreter menu");
            System.out.println("1. select a program");
            System.out.println("2. sun the selected program");
            System.out.println("0. exit");
            System.out.print("choose an option: ");

            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1":
                        currentController = selectProgram(scanner);
                        break;
                    case "2":
                        if (currentController == null) {
                            System.out.println("select a program first!");
                        } else {
                            currentController.allStep();
                        }
                        break;
                    case "0":
                        break label;
                    default:
                        System.out.println("invalid option");
                        break;
                }
            } catch (MyException e) {
                System.out.println("interpreter error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("unexpected error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static Controller selectProgram(Scanner scanner) {
        System.out.println("1. int v; v = 2; print(v)");
        System.out.println("2. int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)");
        System.out.println("3. bool a; int v; a = true; (if a then v = 2 else v = 3); print(v)");
        System.out.print("select program number: ");

        String progOption = scanner.nextLine();
        IStmt selectedProgram;

        switch (progOption) {
            case "1" -> selectedProgram = Examples.EX1;
            case "2" -> selectedProgram = Examples.EX2;
            case "3" -> selectedProgram = Examples.EX3;
            default -> {
                System.out.println("invalid program choice.");
                selectedProgram = new NopStmt();
            }
        }

        PrgState prgState = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), selectedProgram);
        IRepository repo = new Repository(prgState);
        return new Controller(repo, true);
    }
}