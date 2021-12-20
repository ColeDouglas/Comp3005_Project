/* The queries used in my application
 *
 * @ author Cole Douglas
 *
*/

-------  Customer Stuff  -------

--- Find out if there exists a customer registered ---
-- input = customer id you are looking for 
Select * from Customer where IDCustomer = input;

--- Make a customer ---
-- Input = the user who wants to be created
insert into Customer values ('input');

--- Find a specific customer ---
-- input = user name 
Select IDCustomer from customer where customer.name = input;

--- Delete a customer ---
-- input person getting deleted
delete from customer where customer.name = input;

--- Remove a customer's order? ---
-- Input = Customer ID
-- delete from checkout where IDcustomer = input;
-- I decided against this because it meant in progress deliveries would be lost if a customer unregistered.



-------  Author Stuff  -------

---  Find all authors  ---
Select * from author;

--- Find a specific author ---
-- Input1 = first name 
-- Input2 = last name
Select * from author where namefirst = 'Input1' and nameLast = 'Input2';

--- Make a new author ---
-- Input1 = first name 
-- Input2 = last name
insert into author(namelast, namefirst) values ('Input1', 'Input2');

--- Find a specific author ---
-- Input1 = first name 
-- Input2 = last name
Delete from author where namefirst = 'Input1' and nameLast = 'Input2';





-------  Publisher Stuff  -------

---  Find all publishers ---
Select * from Publisher;

---  Find all phones related to one publisher  ---
-- Input = Id of publisher you are looking for
Select phone.phonenumber from Phone, calls, Publisher where Publisher.IDPublisher = Input and Phone.IDPhone = Calls.IDPhone and Calls.IDPublisher = Publisher.IDPublisher;
    
---  Create a new publisher  ---
-- Input1 = Name of the publisher
-- Input2 = Address of the publisher
-- Input3 = Email Address of the publisher
-- Input4 = Bank account number to which to send payment
Insert into Publisher(name, address, emailaddress, bankAccount) values ( 'Input1', 'Input2', 'Input3', Input4);

---  Find out if the phone number exists already  ---
-- Input = The number you are looking for
Select * from phone where phonenumber = Input;

---  Create a new phone number  --- 
Insert into phone( phonenumber ) values ( Input );

--- Delete a publisher  ---
-- Input = The Id of the deleted publisher
-- Delete the stuff first because manual cascade is better;
select IDPhone from calls where IDPublisher = Input;
Delete from calls where IDPublisher = Input;
Delete from sold where IDPublisher = Input;
Delete from phone where IDPhone is <result>;
Delete from publisher where IDPublisher = Input;




-------  Book Stuff  -------

--- Print out all books ---
Select * from book;


---  Create a book  ---
-- Input1 = Name of the book
-- Input2 = ISBN 
-- Input3 = Genre 
-- Input4 = Number of pages
-- Input5 = Price
-- Input6 = Release date
-- Input7 = Quantity
-- Input8 = Comission the publisher gets
Insert into Book(name, isbn, genre, numberofpages, price, releasedate, quantity, commission) values ( Input1 , Input2, Input3 , Input4 , Input5, Input6, Input7 , Input8);

---  Create a writes  ---  
-- Input1 = The ID of the book the author wrote
-- Input2 = The ID of the Author
Insert into writes(IDBook, IDAuthor) values (Input1, Input2) 

---  Create a sold  ---  
-- Input1 = The ID of the book the publisher made
-- Input2 = The ID of the publisher
Insert into writes(IDBook, IDPublisher) values (Input1, Input2) 

---  Get the ID of a very specific book  ---
-- Input1 = Name of the book
-- Input2 = ISBN 
-- Input3 = Genre 
-- Input4 = Number of pages
-- Input5 = Price
-- Input6 = Release date
-- Input7 = Quantity
-- Input8 = Comission the publisher gets
Select IDBook into Book where 
    name = Input1
    and isbn = Input2
    and genre = Input3
    and numberofpages = Input4
    and price = Input5
    and releasedate = Input6
    and quantity = Input7
    and commission = Input8;

--- Search for a book ---
-- input1 = Search field e.g. ISBN, Title 
-- input2 = the thing your looking for
Select * from book where input1 = input2;

-- or --
Select * from book where input1 = 'input2';

-- Search for a book by author --
select book.IDBook, book.name, ISBN, Genre, NumberOfPages, Price, ReleaseDate, Quantity from book, writes, author 
    where author.nameLast = 'input2' 
    and author.IDAuthor = writes.IDAuthor
    and book.IDBook = writes.IDBook;

-- Search for a book by publisher --
select book.IDBook, book.name, ISBN, Genre, NumberOfPages, Price, ReleaseDate, Quantity from publisher, sold, book 
    where publisher.name = 'input2' 
    and publisher.IDPublisher = sold.IDPublisher
    and book.IDBook = Sold.IDBook;

--- Find the author of a book ---
-- input = the Book ID 
select namelast, namefirst from author, writes, book 
    where Book.IDBook = input
    and Book.IDBook = writes.IDBook
    and writes.IDAuthor = Author.IDAuthor;

--- Find the publisher of a book ---
-- input = the Book ID 
select publisher.name from publisher, sold, book 
    where Book.IDBook = input
    and Book.IDBook = Sold.IDBook
    and Sold.IDPublisher = Publisher.IDPublisher;

-- Get the email address of all possible publishers of a book to send one of them an email ordering more.
-- input = the Book ID 
Select emailaddress from book, sold, publisher 
    where Book.IDBook = input
	and Sold.IDBook = Sold.IDPublisher
	and Sold.IdPublisher = Publisher.IDPublisher

--- Get the largest in use shipmentnumber ---
Select max(shipmentnumber) from shipment;


------  Sales report Stuff  ------

--- Sale report for genre  ---
Select genre, count(Sales.IDSales) from sales, book 
    where Sales.IDBook = Book.IDBook 
    group by genre ;

--- Sale report for author ---
 Select namefirst, namelast, count(Sales.IDSale) from sales, book, writes, author 
    where book.IdBook = Sales.IDBook
    and book.IDBook = writes.IDBook 
    and writes.IDAuthor = Author.IDAuthor  
    group by Author.IDAuthor;

--- Sale report for publisher ---
 Select publisher.name, count(Sales.IDSale) from sales, book, sold, publisher 
    where book.IdBook = Sales.IDBook
    and book.IDBook = sold.IDBook 
    and sold.IDPublisher = Publisher.IDPublisher  
    group by Publisher.IDPublisher;




------  Shipment Stuff  ------

--- Display all shipments ---
Select shipment.Shipmentnumber, count(*), destination, IDCustomer from shipment, checkout 
group by shipment.shipmentnumber, destination, IDCustomer;

--- Get a all shipments from one customer ---
-- Input = The id of the customer
Select Checkout.IDcustomer, checkout.shipmentNumber, Checkout.IDBook, Book.name from checkout, book, shipment
	where Checkout.IDCustomer = 'Input';
    and checkout.IDbook = book.IDBook
	and shipment.shipmentNumber = checkout.shipmentNumber;

--- Get a specific shipment ---
-- Input = The id of the shipment
    Select from book.name, currentLocation, destination from checkout, book, shipment
	where Checkout.shipmentNumber = 'Input'
	and checkout.IDbook = book.IDBook
	and shipment.shipmentNumber = checkout.shipmentNumber;

--- Manually delete a shipment ---
-- Input = shipmentnumber to be deleted
    Delete from shipment where ShipmentNumber = Input;
    Delete from checkout where ShipmentNumber = Input;



------  Cart Stuff  ------

--- See the chat ---
-- Input = the shipping number
    Select Book.name from book, checkout  where Book.IDBook = Checkout.IDBook and checkout.ShipmentNumber = Input

--- Add to cart ---
-- Input1 = customer making the order
-- Input2 = Book to be added to cart
-- Input3 = The eventual shipment this will be a part off
    Insert into Checkout(IDCustomer, IDBooK, ShipmentNumber) values ( Input1, Input2, Input3 );

--- Cancel Order ---
-- Input = the order being canceled;
    Delete from checkout where ShipmentNumber = Input;

--- Get the number of books in an order ---
-- Input = The shipment number
    Select Count(*) from checkout, book where checkout.IDBook = book.IDBook and Checkout.ShipmentNumber = input;

--- Get The ID and Quantity of every book in the order --- 
-- Input = The shipment number
    Select Book.IDBook, BookQuantity where where checkout.IDBook = book.IDBook and Checkout.ShipmentNumber = input;


--- update the quatity of the sold books --- 
-- Input1 = The new quantity
-- Input2 = The new ID
    update book set quantity = input1 where IDBook = input2;


--- Create a Sale to record sale ---
-- Input 1 = The Id of the Book being sold
    Insert into Sales (IDBook, Saledate) values (Input, 'Consts.CURRENTDATE');

--- Finalize cart ---
-- Input1 = The id of the customer
-- Input2 = Shipment number
-- Input3 = Destination of the shipment
-- Input4 = Billing information from the customer
    Insert into Shipment( CustomerID, ShipmentNumber, CurrentLocation, Destination, Billing ) values ('Input1', 'Input2','Consts.WAREHOUSE','Input3', 'Input4');