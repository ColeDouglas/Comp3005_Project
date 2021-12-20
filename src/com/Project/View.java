package com.Project;

import java.util.Scanner; // this is the I/O system

/* This is what the user sees
 * This way the user input stuff can be kept in one place.
 */


public class View {

    ///////////// Class Methods /////////////

    // The login screen
    public String login(Scanner scanner) {
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> Would you like to login?"+"\n");
            System.out.println(""+">> (a) Login");
            System.out.println(""+">> (b) Register");
            System.out.println(">> (z) Exit");

            // get the user input
            input = scanner.nextLine();


            //check the value
            if (input.equals("a") || input.equals("b") ||input.equals("z")){
                checked = true;
            }
            else{
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    // Registering a new user
    public String register(Scanner SCANNER){
        String input = "";
        System.out.println(">> What is the name you wish to register");
        input = SCANNER.nextLine();

        PostgresHandler.createCustomer(input);

        return "1";
    }

    // This is to get the user's profile based on their name
    public String authenticate(Scanner scanner) {
        int output = -1;
        Boolean checked = false;
        String input = "";


        while(!checked) {
            Boolean valid = false;
            while(!valid) {
                System.out.println("\n" + ">> Please input your numeric UserID");
                System.out.println(">> or press z to exit");
                System.out.println(">> Hint: Owner is 0, Guest is 1, Registered users are +2");

                input = scanner.nextLine();

                // Check input is alpha numeric
                if (isNumeric(input) || input.equals("z")) {

                    // Leave before querying
                    if (input.equals("z")) {
                        return "-1";
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
            } else if(input.equals("1")){
                checked = true;
                return "guest";
            }else {
                checked = true;
            }
        }
        return input;
    }


    ////////////  Owner Stuff ////////////

    // This is the basic owner menu
    public String ownerMenu(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) Manage Users");
            System.out.println(">> (b) Manage Books");
            System.out.println(">> (c) Manage Authors");
            System.out.println(">> (d) Manage Publishers");
            System.out.println(">> (e) Manage Orders");
            System.out.println(">> (f) See Sales Reports");
            System.out.println(">> (z) Log out");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") || input.equals("b") ||  input.equals("c") || input.equals("d") || input.equals("e") || input.equals("f") || input.equals("z")){
                checked = true;
            } else{
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }


    ////// Customer Object //////

    // This is for dealing with customer objects
    public String manageCustomers(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) See all Customers");
            System.out.println(">> (b) Remove a Customer");
            System.out.println(">> (z) Go Back");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") ||input.equals("b")|| input.equals("z")){
                checked = true;
            } else {
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    public String getCustomers(Scanner SCANNER){
        PostgresHandler.getCustomers();
        return "True";
    }

    public String removeCustomers(Scanner SCANNER){
        boolean finished = false;
        String input= "";

        while(!finished) {
            System.out.println("\n"+">> What is the name of the person being removed?"+"\n");

            // get the user input
            input = SCANNER.nextLine();

            String Output = PostgresHandler.deleteCustomer(input);

            if(Output.equals("-1")){
                System.out.println(">> No Such User");

                System.out.println("\n"+">> Would you like to try again?"+"\n");
                System.out.println(">> a) Yes");
                System.out.println(">> b) No");

                // get the user input
                input = SCANNER.nextLine();
                if(input.equals("b")) {
                    finished = true;
                }
            }else{
                finished = true;
            }
        }
        return input;
    }


    ////// Book Object //////

    // This is for dealing with customer objects
    public String manageBooks(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) See all books");
            System.out.println(">> (b) Search for a book");
            System.out.println(">> (c) Remove a book");
            System.out.println(">> (d) Create a book");
            System.out.println(">> (e) Order more of an existing book");
            System.out.println(">> (z) Go Back");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if ( input.equals("a") || input.equals("b") || input.equals("c") || input.equals("d") || input.equals("e") || input.equals("z") ){
                checked = true;
            } else {
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    public String getBooks(Scanner SCANNER){
        PostgresHandler.getBooks();
        return "True";
    }

    public String removeBook(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What is the ID of the Book being removed?"+"\n");

            // get the user input
            input = SCANNER.nextLine();

            String Output = PostgresHandler.deleteBook(input);

            if(Output.equals("-1")){
                System.out.println(">> No Such Book");

                System.out.println("\n"+">> Would you like to try again?"+"\n");
                System.out.println(">> a) Yes");
                System.out.println(">> b) No");

                // get the user input
                input = SCANNER.nextLine();
                if(input.equals("b")) {
                    checked = true;
                }
            }else{
                checked = true;
            }
        }
        return input;
    }

    public String orderBook(Scanner SCANNER){
        System.out.println("\n"+">> What is the ID of the Book being Ordered?"+"\n");
        String input = SCANNER.nextLine();

        String email = PostgresHandler.sendEmailByBook(input);
        return "true";
    }

    public String createBook(Scanner SCANNER){
        boolean finished = false;

        System.out.println(">> What is the name of the book you want to create?");
        String name = SCANNER.nextLine();

        System.out.println(">> What is the ISBN?");
        String isbn = SCANNER.nextLine();

        System.out.println(">> What is the genre?");
        String genre = SCANNER.nextLine();

        System.out.println(">> What is the number of the pages?");
        String numberOfPages = SCANNER.nextLine();

        System.out.println(">> What is the price?");
        String price = SCANNER.nextLine();

        String releaseDate = "";
        System.out.println(">> What is the release year?");
        releaseDate = releaseDate + SCANNER.nextLine() + "-";

        System.out.println(">> What is the release month?");
        releaseDate = releaseDate + SCANNER.nextLine() + "-";

        System.out.println(">> What is the release day?");
        releaseDate = releaseDate + SCANNER.nextLine();

        System.out.println(">> What is the quantity you started with?");
        String quantity = SCANNER.nextLine();

        System.out.println(">> What is the commission rate?");
        String commission = SCANNER.nextLine();

        // This should return the IDBook of the new Book
        String result = PostgresHandler.createBook( name, isbn, genre, numberOfPages, price, releaseDate, quantity, commission );
        System.out.println(">> Testing:" + result);

        System.out.println(">> What is the ID of the author?");
        String IDAuthor = SCANNER.nextLine();

        System.out.println(">> What is the ID of the publisher?");
        String IDPublisher = SCANNER.nextLine();

        PostgresHandler.createWrites( result, IDAuthor );
        PostgresHandler.createSold( result, IDPublisher );

        return "True";
    }

    // Look for a book
    public String bookSearch(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> How would you like to search?"+"\n");
            System.out.println(">> (a) Book ID");
            System.out.println(">> (b) Name");
            System.out.println(">> (c) Author");
            System.out.println(">> (d) ISBN");
            System.out.println(">> (e) Genre");
            System.out.println(">> (f) Price");
            System.out.println(">> (g) Publisher");
            System.out.println(">> (h) Release Date");
            System.out.println(">> (z) Log out");

            // get the user input
            input = SCANNER.nextLine();

            //search by book ID
            if (input.equals("a")) {
                System.out.println("\n"+">> What is the ID of the book?"+"\n");
                input = SCANNER.nextLine();
                PostgresHandler.searchBook("IDBook",input);
                checked = true;

                // Search by name
            } else if (input.equals("b")) {
                System.out.println("\n"+">> What is the name of the book?"+"\n");
                input = SCANNER.nextLine();
                PostgresHandler.searchBook("Name",input);
                checked = true;

                // Search by Author
            } else if (input.equals("c")) {
                System.out.println("\n"+">> What is the last name of the author you are looking for?"+"\n");
                input = SCANNER.nextLine();
                PostgresHandler.searchBook("Author",input);
                checked = true;

                // Search by ISBN
            } else if (input.equals("d")) {
                System.out.println("\n"+">> What is the ISBN of the book you are are looking for?"+"\n");
                input = SCANNER.nextLine();
                PostgresHandler.searchBook("ISBN",input);
                checked = true;

                // Search by Genre
            } else if (input.equals("e")) {
                System.out.println("\n"+">> What is the genre of the book you are are looking for?"+"\n");
                input = SCANNER.nextLine();
                PostgresHandler.searchBook("Genre",input);
                checked = true;

                // Search by Price
            } else if (input.equals("f")) {
                System.out.println("\n"+">> What is the price of the book you are looking for?"+"\n");
                input = SCANNER.nextLine();
                PostgresHandler.searchBook("Price", input);
                checked = true;

                // Search by Publisher
            } else if (input.equals("g")) {
                System.out.println("\n"+">> What is the publisher of the book you are are looking for?"+"\n");
                input = SCANNER.nextLine();
                PostgresHandler.searchBook("Publisher", input);
                checked = true;

                // Search by Release Date
            } else if (input.equals("h")) {
                String DateString = "";
                System.out.println("\n"+">> What was the year this book was released?"+"\n");
                DateString = DateString + SCANNER.nextLine() + "-";

                System.out.println("\n"+">> What was the month this book was released?"+"\n");
                DateString = DateString + SCANNER.nextLine() + "-";

                System.out.println("\n"+">> What was the day this book was released?"+"\n");
                DateString = DateString + SCANNER.nextLine();

                PostgresHandler.searchBook("ReleaseDate", DateString);
                checked = true;

            }else if(input.equals("z")){
                // Just go
                checked = true;
            } else{
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return "1";
    }


    //////  Author  //////

    public String manageAuthors(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) See all authors");
            System.out.println(">> (b) Remove an author ");
            System.out.println(">> (c) Create an author ");
            System.out.println(">> (z) Go Back");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") || input.equals("b") || input.equals("c") || input.equals("z")){
                checked = true;
            } else {
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    public String getAuthors(Scanner SCANNER){
        PostgresHandler.getAuthors();
        return "True";
    }

    public String createAuthor(Scanner SCANNER){
        boolean finished = false;
        while (!finished) {
            System.out.println(">> What is the first name of the author you want to create?");
            String firstName = SCANNER.nextLine();

            System.out.println(">> What is the last name of the author you want to create?");
            String lastName = SCANNER.nextLine();


            String result = PostgresHandler.createAuthor(firstName, lastName);
            if (result.equals("-1")){
                System.out.println(">> This author already exists");

                System.out.println("\n"+">> Would you like to try again?"+"\n");
                System.out.println(">> a) Yes");
                System.out.println(">> b) No");

                // get the user input
                String input = SCANNER.nextLine();
                if(input.equals("b")) {
                    finished = true;
                }
            }else{
                finished = true;
            }
        }
        return "True";
    }

    public String deleteAuthor(Scanner SCANNER){
        boolean finished = false;
        while (!finished) {
            System.out.println(">> What is the first name of the author you want to delete?");
            String firstName = SCANNER.nextLine();

            System.out.println(">> What is the last name of the author you want to delete?");
            String lastName = SCANNER.nextLine();


            String result = PostgresHandler.deleteAuthor(firstName, lastName);
            if (result.equals("-1")){
                System.out.println(">> No such author exists");

                System.out.println("\n"+">> Would you like to try again?"+"\n");
                System.out.println(">> a) Yes");
                System.out.println(">> b) No");

                // get the user input
                String input = SCANNER.nextLine();
                if(input.equals("b")) {
                    finished = true;
                }
            }else{
                finished = true;
            }
        }
        return "True";
    }


    //////  Publisher //////

    public String managePublishers(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) See all Publishers");
            System.out.println(">> (b) Remove an Publisher ");
            System.out.println(">> (c) Create an Publisher ");
            System.out.println(">> (z) Go Back");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") || input.equals("b") || input.equals("c") || input.equals("z")){
                checked = true;
            } else {
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    public String getPublishers(Scanner SCANNER){
        PostgresHandler.getPublishers();
        return "True";
    }

    public String createPublisher(Scanner SCANNER) {
        String result1 = "";
        String result2 = "";
        String input = "";
        boolean finished = false;
        while (!finished) {

            System.out.println(">> What is the name of the publisher you want to create?");
            String name = SCANNER.nextLine();

            System.out.println(">> What is their address?");
            String address = SCANNER.nextLine();

            System.out.println(">> What is their email address?");
            String emailAddress = SCANNER.nextLine();

            boolean bankingGood = false;
            String bankAccountNumber = "";
            while(!bankingGood) {
                System.out.println(">> What is their Bank Account Number?");
                bankAccountNumber = SCANNER.nextLine();

                if(isNumeric(bankAccountNumber)){
                    bankingGood = true;
                }else{
                    System.out.println(">> That account was not a number.");
                }
            }

            result2 = PostgresHandler.createPublisher(name, address, emailAddress, bankAccountNumber);
            if (result2.equals("-1")) {
                System.out.println(">> This name is already assigned to a publisher");

                System.out.println("\n" + ">> Would you like to try again?" + "\n");
                System.out.println(">> a) Yes");
                System.out.println(">> b) No");

                // get the user input
                input = SCANNER.nextLine();
                if (input.equals("b")) {
                    finished = true;
                    return "false";
                }
            }else {
                finished = true;
            }
        }

        boolean phonegood = false;
        while (!phonegood) {
            boolean phoneanumber = false;
            String PhoneNumber = "";
            while(!phoneanumber) {
                System.out.println(">> What is their PhoneNumber?");
                PhoneNumber = SCANNER.nextLine();
                if(isNumeric(PhoneNumber)){
                    phoneanumber = true;
                }else{
                    System.out.println(">> That account was not a number.");
                }
            }

            result1 = PostgresHandler.createPhone(PhoneNumber);
            if (result1.equals("-1")) {
                System.out.println(">> This phone number is already assigned to a publisher");
                System.out.println(">> and you are in too deep to back out now!" + "\n");
            } else {
                phonegood = true;
            }
        }
        PostgresHandler.createCalls(result1, result2);
        return result2;
    }

    public String deletePublisher(Scanner SCANNER) {
        boolean finished = false;
        while (!finished) {
            System.out.println(">> What is the ID of the publisher you want to delete?");
            String iD = SCANNER.nextLine();


            String result = PostgresHandler.deletePublisher(iD);
            if (result.equals("-1")){
                System.out.println(">> No such publisher exists");

                System.out.println("\n"+">> Would you like to try again?"+"\n");
                System.out.println(">> a) Yes");
                System.out.println(">> b) No");

                // get the user input
                String input = SCANNER.nextLine();
                if(input.equals("b")) {
                    finished = true;
                }
            }else{
                finished = true;
            }
        }
        return "True";
    }


    //////  Sales Report //////

    public String manageSalesReports(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) See Sales Per Genre");
            System.out.println(">> (b) See Sales Per Author ");
            System.out.println(">> (c) See Sales Per Publishers");
            System.out.println(">> (z) Go Back");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") || input.equals("b") || input.equals("c") || input.equals("z")){
                checked = true;
            } else {
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    public String showSalesReport(Scanner SCANNER, String reportType){
        if(reportType.equals("Genre")){
            PostgresHandler.getSalesReportGenre();
        }else if(reportType.equals("Author")){
            PostgresHandler.getSalesReportAuthor();
        }else if(reportType.equals("Publisher")){
            PostgresHandler.getSalesReportPublisher();
        }else{

        }
            return "True";
    }


    //////  Shipments  ///////

    public String manageOrders(Scanner SCANNER){
        boolean checked = false;
        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) See Orders");
            System.out.println(">> (b) Get all orders for one customer ");
            System.out.println(">> (c) Get one order ");
            System.out.println(">> (d) Manually delete an order ");
            System.out.println(">> (z) Go Back");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") || input.equals("b") ||  input.equals("c") ||  input.equals("d") || input.equals("z")){
                checked = true;
            } else {
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    public String getAllShipments(){
        PostgresHandler.getShipments();
        return "true";
    }

    public String getShipmentsByCustomer(Scanner SCANNER){
        System.out.println(">> What is the customer ID whose shipments you want to see?");
        String IDCustomer = SCANNER.nextLine();

        PostgresHandler.getShipmentsByCustomer(IDCustomer);
        return "true";
    }

    public String getAllShipmentsByNumber(Scanner SCANNER){
        System.out.println(">> What is the shipment number you want to see?");
        String ShipmentNumber = SCANNER.nextLine();
        PostgresHandler.getShipmentsByShipmentNumber( ShipmentNumber );
        return "true";
    }

    public String removeShipment(Scanner SCANNER){
        System.out.println(">> What is the shipment number you want to Remove");
        String ShipmentNumber = SCANNER.nextLine();
        PostgresHandler.deleteShipment( ShipmentNumber );
        return "true";
    }


    ////////////  User Stuff  ///////////

    // This is the basic user menu
    public String userMenu(String user, Scanner SCANNER){
        boolean checked = false;
        boolean isGuest = user.equals("guest");

        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) Look at all books");
            System.out.println(">> (b) Search for a book");

            if(!isGuest){
                System.out.println(">> (c) See my orders");
                System.out.println(">> (d) Check the status of an order");
                System.out.println(">> (e) Create an order");
            }
            System.out.println(">> (z) Log out");

            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") || input.equals("b") || input.equals("z")){
                checked = true;
            } else  if (!isGuest&&(input.equals("c") || input.equals("d") || input.equals("e"))){
                checked = true;
            } else{
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }

    public String checkoutMenu( Scanner SCANNER){
        boolean checked = false;

        String input= "";

        while(!checked) {
            System.out.println("\n"+">> What would you like to do?"+"\n");
            System.out.println(">> (a) See current cart");
            System.out.println(">> (b) Add to cart");
            System.out.println(">> (c) Finish order");
            System.out.println(">> (d) Cancel order");


            // get the user input
            input = SCANNER.nextLine();

            //check the value
            if (input.equals("a") || input.equals("b") || input.equals("c") || input.equals("d")) {
                checked = true;
            }else{
                System.out.println(">> Sorry, invalid input.");
                System.out.println(">> Please try again"+"\n");
            }
        }
        return input;
    }


    ////////////  Cart Stuff  ///////////
    public String seeCart(int ShippingID){
        String ShippingNumber = String.valueOf(ShippingID);
        PostgresHandler.seeCart(ShippingNumber);
        return "true";
    }

    public String addToCart(String User, Scanner SCANNER, int ShippingID){
        String ShippingNumber = String.valueOf(ShippingID);
        System.out.println(">> What is the Book ID you want to add?");
        String IDBook = SCANNER.nextLine();
        PostgresHandler.addToCart( User, IDBook, ShippingNumber );
        return "true";
    }

    public String deleteCart(int ShippingID){
        String ShippingNumber = String.valueOf(ShippingID);
        PostgresHandler.removeCart( ShippingNumber );
        return "true";
    }

    public String checkoutCart(Scanner SCANNER, String User, int ShippingID){
        String ShippingNumber = String.valueOf(ShippingID);

        System.out.println(">> What is Location you want to send this order to?");
        String destination = SCANNER.nextLine();

        System.out.println(">> What is bank account you want to send to charge?");
        String billing = SCANNER.nextLine();

        PostgresHandler.finishOrder( User, ShippingNumber, destination, billing);
        return "true";
    }

    ///////////// Helper Methods /////////

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

