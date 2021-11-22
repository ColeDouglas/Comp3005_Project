package com.Project;

/* This controls the Application states
 *
 */

import java.util.Scanner;

public class Control {

    ///////////// Class variables /////////////

    private final View view = new View();  /// This is the scr


    ///////////// Class Methods /////////////

    // This is the method for launching the application
    public void launch() {
        String choice = "";
        boolean leaving= false;
        final Scanner SCANNER = new Scanner(System.in);

        System.out.println("\n" + ">>         Welcome to  ");
        System.out.println(">>    A Bouquet of Proses");

        while (!leaving) {
            // show login
            choice = view.login(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;
            }else {
                // Make sure the user is in the database
                String user = view.authenticate(SCANNER);
                if(user.equals("owner")){
                    // open the owner page
                    owner(SCANNER);
                }else{
                    // Open the user page
                    user(user,SCANNER);
                }
            }
        }
        System.out.println("\n" + ">> Thank you for shopping at ");
        System.out.println("\n" + ">>    A Bouquet of Proses"    );
        SCANNER.close();
    }

    // This is the page for the user
    private void user(String user, Scanner Scanner) {
        System.out.println(">> Successfully logged in as user");

        // user stuff here
        while(true) {
            return;
        }
    }

    // this is the page for the owner
    private void owner(Scanner Scanner) {
        System.out.println(">> Successfully logged in as owner");
        /// Owner stuff here
        while (true) {
            return;
        }
    }
}

