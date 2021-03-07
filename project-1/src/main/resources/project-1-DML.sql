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

INSERT INTO users (username, pw, first_name, last_name, email, role_id) 
	VALUES ('t', 't', 't', 't', 't', 2);
delete from users where id = 