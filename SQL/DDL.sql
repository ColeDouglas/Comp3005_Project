/* All the DDL you need to make your own server
 *
 *  The first part creates the tables
 *
 *  The second part inserts some data to start with
 *
 * @author Cole Douglas
*/

-- The Book Relation --
create table book (
	IDBook			serial,               
	Name			VARCHAR(30),       
	ISBN			INT,
	Genre			VARCHAR(30),
	NumberOfPages	INT,
	Price			NUMERIC(6,2),
 	Publisher		VARCHAR(30),
	ReleaseDate		DATE,
	Quantity		INT,
	Commission 		NUMERIC(3,2),

	primary key (IDBook)
);


-- The Sales Relation --
Create table sales( 
	IdSale 			serial,
	IDBook  		Int,
	SaleDate  		Date,
		
	primary key (IDSale)
);


-- The Author Relation --
create table author(
	IDAuthor		serial,
 	nameFirst 		varchar(20),
	nameLast		varchar(20),

 	primary key (IDAuthor)
);


 -- The Book/Author Relation --
create table writes(
	IDBook			INT,
	IDAuthor 		INT, 

	primary key (IDBook, IDAuthor),
	foreign key (IDBook) references book,
	foreign key (IDAuthor) references author
);

 -- The Publisher Relation --
create table publisher(
	IDPublisher		serial,
	name 			varchar(30),
	Address			varchar(30),
	emailAddress	varchar(30),
	BankingAccount	INT,

	primary key (IDPublisher)
 );

 -- The Book/Publisher Relation --
create table Sold(
	IDBook 			INT,
	IDPublisher 	INT,	
	Saledate 		Date,

	primary key (IDBook, IDPublisher),
	foreign key (IDBook) references book,
	foreign key (IDPublisher) references publisher
);


-- The Phone Relation --
create table phone(
	IDPhone			serial,
	PhoneNumber		numeric(13),

	primary key (IDPhone)
);


 -- The Publisher/Phone Relation --
create table calls(
	IDPublisher 	INT,
	IDPhone			INT, 

	primary key (IDPublisher, IDPhone),
	foreign key (IDPublisher) references publisher,
	foreign key (IDPhone) references phone
);


-- The Customer Relation --
create table Customer( 
	IDCustomer  	 	serial,
	Name			VARCHAR(20),

	primary key (IDCustomer)
);


-- The Shipment Relation --
-- ID Shipment is for the tables, Shipment Number is for the users.
create table Shipment (
	IDShipment			serial,
	CustomerID		INT,
	ShipmentNumber		INT,
	CurrentLocation VARCHAR(30),
	Destination		VARCHAR(30),
	Billing			INT,
	primary key (IDShipment)
);


-- The Customer/Shipment Relation --
create table has (
	IDCustomer 		INT,
	IDShipment 		INT,
	primary key (IDCustomer, IDShipment),
	foreign key (IDCustomer) references Customer,
	foreign key (IDShipment) references Shipment		
);

-- The Customer/book Relation --
create table checkout (
	IDCustomer		INT,
	IDBook			INT,
	ShipmentNumber  		INT,
	primary key (IDCustomer, IDBook),
	foreign key (IDCustomer) references Customer,
	foreign key (IDBook) references book
);



--- Sample insertions to get you started --- 


insert into Book ( Name, ISBN, Genre, NumberOfPages, Price, Publisher, ReleaseDate, Quantity, Commission )
values 
    ( 'Going Postal',           0060502932,    'Fantasy', 416,  7.99,   'RandomHouse', '2005-09-27', 42, 0.11),
 	( 'Good Omens',             0060853980,    'Fantasy', 512, 12.99, 'WilliamMorrow', '2006-11-28', 30,  0.6),	  
 	( 'A Slip of the Keyboard', 0060853980,    'Opinion', 512, 28.00, 'WilliamMorrow', '2006-11-28', 25,  0.6),	
 	( 'Theogony',                019953831, 'Philosophy',  24, 24.11,  'Peguin Books', '0-01-01', 12, 0.12),	
 	( 'Work and Days',           017868421, 'Philosophy',  18, 28.00,  'Peguin Books', '0-01-01', 11, 0.35),	
	( 'The Sandman',            0380817705, 'Comic Book', 416, 40.12,   'HarperTorch', '2002-01-08', 25, 0.26),	
    (  'Neverwhere',            1401210074,    'Fiction', 224, 11.46,       'Vertigo', '2007-02-14', 44, 0.13),
	( 'Happily Ever After', 	0358757705,'Non-Fiction', 148, 38.00,   'RandomHouse', '1986-01-01', 31, 0.44),
	( 'White Fragility', 		0807047414,   'Politics', 192, 10.01,  'Beacon Press', '2018-07-26', 17, 0.39),
	( 'Normal People', 			1984822187,    'Fiction', 304,  8.20,   'HarperTorch', '2020-02-18', 31, 0.17),
	( 'Diary of a Wimpy kid',   1419749153,    'Children', 224, 9.00, 'WilliamMorrow', '2011-12-25', 29, 0.12);


insert into Sales(IDBook, SaleDate )
values 
	(1, '2007-8-27'),
	(1, '2010-11-5'),
	(1, '2014-11-2'),
	(1, '2019-11-29'),	
	(1, '2021-4-27'),
	(2, '2006-6-8'),
	(2, '2011-5-24'),
	(2, '2014-2-2'),
	(2, '2020-5-2'),
	(2, '2021-8-22'),	
	(3, '2001-6-13'),
	(3, '2002-4-15'),
	(3, '2013-8-15'),	
	(3, '2015-4-7'),
	(4, '2000-2-11'),	
	(4, '2013-7-4'),
	(4, '2018-11-6'),
	(6, '2010-2-19'),
	(6, '2012-6-11'),	
	(7, '2020-8-30'),
	(8, '2001-10-17'),
	(8, '2003-11-15'),
	(9, '2006-2-20'),
	(10,'2001-9-12'),
	(11,'2021-11-15');


insert into Author(nameFirst, nameLast)
values 
    ('Terry', 'Pratchet'),
	('Hesoid', 'the Sage'),
	('Neil', 'Gaiman'),
	('Robin', 'DiAngelo'),
	('Sally', 'Rooney'),
	('Jeff', 'Kinney');


insert into writes(IDBook, IDAuthor)
values 
	(1 , 1),
	(2 , 1),
	(3 , 1),
	(4 , 2),
	(5 , 2),
	(6 , 3),
	(7 , 3),
	(8 , 3),
	(9 , 4),
	(10 , 5),
	(11 , 6);
	

insert into Publisher ( Name, Address, EmailAddress, BankingAccount )
values
	('RandomHouse' , 'Some Street', 'RandomHouse Email', 1),
	('WilliumMorrow' , 'Some Street', 'WilliumMorrow Email', 2),
	('Peguin Books' , 'Some Street', 'Peguin Books', 3),
	('HarperTorch' , 'Some Street', 'Harper Torch Email', 4),
	('Vertigo' , 'Some Street', 'Vertigo Email', 5),
	('Beacon Press' , 'Some Street', 'Beacon Email', 6);


insert into sold ( IDBook, IDPublisher)
values
	(1 , 1),
	(2 , 2),
	(3 , 2),
	(4 , 3),
	(5 , 3),
	(6 , 4),
	(7 , 5),
	(8 , 1),
	(9 , 6),
	(10, 4),
	(11, 2);


insert into Phone ( PhoneNumber )
values
	(1111111),
	(1111112),
	(2222222),
	(3333333),
	(4444444),
	(5555555),
	(6666666),
	(6666667);


insert into calls ( IDPublisher, IDPhone )
values
	(1, 1),
	(1, 2),
	(2, 3),
	(3, 4),
	(4, 5),
	(5, 6),
	(6, 7),
	(6, 8);


insert into Customer ( IDCustomer, Name )
values
	(0, 'owner'),
	(1, 'user');


insert into checkout ( IDCustomer, IDBook, ShipmentNumber)
values
	(1,1,66);


insert into Shipment( CustomerID, ShipmentNumber, CurrentLocation, Destination, Billing )
values
	(1, 66,'Somewhere', 'Somewhere else', 1);

