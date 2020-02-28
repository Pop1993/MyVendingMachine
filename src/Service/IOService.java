package Service;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IOService {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMessage (String message) {
        System.out.println(message);
    }

    //reads user input. throws exception if input is not of type int
    public static int readUserInput () {
        int userInput = 0;
        do {
            try {
                System.out.print("Input: ");
                userInput = scanner.nextInt();
            } catch (InputMismatchException exception) {
                displayMessage("Input mismatch exception. Try again...");
            }
            scanner.nextLine(); // clears the buffer
        } while (userInput <= 0);

        return userInput;
    }

    //adds spaces after words, so that the displayed stock is organized
    public static String addSpaces(String text) {
        String result = text;

        if(result.length() < 15) {
            int numberOfSpacesToBeAdded = 15 - result.length();

            for (int index = 0; index < numberOfSpacesToBeAdded; index++) {
                result += " ";
            }
        }
        return result;
    }

    //adds a line made of equals sign. Emulates retro vibe
    public static void addLineBreak (int howManyLines) {
        String result = "";
        for (int index = 0; index < 75; index++) {
            result += "=";
        }
        for (int index = 0; index < howManyLines; index++) {
            displayMessage(result);
        }
    }
}
