package com.Project;

import java.sql.*;

/*  This handles all the postgres Stuff
 *
 *
 */
public class PostgresHandler {

    ////////////  Methods  ////////////

    // See if there exists a user with that user id
    public static String getName(String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            // Check if that user exists
            ResultSet rset = stmt.executeQuery(
                    "Select * from customer where IDCustomer =" + Input +" ;"
            );
            while (rset.next()) {
                return Input;
            }
            return "-1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }


    ////// Customer Stuff //////

    public static String createCustomer(String Input){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Check if someone already exists
            ResultSet rset = stmt.executeQuery(
                    "Select IDCustomer from customer where customer.name = '" + Input +"';"
            );
            if (rset.next()) {
                System.out.println(" This user already exists");
                return "-1";
            } else {
                stmt.executeUpdate(
                        "insert into customer(name) values ('"+ Input +"');"
                );
                rset = stmt.executeQuery(
                        "Select IDCustomer from customer where customer.name ='"  + Input +"';"
                );
                if(rset.next()) {
                    System.out.println(">> Welcome to Bouquet of Proses");
                    System.out.println(">> Your new ID is " + rset.getString("IDCustomer"));
                }else{
                    //HOW!! You just made it
                }
            }
            return "0";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String deleteCustomer(String Input){

        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
            ) {

            // Check if they exist
            ResultSet rset = stmt.executeQuery(
                    "Select IDCustomer from customer where customer.name = '" + Input +"';"
            );

            // Remove them if they do
            if (rset.next()) {
                stmt.executeUpdate(
                        "delete from customer where customer.name = '" + Input + "';"
                );
                return "1";
            }
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    // Print all books in database
    public static String getCustomers() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select * from Customer;"
            );
            while (rset.next()) {
                System.out.println(rset.getObject("IDCustomer")+". " + rset.getObject("Name"));
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }


    //////  Book stuff //////

    // Print all books in database
    public static String getBooks() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select * from Book;"
            );
            while (rset.next()) {
                System.out.println(rset.getObject("IDBook")+". " + rset.getObject("Name"));
                getAuthorByBook(rset.getString("IDBook"));
                System.out.println(" Genre: " + rset.getObject("Genre"));
                System.out.println(" Price: " + rset.getObject("Price"));
                System.out.println();
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    // See if there exists a Book with an input which matches that SearchType
    public static String searchBook(String SearchType, String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rset = null;
            Boolean found = false;
            // Check for ints
            if(SearchType.equals("IDBook")||SearchType.equals("ISBN")||SearchType.equals("NumberOfPages")||SearchType.equals("Price")) {
                rset = stmt.executeQuery(
                        "Select * from Book where " + SearchType + " = " + Input + ";"
                );
            // Check for strings
            }else if(SearchType.equals("Genre")||SearchType.equals("Name")||SearchType.equals("ReleaseDate")){
                rset = stmt.executeQuery(
                        "Select * from Book where " + SearchType + " = '" + Input + "';"
                );

            // Looking for book by author is it's own thing
            }else if(SearchType.equals("Author")){
                rset = stmt.executeQuery(
                    "select Book.IDBook, book.name, ISBN, Genre, NumberOfPages, Price, ReleaseDate, Quantity from book, writes, author where author.nameLast = '" + Input +"' and author.IDAuthor = Writes.IDAuthor and book.IDBook = Writes.IDBook;"
                );
            // Looking for book by author is it's own thing
            }else if(SearchType.equals("Publisher")){
                rset = stmt.executeQuery(
                        "select book.IDBook, book.name, ISBN, Genre, NumberOfPages, Price, ReleaseDate, Quantity from publisher, sold, book where publisher.name = '" + Input +"' and publisher.IDPublisher = sold.IDPublisher and book.IDBook = Sold.IDBook;"
                );
            } else{
                System.out.println("Invalid SearchType");
                return "-2";
            };

            while (rset.next()) {
                found = true;
                System.out.println(rset.getObject("IDBook")+". " + rset.getObject("Name"));
                getAuthorByBook(rset.getString("IDBook"));
                System.out.println(" ISBN: " + rset.getObject("ISBN"));
                System.out.println(" Genre: "+ rset.getObject("Genre"));
                System.out.println(" Number of Pages: " + rset.getObject("NumberOfPages"));
                System.out.println(" Price: " + rset.getObject("Price") );
                getPublisherByBook(rset.getString("IDBook"));
                System.out.println(" ReleaseDate: "+ rset.getObject("ReleaseDate"));
                System.out.println(" Quantity: "+ rset.getObject("Quantity"));
                System.out.println();
            }

            if(found){
                return Input;
            } else{
                System.out.println(">> No books match your search criteria");
                return "-1";
            }

        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }

        return "-1";
    }

    // Remove a book
    public static String deleteBook(String Input){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Delete Sales
            int rset1 = stmt.executeUpdate(
                    "delete from Sales where IDBook = " + Input + ";"
            );

            //Delete Sold
            int rset2 = stmt.executeUpdate(
                    "delete from sold where IDBook = " + Input + ";"
            );

            //Delete writes
            int rset3 = stmt.executeUpdate(
                    "delete from writes where IDBook = " + Input + ";"
            );

            //Delete Books
            int rset4 = stmt.executeUpdate(
                    "delete from Book where IDBook = " + Input + ";"
            );
            if ( rset4 == 0) {
                return "-1";
            }
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "1";
    }

    public static String createBook(String Input1, String Input2, String Input3, String Input4, String Input5, String Input6, String Input7, String Input8){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(
                    "insert into Book(name, isbn, genre, numberofpages, price, releasedate, quantity, commission) values ('"+ Input1 +"',"+ Input2 + ",'"+ Input3 + "', " + Input4 + " , " + Input5 + " , '" + Input6 + "' , " + Input7 + " , " + Input8 + ");"
            );

            ResultSet rset = stmt.executeQuery(
                    // Get the ID of the book just created
                    "Select IDBook from Book where name = '" + Input1 + "' and isbn = " + Input2 + " and genre = '" + Input3 + "' and numberofpages = " + Input4 + " and price = " + Input5 + " and releasedate = '" + Input6 + "' and quantity = " + Input7 + " and commission = "+ Input8 +";"
            );
            String temp = "-1";
            while(rset.next()){
                temp = rset.getString("IDBook");
            }

            return temp;
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String createWrites(String Input1, String Input2){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(
                    "insert into Writes(IDBook, IDAuthor) values ("+ Input1 +", "+ Input2 + ");"
            );

        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String createSold(String Input1, String Input2){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(
                    "insert into Sold(IDBook, IDPublisher) values ("+ Input1 +", "+ Input2 + ");"
            );

        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }



    //////  Author Stuff  //////

    public static String getAuthors() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select * from Author;"
            );
            while (rset.next()) {
                System.out.println(rset.getObject("IDAuthor") + ". " + rset.getObject("namelast") + ", " + rset.getObject("namefirst"));
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String createAuthor(String Input1, String Input2){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Check if someone already exists
            ResultSet rset = stmt.executeQuery(
                    "Select * from author where namefirst = '" + Input1 + "' and nameLast = '" + Input2 + "';"
            );
            if (rset.next()) {
                return "-1";
            } else {
                stmt.executeUpdate(
                        "insert into Author(namefirst, namelast) values ('"+ Input1 +"', '"+ Input2 + "');"
                );
            }
            return "0";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String deleteAuthor(String Input1, String Input2){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            ResultSet rset1 = stmt.executeQuery(
                    "Select IDAuthor from author where namefirst = '" + Input1 + "' and nameLast = '" + Input2 + "';"
            );

            String tempOut = "";

            while(rset1.next()){
                tempOut = rset1.getString("IDAuthor");
            }

            stmt.executeUpdate(
                    "Delete from writes where IDAuthor = " + tempOut +";"
            );


            int rset9 = stmt.executeUpdate(
                    "Delete from author where namefirst = '" + Input1 + "' and nameLast = '" + Input2 + "';"
            );

            if ( rset9 == 0) {
                return "-1";
            }
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "1";
    }


    ////  Publisher Stuff  //////

    public static String getPublishers() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select * from Publisher;"
            );
            while (rset.next()) {
                System.out.println(rset.getObject("IDPublisher")+". " + rset.getObject("Name"));
                getPhoneByPublisher(rset.getString("IDPublisher"));
                System.out.println(" Address: " + rset.getObject("Address"));
                System.out.println(" Email Address: " + rset.getObject("emailaddress"));
                System.out.println(" Bank Account Number: " + rset.getObject("bankingaccount"));
                System.out.println();
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String createPhone(String Input){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Check if someone already exists
            ResultSet rset = stmt.executeQuery(
                    "Select * from phone where phonenumber = '" + Input + "';"
            );
            if (rset.next()) {
                return "-1";
            } else {
                stmt.executeUpdate(
                        "insert into phone( phonenumber ) values ('"+ Input+"');"
                );

                rset = stmt.executeQuery(
                        "Select * from phone where phonenumber = '" + Input + "';"
                );
                rset.next();
                return rset.getString("IDPhone");
            }
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        // Just in case
        return "-1";
    }

    public static String createCalls(String Input1, String Input2){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(
                    "insert into Calls( IDPublisher, IDPhone ) values ("+ Input2 +","+ Input1+");"
            );

        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        // Just in case
        return "-1";
    }

    public static String createPublisher(String Input1, String Input2, String Input3, String Input4 ){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Check if someone already exists
            ResultSet rset = stmt.executeQuery(
                    "Select * from publisher where name = '" + Input1 + "';"
            );
            if (rset.next()) {
                return "-1";
            } else {
                stmt.executeUpdate(
                        "Insert into Publisher(name, address, emailAddress, bankingAccount) values ('"+ Input1 +"', '"+ Input2 + "','" + Input3 + "'," + Input4 + ");"
                );

                rset = stmt.executeQuery(
                        "Select * from publisher where name = '" + Input1 + "';"
                );
                rset.next();
                return rset.getString("IDPublisher");

            }
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String deletePublisher(String Input){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            // Get the number of "calls" which match the publisher
            ResultSet rset1 = stmt.executeQuery(
                    "Select Count(*) from calls where IDPublisher = "+ Input +";"
            );

            rset1.next();
            String[] temp = new String[Integer.valueOf(rset1.getString("Count"))];

            // Get the "calls" which match the publisher
            ResultSet rset2 = stmt.executeQuery(
                    "Select * from calls where IDPublisher = "+ Input +";"
            );


            int iterator = 0;
            while(rset2.next()){
                String tempOut = rset2.getString("IDPhone");
                temp[iterator] = tempOut;
                iterator = iterator + 1;
            }

            for(int i =0; i < temp.length; i++){
                stmt.executeUpdate(
                        "Delete from calls where IDPublisher = "+ Input +";" +
                                "Delete from phone where IDPhone = "+ temp[i] +";"
                );
            }


            // Orphan the books
            int rset3 = stmt.executeUpdate(
                        "Delete from sold where IDPublisher = "+Input+";"
            );

            // Delete the books
            int rset4 = stmt.executeUpdate(
                    "Delete from publisher where IDPublisher = "+Input+";"
            );

            if ( rset4 == 0) {
                return "-1";
            }

        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "1";
    }


    //////  Sales Report  ////////

    public static String getSalesReportGenre() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select genre, count(Sales.IDSale) from sales, book where Sales.IDBook = Book.IDBook group by genre;"
            );

            while (rset.next()) {
                System.out.println("  Genre:" + rset.getObject("Genre"));
                System.out.println("  Sales Amount:" + rset.getObject("Count"));
                System.out.println("");
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String getSalesReportAuthor() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select namefirst, namelast, count(Sales.IDSale) from sales, book, writes, author where book.IdBook = Sales.IDBook and book.IDBook = writes.IDBook and writes.IDAuthor = Author.IDAuthor group by Author.IDAuthor;"
            );
            while (rset.next()) {
                System.out.println("  Author:" + rset.getObject("nameLast") +", " + rset.getObject("nameFirst"));
                System.out.println("  Sales Amount:" + rset.getObject("Count"));
                System.out.println("");
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String getSalesReportPublisher() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select publisher.name, count(Sales.IDSale) from sales, book, sold, publisher where book.IdBook = Sales.IDBook and book.IDBook = sold.IDBook and Sold.IDPublisher = Publisher.IDPublisher group by Publisher.IDPublisher;"
            );
            while (rset.next()) {
                System.out.println("  Publisher Name: " + rset.getString("name"));
                System.out.println("  Sales Amount: " + rset.getString("Count"));
                System.out.println();
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }


    //////  Shipments  ////////

    public static String getShipments() {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select shipment.Shipmentnumber, count(*), destination, IDCustomer from shipment, checkout group by shipment.shipmentnumber, destination, IDcustomer;"
            );
            while (rset.next()) {
                System.out.println("Shipment Number: " + rset.getString("shipmentnumber"));
                System.out.println("  Ordered by: " + rset.getString("IDCustomer"));
                System.out.println("  Destination: " + rset.getString("destination"));
                System.out.println("  Number of items: " + rset.getString("Count"));
                System.out.println();
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String getShipmentsByCustomer(String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                   "Select checkout.shipmentNumber, Checkout.IDBook, Book.name from checkout,book, shipment where checkout.IDCustomer = '"+ Input +"' and checkout.IDbook = book.IDBook and shipment.shipmentNumber = checkout.shipmentNumber;"
            );

            System.out.println("Customer: " + Input);
            while (rset.next()) {
                System.out.println("  Ordered: " + rset.getString("name"));
                System.out.println("  Shipment: " + rset.getString("shipmentNumber"));
                System.out.println();
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String getShipmentsByShipmentNumber(String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                   " Select book.name, currentLocation, destination from checkout, book, shipment where Checkout.ShipmentNumber = '"+Input+"' and checkout.IDbook = book.IDBook and shipment.shipmentNumber = checkout.shipmentNumber; "
            );

            System.out.println("Order Number: " + Input);
            while (rset.next()) {
                System.out.println("  Book: " + rset.getString("name"));
                System.out.println("  Current Location: " + rset.getString("currentLocation"));
                System.out.println("  Destination: " + rset.getString("Destination"));
                System.out.println();
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String deleteShipment(String Input){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(
                    "Delete from writes where IDAuthor = " + Input +";"
            );

            int rset = stmt.executeUpdate(
                    "Delete from shipment where ShipmentNumber = '"+ Input +"';"
            );

            if ( rset == 0) {
                return "-1";
            }
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "1";
    };

    public static int getNextShipmentNumber(){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            int output = 0;

            ResultSet rset = stmt.executeQuery(
                    "Select max(shipmentnumber) from shipment;"
            );

            rset.next();

            int out = rset.getInt("max");

            if ( out < 0) {
                return 0;
            }
            return out;
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return 1;
    };

    ////// Cart Stuff //////
    public static String seeCart(String ShipmentNumber) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            //Get the books
            ResultSet rset = stmt.executeQuery(
                    "Select Book.name from book, checkout  where Book.IDBook = Checkout.IDBook and checkout.ShipmentNumber = "+ ShipmentNumber +";"
            );
            System.out.println("Current Cart: ");
            while (rset.next()) {
                System.out.println(" Book : " + rset.getString("Name"));
            }
            System.out.println();
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    public static String addToCart(String IDCustomer, String IDBook, String ShippingNumber){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(
                  " Insert into Checkout(IDCustomer, IDBooK, ShipmentNumber) values ( "+ IDCustomer + "," + IDBook + "," + ShippingNumber + ");"
            );

            return "0";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "0";
    };

    public static String removeCart(String ShippingNumber){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {

            stmt.executeUpdate(
                    " Delete from checkout where ShipmentNumber = "+ShippingNumber+";"
            );

            return "0";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "0";
    };

    public static String finishOrder(String IDCustomer, String ShippingNumber,String destination,String billing){
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            // Get the size of the order
            ResultSet rset = stmt.executeQuery(
                    "Select Count(*) from checkout, book where checkout.IDBook = book.IDBook and Checkout.ShipmentNumber = "+ ShippingNumber+";"
            );
            rset.next();
            int orderSize = rset.getInt("count");
            String[] IdBooks = new String[orderSize];
            String[] quantities= new String[orderSize];


            // Get every IDbook in the order

            int iterator = 0;
            ResultSet rset2 = stmt.executeQuery(
                    "Select Book.IDBook, Book.Quantity from checkout, book where checkout.IDBook = book.IDBook and Checkout.ShipmentNumber = "+ ShippingNumber+";"
            );

            while(rset2.next()){
                IdBooks[iterator] = rset2.getString("IDBook");
                quantities[iterator] = rset2.getString("Quantity");
                iterator = iterator + 1;
            }


            for (int i = 0; i < IdBooks.length; i++) {
                int temp = Integer.valueOf(quantities[i]);

                // lower the quantity of each
                temp =  temp - 1;
                // If less then 10 send email
                if(temp < 10){
                   //sendEmailByBook(IdBooks[i]);
                    temp = temp + 10;
                }
                String Output = String.valueOf(temp);

                //Update database
                stmt.executeUpdate(
                        "Update book set quantity = "+ temp +" where IDBook = "+ IdBooks[i]+";"
                );

                // Create a sale of each
                stmt.executeUpdate(
                        "Insert into Sales (IDBook, Saledate) values (" + IdBooks[i] + ", '" + Consts.CURRENTDATE + "');"
                );
            }

            // create the shipment itself
            stmt.executeUpdate(
                    "Insert into Shipment( CustomerID, ShipmentNumber, CurrentLocation, Destination, Billing ) values ("+ IDCustomer +",'" + ShippingNumber + "','"+Consts.WAREHOUSE +"','" + destination + "', '"+billing+"')"
            );
            return "0";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "0";
    };


    ////// Helper Functions ///////

    // Get the authors of a book
    public static String getAuthorByBook( String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rset = stmt.executeQuery(
                    "select namelast, namefirst from author, writes, book where Book.IDBook = +" + Input + "and Book.IDBook = writes.IDBook and writes.IDAuthor = Author.IDAuthor;"
            );

            String output = "";
            while (rset.next()) {
                System.out.println(" Author : " + rset.getObject("nameLast") +", " + rset.getObject("nameFirst"));
            }
            return output;
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    // Get the publisher of a book
    public static String getPublisherByBook( String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rset = stmt.executeQuery(
                    "select publisher.name from publisher, sold, book where Book.IDBook = " + Input + " and Book.IDBook = Sold.IDBook and Sold.IDPublisher = Publisher.IDPublisher;"
            );

            String output = "";
            while (rset.next()) {
                System.out.println(" Publisher : " + rset.getObject("name"));
            }
            return output;
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    // Get the phone number of a publisher
    public static String getPhoneByPublisher( String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rset = stmt.executeQuery(
                    "select phone.phonenumber from Phone, calls, Publisher where Publisher.IDPublisher = " + Input + " and Phone.IDPhone = Calls.IDPhone and Calls.IDPublisher = Publisher.IDPublisher;"
            );

            String output = "";
            while (rset.next()) {
                System.out.println(" PhoneNumber : " + rset.getObject("phonenumber"));
            }
            return output;
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    // Get the email by book
    public static String sendEmailByBook( String Input) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:" + Consts.LOCALPORT + "/" + Consts.DBNAME, Consts.USERNAME, Consts.PASSWORD);
                Statement stmt = conn.createStatement();
        ) {
            ResultSet rset = stmt.executeQuery(
                    "select publisher.emailAddress from publisher, sold, book where Book.IDBook = " + Input + " and Book.IDBook = Sold.IDBook and Sold.IDPublisher = Publisher.IDPublisher;"
            );

            while (rset.next()) {
                emailPublisher(rset.getString("emailAddress"));
            }
            return "1";
        } catch (Exception sqle) {
            System.out.println("Exception : " + sqle);
        }
        return "-1";
    }

    // Doesn't Work
    public static void emailPublisher(String Email){
        // Email publisher here
        // Maybe divide order between publishers.
        // Naybe make the order on a first come first serve basis
    }


}

