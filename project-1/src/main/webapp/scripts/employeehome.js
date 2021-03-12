

	let welcome = document.getElementById('welcome');

	let userString = sessionStorage.getItem('currentUser');
	
	//===================REDIRECT TO INDEX 
	if (userString === null) {
		window.location = "http://localhost:8080/project-1/";
	} else {
		
		let currentUser = JSON.parse(userString); // parse the data that we se == to that attribute
		console.log(currentUser);
		
		if (currentUser != null) {
			
			welcome.innerHTML = "Welcome " + currentUser.firstName + " " + currentUser.lastName;	
		}
	}

	//==================POPULATE EXPENSE TABLE
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

			//------------ADD FILTER
			function filter() {
			var input, filter, table, tr, td, i, txtValue;
			input = document.getElementById("search");
			filter = input.value.toUpperCase();
			table = document.getElementById("exp-table");
			tr = table.querySelectorAll("#exp-table tbody tr");
			if (input.value != "" && input.value != "All"){}
				for (i = 0; i < tr.length; i++) {
					td = tr[i].getElementsByTagName("td")[0];
					if (td) {
					txtValue = td.textContent || td.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						tr[i].style.display = "";
					} else {
						tr[i].style.display = "none";
					}
					}
				}
			}
			}
		}
	}
	//===========================LOGOUT
	document.getElementById('logout-btn').addEventListener('click', function () {
		
		let xhr = new XMLHttpRequest();
		
		xhr.open("POST", "http://localhost:8080/project-1/logout");
		xhr.send();
		
		sessionStorage.clear();
		window.location = "http://localhost:8080/project-1/";
		
	})