package ui;

import java.util.Scanner;

public class UserIOConsoleImpl implements UserIO{
    public void print(String toPrint){
        /*
        Print a given String to the console.
        The String value displayed should be passed in as a parameter.
        */
        System.out.println(toPrint);
    }

    public String readString(String messageToGive){
        /*
        Display a given message String to prompt the user to enter in a String,
        then read in the user response as a String and return that value.
        The prompt message should be passed in as a parameter
        and the String value read in should be the return value of the method.
         */
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(messageToGive);
        return inputScanner.nextLine();
    }

    public int readInt(String messageToGive){
        /*
        Display a given message String to prompt the user to enter in an integer,
        then read in the user response and return that integer value.
        The prompt message value should be passed in as a parameter
        and the value that is read in should be the return of the method.
         */
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(messageToGive);
        return Integer.parseInt(inputScanner.nextLine());
    }


    public int readInt(String prompt, int min, int max){
        /*
        Display a prompt to the user to enter an integer between a specified min and max range,
        and read in an integer. If the user's number does not fall within the range,
        keep prompting the user for new input until it does.
        The prompt message and the min and max values should be passed in as parameters.
        The value read in from the console should be the return of the method.
         */
        int userNum = 0;
        boolean validInput = false;

        Scanner inputScanner = new Scanner(System.in);

        while (!validInput) {
            try {
                System.out.println(prompt);
                userNum = Integer.parseInt(inputScanner.nextLine());
                if (min <= userNum && userNum <= max){
                    validInput = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("The number must be an integer.");
            }
        }
        return userNum;
    }

    public double readDouble(String prompt){
        /*
        Display a given message String to prompt the user to enter in a double,
        then read in the user response and return that double value.
        The prompt message value should be passed in as a parameter and the value
        that is read in should be the return of the method.
         */
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(prompt);
        return Double.parseDouble(inputScanner.nextLine());
    }

    public double readDouble(String prompt, double min, double max){
        double userNum = 0;
        boolean validInput = false;

        Scanner inputScanner = new Scanner(System.in);

        while (!validInput) {
            try {
                System.out.println(prompt);
                userNum = Double.parseDouble(inputScanner.nextLine());
                if (min <= userNum && userNum <= max){
                    validInput = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("The number must be a double.");
            }
        }
        return userNum;
    }

    public float readFloat(String prompt){
        /*
        Display a given message String to prompt the user to enter a float
        and then read in the user response and return that float value.
        The prompt message value should be passed in as a parameter
        and the value that is read in should be the return of the method.
         */
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(prompt);
        return Float.parseFloat(inputScanner.nextLine());
    }

    public float readFloat(String prompt, float min, float max){
        float userNum = 0;
        boolean validInput = false;

        Scanner inputScanner = new Scanner(System.in);

        while (!validInput) {
            try {
                System.out.println(prompt);
                userNum = Float.parseFloat(inputScanner.nextLine());
                if (min <= userNum && userNum <= max){
                    validInput = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("The number must be a float.");
            }
        }
        return userNum;
    }

    public long readLong(String prompt){
        Scanner inputScanner = new Scanner(System.in);
        System.out.println(prompt);
        return Long.parseLong(inputScanner.nextLine());
    }

    public long readLong(String prompt, long min, long max){
        long userNum = 0;
        boolean validInput = false;

        Scanner inputScanner = new Scanner(System.in);

        while (!validInput) {
            try {
                System.out.println(prompt);
                userNum = Long.parseLong(inputScanner.nextLine());
                if (min <= userNum && userNum <= max){
                    validInput = true;
                }
            }
            catch (NumberFormatException e) {
                System.out.println("The number must be a float.");
            }
        }
        return userNum;
    }
}
