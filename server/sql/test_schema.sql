DROP database IF EXISTS capstone_test;
CREATE database capstone_test;
use capstone_test;

CREATE TABLE account(
	account_id int PRIMARY KEY auto_increment,
	email varchar(255) UNIQUE NOT NULL,
	password varchar(50) NOT NULL,
);

CREATE TABLE directory(
	directory_id int PRIMARY KEY auto_increment,
	account_id int NOT NULL,
	parent_directory int NULL,
	directory_name varchar(50) NOT NULL,
	
	CONSTRAINT fk_directory_account
	FOREIGN KEY (account_id)
	REFERENCES account(account_id),
	
	CONSTRAINT fk_directory_parent
	FOREIGN KEY (parent_directory)
	REFERENCES directory(directory_id)
	on delete cascade
);

CREATE TABLE document_type(
	document_type_id int PRIMARY KEY auto_increment,
	document_type_name varchar(50)
);

CREATE TABLE document(
	document_id int PRIMARY KEY auto_increment,
	document_type_id int NOT NULL,
	document_name varchar(50) NOT NULL,
	directory_id int NOT NULL,
	
	CONSTRAINT fk_document_type
	FOREIGN KEY (document_type_id)
	REFERENCES document_type(document_type_id),
	
	CONSTRAINT fk_document_directory
	FOREIGN KEY (directory_id)
	REFERENCES directory(directory_id)
);

CREATE TABLE element_type(
	element_type_id int PRIMARY KEY auto_increment,
	`type` varchar(50) NOT null
);

CREATE TABLE element(
	element_id int PRIMARY KEY auto_increment,
	element_type_id int NOT NULL,
	document_id int NOT NULL,
	
	CONSTRAINT fk_element_type
	FOREIGN KEY (element_type_id)
	REFERENCES element_type(element_type_id),
	
	CONSTRAINT fk_element_document
	FOREIGN KEY (document_id)
	REFERENCES document(document_id)
	on delete cascade
);

CREATE TABLE document_element_link(
	document_element_link_id int primary KEY auto_increment,
	element_id int NOT NULL,
	document_id int NOT NULL,
	name varchar(50) null,
	description varchar(250) null,
	
	CONSTRAINT fk_link_element
	FOREIGN KEY (element_id)
	REFERENCES element(element_id)
	on delete cascade,
	
	CONSTRAINT fk_link_document
	FOREIGN KEY (document_id)
	REFERENCES document(document_id)
	on delete cascade
);

CREATE TABLE `attribute`(
	attribute_id int PRIMARY KEY auto_increment,
	element_id int NOT NULL,
	`key` varchar(50) not null,
	value TEXT NOT NULL,
	
	CONSTRAINT fk_attribute_element
	FOREIGN KEY (element_id)
	REFERENCES element(element_id)
);

delimiter //
CREATE PROCEDURE set_known_good_state()
BEGIN
	DELETE FROM `attribute`;
	DELETE FROM document_element_link;
	delete from element;
	delete from document;
	delete from document_type;
	delete from directory;
	delete from account;
	delete from element_type;

	alter table `attribute` auto_increment = 1;
	alter table document_element_link auto_increment = 1;
	alter table element auto_increment = 1;
	alter table document auto_increment = 1;
	alter table document_type auto_increment = 1;
	alter table directory auto_increment = 1;
	alter table account auto_increment = 1;
	alter table element_type auto_increment = 1;
	alter table `attribute` auto_increment = 1;
	
	insert into account (email, password, password_salt) values
		("a@a.com", "a", "test"),
		("b@b.com", "b", "test");
	
	insert into directory (account_id, parent_directory, directory_name) values
		(1, null, "root-directory"),
		(2, null, "root-directory"),
		(1, 1, "subdirectory-test"),
		(2, 2, "sub-directory");
	
	insert into document_type (document_type_name) values
		("NOTE"),
		("TODO"),
		("UML");
	
	insert into document (document_type_id, document_name, directory_id) values
		(2, "user1-todo", 1),
		(3, "user1-uml", 1),
		(1, "user1-note", 3),
		(1, "user2-note", 2),
		(2, "user2-todo", 2),
		(3, "user2-uml", 2);
	
	insert into element_type (`type`) values
		("LINE"),
		("BOX"),
		("TEXT"),
		("CLASS_BOX"),
		("INTERFACE"),
		("ARROW"),
		("TODO_GROUP"),
		("TODO");
	
	insert into element (element_type_id, document_id) values
		(1, 2), -- line for user 1, doc 2 UML, in directory 1
		(2, 2), -- box for user 1, doc 2 UML, in directory 1
		(3, 4); -- text for user 2, doc 4 note, directory 2
	
	insert into `attribute` (element_id, `key`, value) values
		(1, "startXPos",  	"0"), -- lines start X (0, 0)
		(1, "startYPos", 	"0"), -- lines start Y (0, 0)
		(1, "endXPos", 		"1"), -- lines end X (1, 0)
		(1, "endYPos", 		"0"), -- lines end Y (1, 0)
		(2, "xPos", 		"0"), -- boxes xPos (0, 0)
		(2, "yPos", 		"0"), -- boxes yPos (0, 0)
		(2, "width", 		"2"), -- boxes width
		(2, "height", 		"2"), -- boxes height
		(3, "xPos", 		"0"), -- texts xPosition (0, 0)
		(3, "yPos", 		"0"), -- texts yPosition (0, 0)
		(3, "innerText", 	"This is the inner text for the text field"); -- texts inner text
	
	insert into document_element_link (element_id, document_id) values
		(2, 1), -- link user 1's UML box to a todo
		(2, 3); -- link user 1's UML box to a note
		
END
delimiter ;


call set_known_good_state();

select * from `attribute`;