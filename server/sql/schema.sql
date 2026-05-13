DROP database IF EXISTS capstone;
CREATE database capstone;
use capstone;

CREATE TABLE account(
	account_id int PRIMARY KEY auto_increment,
	email varchar(255) UNIQUE NOT NULL,
	password varchar(50) NOT NULL,
	password_salt varchar(32) NOT null
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

select * from account;
delete from account where account_id = 3;