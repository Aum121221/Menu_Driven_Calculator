import java.util.Scanner;

public class MenuDrivenCalculator {

    static Scanner scanner = new Scanner(System.in);
    static double lastResult = 0;
    static boolean hasLastResult = false;

    // Enum for calculator operations
    enum Operation {
        ADD(1, "Add (+)"),
        SUBTRACT(2, "Subtract (-)"),
        MULTIPLY(3, "Multiply (*)"),
        DIVIDE(4, "Divide (/)"),
        MODULUS(5, "Modulus (%)"),
        EXPONENT(6, "Exponent (^ - power)"),
        SQRT(7, "Square Root (√)"),
        EXIT(8, "Exit");

        private final int code;
        private final String description;

        Operation(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static Operation fromCode(int code) {
            for (Operation op : values()) {
                if (op.getCode() == code) {
                    return op;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Enhanced Menu Driven Java Calculator ===");

        while (true) {
            printMenu();

            int choice = getMenuChoice();
            Operation operation = Operation.fromCode(choice);

            if (operation == Operation.EXIT) {
                System.out.println("Exiting calculator. Goodbye!");
                break;
            }

            double num1, num2 = 0;

            if (operation == Operation.SQRT) {
                num1 = getNumber(
                    "Enter number (or type 'ans' for last result "
                        + (hasLastResult ? lastResult : "N/A") + "): ");
            } else {
                num1 = getNumber(
                    "Enter first number"
                        + (hasLastResult ? " (or type 'ans' for last result " + lastResult + ")" : "")
                        + ": ");
                num2 = getNumber("Enter second number: ");
            }

            Double result = calculate(operation, num1, num2);

            if (result != null) {
                System.out.println("Result: " + result);
                lastResult = result;
                hasLastResult = true;
            }
        }

        scanner.close();
    }

    public static void printMenu() {
        System.out.println("\nMenu:");
        for (Operation op : Operation.values()) {
            System.out.println(op.getCode() + ") " + op.getDescription());
        }
        System.out.print("Choose an operation (1-" + Operation.values().length + "): ");
    }

    public static int getMenuChoice() {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (Operation.fromCode(choice) != null) {
                    return choice;
                } else {
                    System.out.print("Invalid option! Please select a valid number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input! Please enter a number: ");
            }
        }
    }

    public static double getNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("ans") && hasLastResult) {
                return lastResult;
            }
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number input! Please try again.");
            }
        }
    }

    public static Double calculate(Operation op, double num1, double num2) {
        switch (op) {
            case ADD:
                return num1 + num2;
            case SUBTRACT:
                return num1 - num2;
            case MULTIPLY:
                return num1 * num2;
            case DIVIDE:
                if (num2 == 0) {
                    System.out.println("Error: Division by zero is not allowed.");
                    return null;
                }
                return num1 / num2;
            case MODULUS:
                if (num2 == 0) {
                    System.out.println("Error: Modulus by zero is not allowed.");
                    return null;
                }
                return num1 % num2;
            case EXPONENT:
                return Math.pow(num1, num2);
            case SQRT:
                if (num1 < 0) {
                    System.out.println("Error: Square root of negative number is not allowed.");
                    return null;
                }
                return Math.sqrt(num1);
            default:
                return null;
        }
    }
}


