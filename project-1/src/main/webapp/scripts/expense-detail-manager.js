console.log(sessionStorage.getItem('expense'));

expense = JSON.parse(sessionStorage.getItem('expense'))

//cache buttons
let approveBtn = document.getElementById("approve")
let denyBtn = document.getElementById("deny")
let goBackBtn = document.getElementById("go-back")

//cache form fields
let reimbId = document.getElementById('reimb-id')
let submitted = document.getElementById('submitted')
let empName = document.getElementById('emp-name')
let empId = document.getElementById('emp-id')
let expType = document.getElementById('exp-type')
let expAmt = document.getElementById('exp-amt')
let desc = document.getElementById('desc')
let status = document.getElementById('status')

reimbId.innerHTML = expense.reimbId
submitted.innerHTML = new Intl.DateTimeFormat('default', {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    timeZoneName: 'short'
  }).format(expense.submitted)
empName.innerHTML = expense.author.firstName + " " + expense.author.lastName
empId.innerHTML = expense.author.userId
expType.innerHTML = expense.type
expAmt.innerHTML = "$" + expense.amount
desc.innerHTML = expense.description
status.innerHtml = expense.status

if (expense.status = "Pending") {
  approveBtn.addEventListener("click", function () {
    
      let xhr = new XMLHttpRequest();
      xhr.open("POST", "http://localhost:8080/project-1/manageRequest")
      JSON.parse(sessionStorage.getItem('currentUser')).userId = expense.resolver.userId
      JSON.parse(sessionStorage.getItem('currentUser')).resolved = new Date() 
      xhr.send(JSON.stringify(expense))

  })
}