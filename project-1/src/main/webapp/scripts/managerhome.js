	console.log(JSON.parse(sessionStorage.getItem('currentUser')).userId)
	
	document.querySelector('body').style.color = 'blue';
	// capture the welcome element and modofy it so that it says welcome + username
	let welcome = document.getElementById('welcome');


	
	// capture the userString by accessing the session.....
	// let userString = sessionStorage.getItem('currentUser');
	
	// set up some logic....
	// IF the user is null....redirect them to the index.html page ("http://localhost:8080/EmployeeDBServlets/")
	// if (userString === null) {
	// 	window.location = "http://localhost:8080/project-1/";
	// } else {
		
	// 	let currentUser = JSON.parse(userString); // parse the data that we se == to that attribute
		
	// 	console.log(currentUser);
		
	// 	if (currentUser != null) {
			
	// 		welcome.innerHTML = "Welcome " + currentUser.firstName + " " + currentUser.lastName;
			
	// 	}
		
	// }

	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = populateExpenseTable; 
	xhr.open('GET', "http://localhost:8080/project-1/expenseRequests")
	xhr.send()

	function populateExpenseTable() {
		if (this.readyState === 4 && this.status === 200) {
			let expenseList = JSON.parse(this.responseText)
			console.log(expenseList)


			welcome.addEventListener("click", function(event) {

			})

			let expenseTableBody = document.querySelector('.exp-table tbody')
			for (let i=0; i < expenseList.length; i++) {
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

				let authorName = document.createElement('td')
				expenseRow.append(authorName)
				authorName.innerHTML = expenseList[i].author.firstName + expenseList[i].author.lastName

				let authorId = document.createElement('td')
				expenseRow.append(authorId)
				authorId.innerHTML = expenseList[i].author.userId
				  
				let status = document.createElement('td')
				expenseRow.append(status)
				status.innerHTML = expenseList[i].status
				
				let resolved = document.createElement('td')
				expenseRow.append(resolved)
				if (expenseList[i].resolved == false) {
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

				let resolverName = document.createElement('td')
				expenseRow.append(resolverName)
				resolverName.innerHTML = expenseList[i].resolver.firstName + expenseList[i].resolver.lastName

				let resolverId = document.createElement('td')
				expenseRow.append(resolverId)
				resolverId.innerHTML = expenseList[i].resolver.userId

				expenseRow.addEventListener("click", function() {
					let expense = JSON.stringify(expenseList[i])
					
					sessionStorage.setItem('expense', expense)
					window.location = "http://localhost:8080/project-1/expense-detail-manager.html"	

				})
			}
		}
	}



	// function logout() {
		
	// 	let xhr = new XMLHttpRequest();
		
	// 	xhr.open("POST", "http://localhost:8080/project-1/logout");
	// 	xhr.send();
		
	// 	sessionStorage.removeItem('currentUser');
	// 	window.location = "http://localhost:8080/project-1/";
		
	// }