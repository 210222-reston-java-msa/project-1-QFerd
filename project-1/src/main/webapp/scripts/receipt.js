var expense = JSON.parse(sessionStorage.getItem('expense'))
//Should be object:
console.log(expense);

if (expense.receipt === null) {

} else {
    document.getElementById("receipt").src = "data:image/png;base64," + expense.receiptBytes;
}