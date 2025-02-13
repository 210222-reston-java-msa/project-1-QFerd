drop table reimb_type;

create table reimb_status (
	status_id integer primary key,
	status_name varchar(10) unique not null
);

create table reimb_type (
	type_id integer primary key,
	type_name varchar(10) unique not null
);

create table user_roles (
	role_id integer primary key,
	role_name varchar(10) unique not null
);

create table users (
	user_id serial primary key,
	username varchar(50) unique not null,
	pw varchar(50) not null,
	first_name varchar(100) not null,
	last_name varchar(100) not null,
	email varchar(150),
	role_id integer not null references user_roles(role_id)
);

alter table users add column secure_pw varchar;
alter table users add column salt varchar;
alter table users drop column pw;

create table reimbursements (
	reimb_id serial primary key,
	amount numeric(20, 2) default 0 not null,
	submitted timestamp not null,
	resolved timestamp not null,
	description varchar(250),
	author integer not null references users(user_id),
	resolver integer references users(user_id),
	status_id integer not null references reimb_status(status_id),
	type_id integer not null references reimb_type(type_id)
);

alter table reimbursements alter column status_id set default 1;
alter table reimbursements alter column resolved drop not null;
alter table reimbursements rename column author to author_id;
alter table reimbursements rename column resolver to resolver_id;
alter table reimbursements alter column submitted set default now();
alter table reimbursements alter column submitted drop not null;
alter table reimbursements add column receipt bytea;


show timezone;