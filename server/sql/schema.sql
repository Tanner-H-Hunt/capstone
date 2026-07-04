DROP database IF EXISTS capstone;
CREATE database capstone;
use capstone;

CREATE TABLE account(
	account_id int PRIMARY KEY auto_increment,
	email varchar(255) UNIQUE NOT NULL,
	password varchar(72) NOT NULL
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
select * from account;