document.getElementById('submit-btn').addEventListener('click', newExpense);

function newExpense() {
    //cache variables
    let type = document.getElementById('type').value;
    let amt = document.getElementById('amt').value;
    let desc = document.getElementById('desc').value;
    let expense = {type:type, amount:amt, description:desc};

    console.log(expense);

    let xhr = new XMLHttpRequest();
    xhr.open("POST", "http://localhost:8080/project-1/expense");
    xhr.send(JSON.stringify(expense));

}