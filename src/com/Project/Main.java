package com.Project;


/*  A basic Java application for accessing and manipulating a bookstore database.
 *
 *  @author Cole Douglas.
 */

public class Main {
    public static void main(String[] args) {

        PostgresHandler.getName("0");

        final Control CONTROL = new Control(); // Start the program
        CONTROL.launch();
    }
}

/*  TBD-001 the login screen needs to check the database
 *
 *
 */