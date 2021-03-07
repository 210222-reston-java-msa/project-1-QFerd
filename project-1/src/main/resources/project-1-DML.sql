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

delete from users where user_id in(1,2);

select reimb_id, amount, submitted, resolved, description, 
author_id, authors.first_name as author_first_name, authors.last_name as author_last_name, authors.email as author_email, 
resolver_id, resolvers.first_name as resolver_first_name, resolvers.last_name as resolver_last_name, 
rs.status_id, rs.status_name, rt.type_id, rt.type_name 
from reimbursements
	inner join users authors on reimbursements.author_id = authors.user_id
	inner join users resolvers on reimbursements.resolver_id = resolvers.user_id 
	inner join reimb_type rt on reimbursements.type_id = rt.type_id 
	inner join reimb_status rs on reimbursements.status_id = rs.status_id;