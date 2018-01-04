/**
 * Program Name : Testing.java
 * Discussion :    Boolean Algebra
 * Written By :    Chungchhay Kuoch
 * Date :          2015 09 21
 */

package truthtable;
import java.util.*;
import java.util.Scanner;

public class TruthTable {

    class Stack {
        private final String stackArray[];
        private int a;
        private final int maxSize;

        public Stack(int max) {
            maxSize = max;
            stackArray = new String[maxSize];
            a = -1;
        }

        void push(String s) {
            stackArray[++a] = s;
        }

        void push(char c) {
            stackArray[++a] = Character.toString(c);
        }

        String pop() {
            return stackArray[a--];
        }

        public boolean isEmpty() {
            return (a == -1);
        }
    }
    
    private String input = "";
    private Stack stack1;
    private String output = "";
    private String output1 = "";
    public static int letterCount = 0;
    public static int specialLetterCount = 0;
    int [] ary;
    Set vars = new HashSet();
    String str = "";

 
    public TruthTable(String in) {
        int i = 0;
        input = in;

        char [] ch = input.toCharArray();
        for (char c : ch) {
            if (Character.isLetter(c)) {
                i++;
                vars.add(c);
                str = str + c;
            }
        }

        specialLetterCount = vars.size();
        letterCount = i;
        stack1 = new Stack(input.length());
        ary = new int[letterCount];
    }

    public String convertion() {
        for (String s : input.split(" ")) {
            switch (s) {
                case "+":
                    gotOperator(s, 2);
                    break;
                case "-":
                    gotOperator(s, 4);
                    break;
                case "*":
                    gotOperator(s, 3);
                    break;
                case "<": case ">":
                    gotOperator(s, 1);
                    break;
                case "(":
                    stack1.push(s);
                    break;
                case ")":
                    gotParenthesis(s);
                    break;
                default:
                    output = output + s + " ";
            }
        }

        while (!stack1.isEmpty()) {
            output += stack1.pop() + " ";
        }

        return output;
    }

    public void gotOperator(String opThis, int prec1) {
        while (!stack1.isEmpty()) {
            String opTop = stack1.pop();
            if (opTop.equals("(")) {
                stack1.push(opTop);
                break;
            }   else {
                int prec2;
                if (opTop.equals("+")) {
                    prec2 = 2;
                }   else if (opTop.equals("*")) {
                    prec2 = 3;
                }   else if (opTop.equals("<") || opTop.equals(">")) {
                    prec2 = 1;
                }   else {
                    prec2 = 4;
                }
                
                if (prec2 < prec1) {
                    stack1.push(opTop);
                    break;
                }   else{
                    output = output + opTop + " ";
                }
        }
        }

        stack1.push(opThis);
    }

    public void gotParenthesis(String s) {
        while (!stack1.isEmpty()) {
            String iString = stack1.pop();
            if (iString.equals("(")) {
                break;
            }   else {
                output = output + iString + " ";
            }
        }
    }

    public boolean operator(String s) {
        return s.equals("+") || s.equals("*") || s.equals(">") || s.equals("<");
    }

    public boolean operator(char c) {
        return c == '-' || c == '+' || c == '*' || c == '>' || c == '<';
    }

    public String printBinaryFormat(int number){
        String binary = "";
        int j = 0;
        int digitNumber = 0;
        int originalNumber = 0;
        String s = "";
        
        if (number == 0) {
            for (int i = 0; i < letterCount; i++)
                binary = binary + 0 + " ";
            return binary;
        }

        while (number != 0) {
            binary = (number % 2) + " " + binary;
            number /= 2;
        }

        for (String s1 : binary.split("")) {
            if (s1.equals("0") || s1.equals("1")) {
                s = s + s1;
            }
        }

        originalNumber = Integer.parseInt(s);

        do {
            digitNumber++;
            originalNumber /= 10;
        } while (originalNumber != 0);
        
        if (digitNumber < letterCount) {
            s = "";
            while (j < specialLetterCount - digitNumber) {
                s = s + 0 + " ";
                j++;
            }

            binary = s + binary;
            s = "";
            for (String s1 : binary.split("")) {
                if (s1.equals("0") || s1.equals("1")) {
                    s = s + s1;
                }
            }

            if (specialLetterCount < letterCount) {
                for (int i = specialLetterCount; i < letterCount; i++) {
                    for (int k = 0; k < letterCount; k++) {
                        if (str.charAt(k) == str.charAt(i) && k < s.length()) {
                            binary = binary + s.charAt(k) + " ";
                        }
                    }
                }
            }
        }
        return binary;
    }

    public void pushToStack(String num) {
        int count = 0;
        int countChar = 0;
        char [] c = output.toCharArray();
        for (String s : num.split(" ")) {
            ary[count] = Integer.parseInt(s);
            count++;
        }

        count = 0;

        for (int i = 0; i < c.length; i += 2) {
            if (operator(c[i])) {
                output1 = output1 + c[i] + " ";
            }   else if (!operator(c[i])) {
                output1 = output1 + ary[count] + " ";
                count++;
                c[i] = ' ';
            }
        }
    }

    public void evaluate() {
        int N = 2;
        int [] ary = new int[N];
        String iString;
        int result;
        int count = 0;
        int tempCount = 0;
        char [] binary = new char[letterCount];

        for (String s : output1.split(" ")) {
            if (s.equals("0") || s.equals("1")) {
                binary[count] = s.charAt(0);
                count++;
            }

            if (operator(s)) {
                ary[N - 2] = Integer.parseInt(stack1.pop());
                ary[N - 1] = Integer.parseInt(stack1.pop());
                result = Evaluation(s, ary[N - 1], ary[N - 2]);
                iString = String.valueOf(result);
                stack1.push(iString);
            }   else if (s.equals("-")) {
                ary[N - 2] = Integer.parseInt(stack1.pop());
                result = 1 - ary[0];
                iString = String.valueOf(result);
                stack1.push(iString);
                if (result == 0) {
                    binary[tempCount - 1] = '0';
                }   else if (result == 1) {
                    binary[tempCount - 1] = '1';
                }
            }   else {
                tempCount++;
                stack1.push(s);
            }
        }

        for (char c : binary) {
            System.out.print(c + " | ");
        }
        
        output1 = "";
        System.out.println("--> " + stack1.pop());
    }

    public int Evaluation(String s, int a, int b) {
        int result = 0;
        switch (s) {
            case "+":
                result = a + b;
                result = (result == 2 || result == 1) ? 1 : 0;
                break;
            case "*":
                result = a * b;
                break;
            case "<":
                if (a == b)
                    result = 1;
                else
                    result = 0;
                break;
            case ">":
                if ((a == 1) && (b == 0))
                    result = 0;
                else
                    result = 1;
                break;
            default:
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        TruthTable ex = new TruthTable(s);
        System.out.println("Given : \n" + s);
        String output = ex.convertion();
        for (int i = 0; i < Math.pow(2, TruthTable.specialLetterCount); i++) {
            String binary = ex.printBinaryFormat(i);
            ex.pushToStack(binary);
            ex.evaluate();
       }
    }
    
}
