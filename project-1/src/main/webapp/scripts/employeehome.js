    document.getElementById('logout-btn').addEventListener('click', logout);

	
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

	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = populateExpenseTable; 
	xhr.open('GET', "http://localhost:8080/project-1/expense-requests")
	xhr.send()

	function populateExpenseTable() {
		if (this.readyState === 4 && this.status === 200) {
			sessionStorage.setItem('expenseList', this.responseText);
			console.log(sessionStorage.getItem('expenseList'));

			var expenseList = JSON.parse(sessionStorage.getItem('expenseList'))
			console.log(expenseList)

			var currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
			console.log(currentUser)

			let expenseTableBody = document.querySelector('.exp-table tbody')
			for (let i=0; i < expenseList.length; i++) {
				if (expenseList[i].author.userId === currentUser.userId) {
					let expenseRow = document.createElement('tr')
					expenseTableBody.append(expenseRow)
					
					let submitted = document.createElement('td')
					expenseRow.append(submitted)
					submitted.innerHTML = new Intl.DateTimeFormat('default', {
						year: 'numeric',
						month: 'numeric',
						day: 'numeric',
						hour: 'numeric',
						minute: 'numeric',
						timeZoneName: 'short'
					}).format(expenseList[i].submitted)


					let status = document.createElement('td')
					expenseRow.append(status)
					status.innerHTML = expenseList[i].status
					
					let resolved = document.createElement('td')
					expenseRow.append(resolved)
					if (expenseList[i].resolved == null) {
						resolved.innerHTML = "-";
					} else {
						resolved.innerHTML = new Intl.DateTimeFormat('default', {
							year: 'numeric',
							month: 'numeric',
							day: 'numeric',
							hour: 'numeric',
							minute: 'numeric',
							timeZoneName: 'short'
						}).format(expenseList[i].resolved)
					}
	
					let amount = document.createElement('td')
					expenseRow.append(amount)
					amount.innerHTML = expenseList[i].amount
					
					let type = document.createElement('td')
					expenseRow.append(type)
					type.innerHTML = expenseList[i].type

					let description = document.createElement('td')
					expenseRow.append(description)
					description.innerHTML = expenseList[i].description

				}
			}
		}
	}
	
	function logout() {
		
		let xhr = new XMLHttpRequest();
		
		xhr.open("POST", "http://localhost:8080/project-1/logout");
		xhr.send();
		
		sessionStorage.removeItem('currentUser');
		window.location = "http://localhost:8080/project-1/";
		
	}