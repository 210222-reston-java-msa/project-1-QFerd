//cache buttons
var approveBtn = document.getElementById("approve")
var denyBtn = document.getElementById("deny")
var goBackBtn = document.getElementById("go-back")
var receiptBtn = document.getElementById("receipt-btn")

//cache form fields
var reimbId = document.getElementById('reimb-id')
var submitted = document.getElementById('submitted')
var empName = document.getElementById('emp-name')
var empId = document.getElementById('emp-id')
var expType = document.getElementById('exp-type')
var expAmt = document.getElementById('exp-amt')
var desc = document.getElementById('description')
var status = document.getElementById('status')
var resolved = document.getElementById('resolved')
var resolverName = document.getElementById('resolver-name')
var resolverId = document.getElementById('resolver-id')

//Should show string:
console.log(sessionStorage.getItem('expense'))
var expense = JSON.parse(sessionStorage.getItem('expense'))
//Should be object:
console.log(expense);

//======================POPULATE TABLE
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
document.getElementById('status').innerHTML = expense.status

if (expense.resolved === null) {
  resolved.innerHTML = "-";
} else {
  resolved.innerHTML = new Intl.DateTimeFormat('default', {
    year: 'numeric',
    month: 'numeric',
    day: 'numeric',
    hour: 'numeric',
    minute: 'numeric',
    timeZoneName: 'short'
    }).format(expense.resolved)
}

if (expense.resolver.firstName === null && expense.resolver.lastName === null) {
  resolverName.innerHTML ="-";
} else {
  resolverName.innerHTML = expense.resolver.firstName + " " + expense.resolver.lastName
}

if (expense.resolver.userId === 0) {
  resolverId.innerHTML = "-";
} else {
  resolverId.innerHTML = expense.resolver.userId
}

//-------------Add event listener to go back
goBackBtn.addEventListener("click", goBack);

//------------Add event listener to approve/deny
approveBtn.addEventListener("click", approve)
denyBtn.addEventListener("click", deny)

//------------Add event listener to view receipt
receiptBtn.addEventListener("click", viewReceipt)

//===================APPROVE/DENY/GO BACK BUTTONS/VIEW RECEIPT
function approve() {
  console.log("button clicked");

  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState === 4 && this.status === 200) {
      console.log("In if statement")
      window.location = "http://localhost:8080/project-1/managerhome.html"
    }   
  }
  xhr.open("POST", "http://localhost:8080/project-1/manage-request");
  let currentUser = JSON.parse(sessionStorage.getItem('currentUser'))
  expense.resolver.userId = currentUser.userId
  expense.statusId = 2;
  xhr.send(JSON.stringify(expense));
}

function deny() {
  console.log("button clicked");

  let xhr = new XMLHttpRequest();
  xhr.onreadystatechange = function () {
    if (this.readyState === 4 && this.status === 200) {
      window.location = "http://localhost:8080/project-1/managerhome.html"
    }
  }
  xhr.open("POST", "http://localhost:8080/project-1/manage-request");
  let currentUser = JSON.parse(sessionStorage.getItem('currentUser'))
  expense.resolver.userId = currentUser.userId
  expense.statusId = 3;
  xhr.send(JSON.stringify(expense));

}

function goBack() {
  window.location = "http://localhost:8080/project-1/managerhome.html";
}

function viewReceipt() {
  window.location = "http://localhost:8080/project-1/receipt.html"
}
















//ARCHIVE


      // var currentUser = JSON.parse(sessionStorage.getItem('currentUser'));

      // console.log(currentUser);
      // //Modify expense 
      // expense.resolver.userId = currentUser.userId
      // expense.resolved = new Date() 
      // expense.status = "Approved";
      // expense.statusId = 2;
      // expense.resolver.firstName = currentUser.firstName;
      // expense.resolver.lastName = currentUser.lastName;

      //----------Return to server to update resource
      // let xhr = new XMLHttpRequest();
      // xhr.onreadystatechange = function() {
        // if (this.readyState === 4 && this.status === 200) {
          // //Reflect changes in html
          // status.innerHTML = expense.status
          // resolved.innerHTML = expense.resolved;
          // resolverName.innerHTML = expense.resolver.firstName + " " + expense.resolver.lastName;
          // resolverId.innerHTML = expense.resolver.userId;
    
          //Update session stored 'expenseList'
          //Should be string:
          // console.log(sessionStorage.getItem('expenseList'))
          
          // var expenseList = JSON.parse(sessionStorage.getItem('expenseList'));
          //Should be object:
          // console.log(expenseList);
    
          // for (let i = 0; i < expenseList.length; i++) {
          //   if (expenseList[i].reimbId === expense.reimbId) {
          //     expenseList[i].resolver.firstName = expense.resolver.firstName;
          //     expenseList[i].resolver.lastName = expense.resolver.lastName;
          //     sessionStorage.setItem('expenseList', JSON.stringify(expenseList));
          //     //should be string
          //     console.log(sessionStorage.getItem('expenseList'))
            // }


          // }

          // xhr.open("POST", "http://localhost:8080/project-1/manage-request");
          // xhr.send(JSON.stringify(expense));