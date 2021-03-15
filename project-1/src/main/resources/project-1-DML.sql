insert into user_roles (role_id, role_name)
	values (1, 'Manager'),
			(2, 'Employee');


insert into reimb_status (status_id, status_name)
	values (1, 'Pending'),
			(2, 'Approved'),
			(3, 'Denied');
			
insert into reimb_type (type_id, type_name)
	values (1, 'Travel'),
			(2, 'Training'),
			(3, 'Supplies');	

insert into users (username, pw, first_name, last_name, email, role_id) 
	values ('Kelly', 'kepw', 'Kelly', 'Eaton', 'ke@gmail.com', 1),
		 ('Paul', 'pcpw', 'Paul', 'Cook', 'pc@gmail.com', 2);
select * from reimbursements;
update reimbursements 
	set resolved = now(),
		resolver_id = 3
		status_id =2
	where reimb_id = 1;
 

insert into reimbursements (amount, description, author_id, type_id)
	values (100, 'Dinner', 4, 1);

insert into reimbursements (amount, description, author_id, type_id)
	values (300, 'Plane ticket', 6, 1);


insert into users (username, pw, first_name, last_name, email, role_id)
			values ('Ellen', 'edpw', 'Ellen', 'Dean', 'ed@gmail.com', 2);
insert into users (username, pw, first_name, last_name, email, role_id, secure_pw, salt)
	values ('John', 'jdpw', 'John', 'Doe', 'jd@gmail.com', 2, 'iv/DtFX3wIFpCN4bV58OOt6r9OkrlTTD1Ht6gvl47g8=', 'brZq35dHB3sp6TsDIYtUYikqhjCXHS');
delete from users where user_id = 7;
update users 
	set email = 'pcook755@yahoo.com'
	where user_id = 4;

update users
	set secure_pw = 'RCZGEHe7n0c7w9hPsN5htAuojufjA3Y5WNUFxX8OEWU=', salt = 'T5iJOzJapiFGQvbv8rYDvJ7v9PXNPh'
	where user_id = 5;
	
update reimbursements 
	set description = 'Lunch with client'
	where reimb_id = 2;

insert into reimbursements (amount, description, author_id, status_id, type_id)
	values(200, 'Certification', 4, 1, 2);

delete from reimbursements where reimb_id in(28);
	
	
	

delete from users where user_id in(1,2);

select reimb_id, amount, submitted, resolved, description, 
author_id, authors.first_name as author_first_name, authors.last_name as author_last_name, authors.email as author_email, 
resolver_id, resolvers.first_name as resolver_first_name, resolvers.last_name as resolver_last_name, 
rs.status_id, rs.status_name, rt.type_id, rt.type_name, receipt
from reimbursements
	left join users authors on reimbursements.author_id = authors.user_id
	left join users resolvers on reimbursements.resolver_id = resolvers.user_id 
	left join reimb_type rt on reimbursements.type_id = rt.type_id 
	left join reimb_status rs on reimbursements.status_id = rs.status_id
	order by submitted desc;

update users 
	set username = 'Paul'
	where user_id = 4;
	