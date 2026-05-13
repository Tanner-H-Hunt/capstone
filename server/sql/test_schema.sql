DROP database IF EXISTS capstone_test;
CREATE database capstone_test;
use capstone_test;

CREATE TABLE account(
	account_id int PRIMARY KEY auto_increment,
	email varchar(255) UNIQUE NOT NULL,
	password varchar(50) NOT NULL,
	password_salt varchar(8) NOT null
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

CREATE TABLE document_element(
	document_element_id int PRIMARY KEY auto_increment,
	element_type_id int NOT NULL,
	document_id int NOT NULL,
	
	CONSTRAINT fk_element_type
	FOREIGN KEY (element_type_id)
	REFERENCES element_type(element_type_id),
	
	CONSTRAINT fk_element_document
	FOREIGN KEY (document_id)
	REFERENCES document(document_id)
);

CREATE TABLE document_element_link(
	document_element_link_id int primary KEY auto_increment,
	element_id int NOT NULL,
	document_id int NOT NULL,
	
	CONSTRAINT fk_link_element
	FOREIGN KEY (element_id)
	REFERENCES document_element(document_element_id),
	
	CONSTRAINT fk_link_document
	FOREIGN KEY (document_id)
	REFERENCES document(document_id)
);

CREATE TABLE attribute_type(
	attribute_type_id int PRIMARY KEY auto_increment,
	attribute_name varchar(50)
);

CREATE TABLE `attribute`(
	attribute_id int PRIMARY KEY auto_increment,
	document_element_id int NOT NULL,
	attribute_type_id int NOT NULL,
	value TEXT NOT NULL,
	
	CONSTRAINT fk_attribute_element
	FOREIGN KEY (document_element_id)
	REFERENCES document_element(document_element_id),
	
	CONSTRAINT fk_attribute_type
	FOREIGN KEY (attribute_type_id)
	REFERENCES attribute_type(attribute_type_id)
);

delimiter //
CREATE PROCEDURE set_known_good_state()
BEGIN
	DELETE FROM `attribute`;
	DELETE FROM attribute_type;
	DELETE FROM document_element_link;
	delete from document_element;
	delete from document;
	delete from document_type;
	delete from directory;
	delete from account;

	alter table `attribute` auto_increment = 1;
	alter table attribute_type auto_increment = 1;
	alter table document_element_link auto_increment = 1;
	alter table document_element auto_increment = 1;
	alter table document auto_increment = 1;
	alter table document_type auto_increment = 1;
	alter table directory auto_increment = 1;
	alter table account auto_increment = 1;
	
	insert into account (email, password, password_salt) values
		("a@a.com", "a", "test"),
		("b@b.com", "b", "test");
	
	insert into directory (account_id, parent_directory, directory_name) values
		(1, null, "root-directory"),
		(2, null, "root-directory"),
		(1, 1, "subdirectory-test"),
		(2, 2, "");
	
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
		
END
delimiter ;