package com.Project;

import java.util.Scanner; // this is the I/O system

/* This is what the user sees
 * This way the user input stuff can be kept in one place.
 */


public class View {

    ///////////// Class Methods /////////////

    //The login screen
    public String login(Scanner scanner) {
        boolean checked = false;
        String input= "";


        while(!checked) {
            System.out.println("\n"+">> Would you like to login?");
            System.out.println("\n"+">> (a) login");
            System.out.println(">> (z) Exit");

            // get the user input
            input = scanner.nextLine();


            //check the value
            if (input.equals("a") || input.equals("z")){
                checked = true;
            }
            else{
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    // This is to get the user's profile based on their name
    //
    public String authenticate(Scanner scanner) {
        int output = -1;
        Boolean checked = false;
        String input = "";


        while(!checked) {
            Boolean valid = false;
            while(!valid) {
                System.out.println("\n" + ">> Please input your numeric UserID");
                System.out.println(">> or press z to exit");
                System.out.println(">> Hint: Owner is 0, users are 1+");

                input = scanner.nextLine();

                // Check input is alpha numeric
                if (isNumeric(input) || input.equals("z")) {

                    // Leave before querying
                    if (input.equals("z")) {
                        return input;
                    } else {
                        valid = true;
                    }
                }else {
                    System.out.println("Please use a valid input");
                }
            }

            ///Check the database
            input = PostgresHandler.getName(input);


            if(input.equals("-1")){
                System.out.println("User not found");
            }else if(input.equals("0")){
                checked = true;
                return "owner";
            }else{
                checked = true;
            }
        }
        return input;
    }

    // This is the basic owner menu
    public int ownerMenu(){
        /*
        final Scanner SCANNER = new Scanner(System.in);
        System.out.println(">> Welcome to the ");
        System.out.println(">> (1) login");
        System.out.println(">> (0) Exit");

        int intput = SCANNER.nextInt();
        SCANNER.close();

        return intput;
        */
        return -1;
    }

    ///////////// Helper Methods /////////////

    /// Check if a user's input is a number
    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

