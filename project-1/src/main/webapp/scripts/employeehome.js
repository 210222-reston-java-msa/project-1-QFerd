    document.getElementsByClassName('logout-btn').addEventListener('click', logout);
    document.getElementsByClassName('new-expense-btn').addEventListener('click', newExpense);
	
    // capture the welcome element and modofy it so that it says welcome + username
	let welcome = document.getElementById('welcome');
	
	// capture the userString by accessing the session.....
	let userString = sessionStorage.getItem('currentUser');
	
	// set up some logic....
	// IF the user is null....redirect them to the index.html page ("http://localhost:8080/EmployeeDBServlets/")
	if (userString === null) {
		window.location = "http://localhost:8080/project-1/";
	} else {
		
		let currentUser = JSON.parse(userString); // parse the data that we se == to that attribute
		
		console.log(currentUser);
		
		if (currentUser != null) {
			
			welcome.innerHTML = "Welcome " + currentUser.firstName + " " + currentUser.lastName;
			
		}
		
	}

	
	function logout() {
		
		let xhr = new XMLHttpRequest();
		
		xhr.open("POST", "http://localhost:8080/project-1/logout");
		xhr.send();
		
		sessionStorage.removeItem('currentUser');
		window.location = "http://localhost:8080/project-1/";
		
	}