import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class CowInterpreter {
    private String[] src;
    private int memPtr;
    private int[] mem;

    int i;
    private ArrayList<Integer> bracketsPosition = new ArrayList<Integer>();

    private Integer register;

    public CowInterpreter() {
        init();
        getValues();
        checkSyntax();
        handleCommand();
    }

    private void getValues() {
        System.out.println("Code:");
        Scanner sc = new Scanner(System.in);
        src = sc.nextLine().split("\\s+");
    }

    private void handleCommand() {
        for (i = 0; i < src.length; i++) {
            switch (src[i]) {
                case "moO":
                    moO();
                    break;
                case "mOo":
                    mOo();
                    break;
                case "OOO":
                    OOO();
                    break;
                case "oom":
                    oom();
                    break;
                case "OOM":
                    OOM();
                    break;
                case "MoO":
                    MoO();
                    break;
                case "MOo":
                    MOo();
                    break;
                case "MMM":
                    MMM();
                    break;
                case "Moo":
                    Moo();
                    break;
                case "moo":
                    moo();
                    break;
                case "mOO":
                    mOO();
                    break;
                case "MOO":
                    MOO();
                    break;
                default:
                    throw new IllegalArgumentException("This is not a valid COW program!");
            }
        }
    }

    public void checkSyntax() {
        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0; i < src.length; i++) {
            switch (src[i]) {
                case "MOO":
                    stack.add(i);
                    bracketsPosition.add(500);
                    break;
                case "moo":
                    if (stack.empty()) {
                        throw new IllegalArgumentException("Stack is empty");
                    } else {
                        int position = stack.pop();
                        bracketsPosition.add(position);
                        bracketsPosition.remove(position);
                        bracketsPosition.add(position, i);
                    }
                    break;
                default:
                    bracketsPosition.add(500);
                    break;
            }
        }
    }

    public void init() {
        register = null;
        mem = new int[255];
        memPtr = 0;
    }

    //JA
    private void MOo() {
        mem[memPtr]--;
    }

    //JA
    private void MoO() {
        mem[memPtr]++;
    }

    //Ja
    private void OOM() {
        writeToStdOut(mem[memPtr]);
    }

    //JA
    private void oom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("You Printed oom, please give in a integer");
        mem[memPtr] = scanner.nextInt();
    }

    //JA
    private void OOO() {
        mem[memPtr] = 0;
    }

    //JA
    private void moO() {
        memPtr++;
        if (mem.length < memPtr) {
            mem[memPtr] = 0;
        }
    }

    //JA
    private void mOo() {
        if (memPtr == 0) {
            throw new IllegalArgumentException("Failed to run!");
        } else {
            memPtr--;
        }
    }

    //JA
    private void Moo() {
        if (mem[memPtr] == 0) {
            mem[memPtr] = readAsciiFromStdIn();
        } else {
            writeAsciiToStdOut(mem[memPtr]);
        }
    }

    //testen
    private void MMM() {
        if (register == null) {
            register = mem[memPtr];
        } else {
            mem[memPtr] = register;
            register = null;
        }
    }

    //JA
    private void MOO() {
        if (mem[memPtr] == 0) {
            i = bracketsPosition.get(i);
        }
    }

    //JA
    private void moo() {
        i = bracketsPosition.get(i) - 1;
    }

    //JA
    private void mOO() {
        final int commandIndex = mem[memPtr];
        if (commandIndex == 3 || commandIndex < 0 || commandIndex > 11) {
            System.exit(0);
        }
        switch (commandIndex) {
            case 0:
                moo();
                break;
            case 1:
                mOo();
                break;
            case 2:
                moO();
                break;
            case 4:
                Moo();
                break;
            case 5:
                MOo();
                break;
            case 6:
                MoO();
                break;
            case 7:
                MOO();
                break;
            case 8:
                OOO();
                break;
            case 9:
                MMM();
                break;
            case 10:
                OOM();
                break;
            case 11:
                oom();
                break;
        }
    }

    private void writeToStdOut(final int value) {
        System.out.println(value);
    }

    private int readAsciiFromStdIn() {
        Scanner sc = new Scanner(System.in);
        String[] hdd = sc.nextLine().split("");

        return hdd[0].getBytes(StandardCharsets.US_ASCII)[0];
    }

    private void writeAsciiToStdOut(final int value) {
        System.out.println((char) value);
    }
}
