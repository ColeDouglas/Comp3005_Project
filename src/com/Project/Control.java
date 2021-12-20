package com.Project;

/* This controls the Application states
 *
 */

import java.util.Scanner;

public class Control {

    ///////////// Class variables /////////////

    private final View view = new View();  /// This is the screen


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
            }else if(choice.equals("b")){
                view.register(SCANNER);
            }
            else {
                // Make sure the user is in the database
                String user = view.authenticate(SCANNER);
                if(user.equals("-1")){
                    continue;
                }
                if(user.equals("owner")){
                    // open the owner page
                    owner(SCANNER);
                }else{
                    // Open the user page
                    if (user.equals("guest")) {
                        System.out.println( "!! WARNING !!");
                        System.out.println( ">> YOU ARE LOGGING IS AS A GUEST");
                        System.out.println( ">> AND WILL NOT HAVE FULL FUNCTIONALITY ");

                    }
                    user(user,SCANNER);
                }
            }
        }
        System.out.println("\n" + ">> Thank you for shopping at ");
        System.out.println("\n" + ">>    A Bouquet of Proses"    );
        SCANNER.close();
    }

    // This is the Menu for the user
    private void user(String user, Scanner SCANNER) {
        boolean leaving = false;

        Boolean isGuest = user.equals("guest");
        System.out.println(">> Successfully logged in as user " + user);

        // user stuff here
        while(!leaving) {
            // basic menu for basic bitches
            String choice = view.userMenu(user, SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;
            }else {
                // print all books
                if(choice.equals("a")){
                    view.getBooks(SCANNER);

                // Find a book
                }else if (choice.equals("b")){
                    view.bookSearch(SCANNER);


                // See my Orders
                } else if (choice.equals("c")&&!isGuest){
                    PostgresHandler.getShipmentsByCustomer(user);

                // See the progress of one order
                } else if (choice.equals("d")&&!isGuest) {
                    view.getAllShipmentsByNumber(SCANNER);

                // Make a new order
                } else if (choice.equals("e")&&!isGuest) {
                    checkoutMenu(user, SCANNER);

                }else{
                    // Just Fucking leave
                }
            }
        }
    }

    // this is the Menu for the owner
    private void owner(Scanner SCANNER) {
        boolean leaving = false;
        System.out.println(">> Successfully logged in as owner");

        while(!leaving) {
            // show login
            String choice = view.ownerMenu(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;
            }else {

                // Deal with the customer objects
                if(choice.equals("a")){
                    // print all books
                    manageCustomers(SCANNER);

                // Deal with books
                }else if (choice.equals("b")){
                    manageBooks(SCANNER);

                // Deal with authors
                }else if (choice.equals("c")){
                    manageAuthors(SCANNER);

                // Deal with publishers
                }else if (choice.equals("d")){
                    managePublishers(SCANNER);

                // Deal with Orders
                }else if (choice.equals("e")){
                    manageOrders(SCANNER);

                // See Sales Report
                }else if (choice.equals("f")) {
                    manageSalesReport(SCANNER);

                }else{
                    // Just Fucking leave
                }
            }
        }
    }

    //////  Sub-menu's for readability  //////

    // I did what I could but this process is if-else for days...

    private void manageCustomers(Scanner SCANNER){
        boolean leaving = false;

        while(!leaving) {
            // show login
            String choice = view.manageCustomers(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;

            }else {
                // Print all customers
                if(choice.equals("a")){
                    view.getCustomers(SCANNER);

                //  Delete users
                }else if (choice.equals("b")){
                    view.removeCustomers(SCANNER);

                }else{
                    // Just Fucking leave
                }
            }
        }
    }

    private void manageBooks(Scanner SCANNER){
        boolean leaving = false;

        while(!leaving) {
            // show login
            String choice = view.manageBooks(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;

            }else {
                //  Print all customers
                if(choice.equals("a")){
                    view.getBooks(SCANNER);

                //  Delete Books
                } else if (choice.equals("b")){
                    view.bookSearch(SCANNER);

                //  Delete Books
                } else if (choice.equals("c")){
                    view.removeBook(SCANNER);

                //  Create Books
                } else if (choice.equals("d")){
                    view.createBook(SCANNER);

                //  Delete Books
                } else if (choice.equals("e")){
                    view.orderBook(SCANNER);

                } else {
                    // Just Fucking leave
                }
            }
        }
    }

    private void manageAuthors(Scanner SCANNER){
        boolean leaving = false;

        while(!leaving) {
            // show login
            String choice = view.manageAuthors(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;

            }else {
                // Print all authors
                if(choice.equals("a")){
                    view.getAuthors(SCANNER);

                //  delete an author
                }else if (choice.equals("b")){
                     view.deleteAuthor(SCANNER);

                //  create an author
                }else if (choice.equals("c")){
                     view.createAuthor(SCANNER);

                }else{
                    // Just Fucking leave
                }
            }
        }
    }

    private void managePublishers(Scanner SCANNER){
        boolean leaving = false;

        while(!leaving) {
            // show login
            String choice = view.managePublishers(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;

            }else {
                // Print all publishers
                if(choice.equals("a")){
                    view.getPublishers(SCANNER);

                //  delete a publisher
                }else if (choice.equals("b")){
                    view.deletePublisher(SCANNER);

                // create a publisher
                }else if (choice.equals("c")){
                    view.createPublisher(SCANNER);

                }else{
                    // Just Fucking leave
                }
            }
        }
    }

    private void manageSalesReport(Scanner SCANNER){
        boolean leaving = false;

        while(!leaving) {
            String choice = view.manageSalesReports(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;

            }else {
                // Show sales by genre
                if(choice.equals("a")){
                    view.showSalesReport(SCANNER, "Genre");

                // Show sales by author
                }else if (choice.equals("b")){
                    view.showSalesReport(SCANNER,"Author");

                // Show sales by Publisher
                }else if (choice.equals("c")){
                    view.showSalesReport(SCANNER,"Publisher");

                }else{
                    // Just Fucking leave
                }
            }
        }
    }

    private void manageOrders(Scanner SCANNER) {
        boolean leaving = false;

        while (!leaving) {
            String choice = view.manageOrders(SCANNER);

            //if log out or scanner error
            if (choice.equals("z")) {
                leaving = true;

            } else {
                // Show all orders
                if (choice.equals("a")) {
                    view.getAllShipments();

                // Get all shipments from one customer
                } else if (choice.equals("b")) {
                    view.getShipmentsByCustomer(SCANNER);


                // Get one shipment
                } else if (choice.equals("c")) {
                     view.getAllShipmentsByNumber(SCANNER);

                //remove one shipment
                } else if (choice.equals("d" )) {
                    view.removeShipment(SCANNER);

                 } else {
                    // Just Fucking leave
                }
            }
        }
    }

    private void checkoutMenu(String user, Scanner SCANNER) {
        int shippingInt = PostgresHandler.getNextShipmentNumber() + 1;
        System.out.println("Current shipment number: " + shippingInt);
        boolean leaving = false;

        while (!leaving) {
            String choice = view.checkoutMenu(SCANNER);

            // See Current Cart
            if (choice.equals("a")) {
                view.seeCart(shippingInt);

            // Add to cart
            }else if (choice.equals("b")) {
                view.addToCart(user, SCANNER, shippingInt);

            // checkout
            } else if (choice.equals("c")) {
                view.checkoutCart(SCANNER, user, shippingInt);
                leaving = true;

            //delete cart
            } else if (choice.equals("d")) {
                view.deleteCart(shippingInt);
                leaving = true;

            } else {
                    // Just fucking stay
            }
        }
    }
}

