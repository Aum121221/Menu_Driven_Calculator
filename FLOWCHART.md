┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [0] PROGRAM SETUP — Blueprint & Context                                                   │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Prepare environment, global state, and operation definitions.                    │  
│ Explanation:                                                                             │  
│  - Create a single Scanner to read console input.                                        │  
│  - Store last calculation and a flag to allow "ans" reuse.                               │  
│  - Define Operation enum with code + description and a lookup method.                    │  
│ Elements Used:                                                                           │  
│  - import java.util.Scanner                                                              │  
│  - static Scanner scanner = new Scanner(System.in)                                       │  
│  - static double lastResult; static boolean hasLastResult                                 │  
│  - enum Operation { ADD(...), ... } (fields: code:int, description:String)               │  
│  - Operation constructor, getCode(), getDescription(), static fromCode(int)               │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [1] MAIN PROGRAM CYCLE — main()                                                            │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Repeatedly show menu, read choice, get input, compute, show result, until EXIT.   │  
│ Explanation:                                                                              │  
│  - Welcome message.                                                                        │  
│  - Loop: show menu, get validated choice, map to Operation, handle EXIT, read numbers,    │  
│    calculate, show/store result, repeat.                                                   │  
│ Elements Used:                                                                             │  
│  - public static void main(String[] args)                                                  │  
│  - while(true) main loop                                                                    │  
│  - Method calls: printMenu(), getMenuChoice(), getNumber(prompt), calculate(...)           │  
│  - Operation op = Operation.fromCode(choice)                                               │  
│  - if (op == Operation.EXIT) { break; }                                                    │  
│  - lastResult assignment; hasLastResult = true                                             │  
│  - scanner.close() at program end                                                           │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [2] SHOW MENU — printMenu()                                                                │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Present available operations clearly.                                            │  
│ Explanation:                                                                              │  
│  - Print header. Loop over enum constants and print each code + description.              │  
│  - Prompt user to enter an operation code.                                                │  
│ Elements Used:                                                                             │  
│  - void printMenu()                                                                        │  
│  - for (Operation op : Operation.values())                                                 │  
│  - op.getCode(), op.getDescription()                                                       │  
│  - System.out.println(), System.out.print()                                                │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [3] GET MENU CHOICE — getMenuChoice()                                                      │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Read input until a valid operation code is given.                                 │  
│ Explanation:                                                                              │  
│  - Loop: read a line, trim whitespace, try to convert to integer.                         │  
│  - If parse fails → ask again (invalid input).                                             │  
│  - If parse succeeds but no matching Operation → ask again (invalid option).               │  
│  - Return the valid numeric choice.                                                         │  
│ Elements Used:                                                                             │  
│  - int getMenuChoice()                                                                     │  
│  - scanner.nextLine() + .trim()                                                             │  
│  - Integer.parseInt(input)                                                                  │  
│  - try { ... } catch (NumberFormatException e)                                             │  
│  - Operation.fromCode(choice) to validate code                                               │  
│  - return choice                                                                            │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [4] GET NUMBER — getNumber(String prompt)                                                  │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Safely obtain a numeric value or reuse last result when requested.               │  
│ Explanation:                                                                              │  
│  - Print the prompt. Read input.                                                           │  
│  - If input == "ans" (case-insensitive) and hasLastResult → return lastResult.           │  
│  - If "ans" but no last result → inform and retry.                                        │  
│  - Otherwise, try parsing to double; on failure instruct and retry.                      │  
│ Elements Used:                                                                             │  
│  - double getNumber(String prompt)                                                         │  
│  - System.out.print(prompt)                                                                │  
│  - String input = scanner.nextLine().trim()                                                │  
│  - input.equalsIgnoreCase("ans") and hasLastResult boolean                                  │  
│  - Double.parseDouble(input) + try/catch NumberFormatException                              │  
│  - return parsed double or lastResult                                                      │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [5] CALCULATE RESULT — calculate(Operation op, double num1, double num2)                   │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Compute the mathematical result or detect domain errors.                         │  
│ Explanation:                                                                              │  
│  - Use operation mapping to decide which math to perform (add/sub/mul/div/mod/exp/sqrt).   │  
│  - For divide/modulus: check divisor != 0; if zero, print error and return null.          │  
│  - For sqrt: ensure non-negative input; else print error and return null.                 │  
│ Elements Used:                                                                             │  
│  - Double calculate(Operation op, double num1, double num2)                                │  
│  - switch (op) { case ADD, SUBTRACT, ... }                                                 │  
│  - arithmetic operators (+, -, *, /, %)                                                    │  
│  - Math.pow(num1, num2), Math.sqrt(num1)                                                   │  
│  - System.out.println(error messages)                                                      │  
│  - return Double (null on error)                                                            │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [6] ERROR HANDLING — Integrated and Centralized                                            │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Keep program safe and user-friendly by catching invalid situations.              │  
│ Explanation:                                                                              │  
│  - Parse errors handled where parsing happens (menu & numbers).                           │  
│  - Logical/domain errors handled in calculation.                                          │  
│  - On any detected error, print friendly message and loop back to the appropriate input.  │  
│ Elements Used:                                                                             │  
│  - try/catch (NumberFormatException)                                                       │  
│  - conditional checks (if num2 == 0, if num1 < 0, if op == null)                          │  
│  - re-prompt loops (while(true) constructs in getMenuChoice/getNumber)                    │  
│  - returning null from calculate() to indicate failure                                     │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [7] STATE UPDATE & DISPLAY                                                                 │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: Show results and remember them for "ans" reuse.                                   │  
│ Explanation:                                                                              │  
│  - If calculate returned a non-null Double, print it, assign to lastResult, and set flag.  │  
│ Elements Used:                                                                             │  
│  - System.out.println("Result: " + result)                                                 │  
│  - lastResult = result; hasLastResult = true                                                │  
└───────────────────────────────────────────────────────────────────────────────────────────┘  
                 │  
                 ▼  
┌───────────────────────────────────────────────────────────────────────────────────────────┐  
│ [8] PROGRAM TERMINATION — CLEANUP & EXIT                                                   │  
├───────────────────────────────────────────────────────────────────────────────────────────┤  
│ Purpose: End program cleanly when user chooses EXIT.                                      │  
│ Explanation:                                                                              │  
│  - Print goodbye. Close Scanner. Break out of main loop and end program.                  │  
│ Elements Used:                                                                             │  
│  - System.out.println("Goodbye!")                                                          │  
│  - scanner.close()                                                                         │  
│  - break; (exit from while loop)                                                           │  
└───────────────────────────────────────────────────────────────────────────────────────────┘
