//==============================USING FORM DATA
window.addEventListener("load", function () {
    //Define function for submit event below
    function newExpense() {
        const xhr = new XMLHttpRequest();

        // Bind the FormData object and the form element (form variable is hoisted)
        const FD = new FormData(form);

        // Define what happens on successful data submission
        xhr.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 200) {
                window.location = "http://localhost:8080/project-1/employeehome.html"
            }
        }
        xhr.open("POST", "http://localhost:8080/project-1/new-expense");
        xhr.send(FD);
    }

    // Access the form element...
    const form = document.getElementById("expense-form");

    // ...and take over its submit event.
    form.addEventListener("submit", function (event) {
        event.preventDefault();
        newExpense();
    });
});




//                    archive

//===================READ FILE
// // These variables are used to store the form data
// const file = {
//     dom: document.getElementById("receipt"),
//     binary: null
// };

// // Use the FileReader API to access file content
// const reader = new FileReader();

// // Because FileReader is asynchronous, store its
// // result when it finishes reading the file
// reader.addEventListener("load", function () {
//     file.binary = reader.result;
// });

// // Read the file once the user selects it.
// file.dom.addEventListener("change", function () {
//     reader.readAsBinaryString(file.dom.files[0]);
// });




// }

// //====================NEW EXPENSE BUTTON
// document.getElementById('submit-btn').addEventListener('click', newExpense);
// function newExpense() {
//     //cache variables
//     let type = document.getElementById('type').value;
//     let amt = document.getElementById('amt').value;
//     let desc = document.getElementById('desc').value;
//     let expense = { type: type, amount: amt, description: desc };

//     console.log(expense);
//     // If there is a selected file, wait until it is read
//     // If there is not, delay the execution of the function
//     if (!file.binary && file.dom.files.length > 0) {
//         setTimeout(newExpense, 10);
//         return;
//     }

//     let xhr = new XMLHttpRequest();

//     // We need a separator to define each part of the request
//     const boundary = "blob";

//     let data = "";
//     // So, if the user has selected a file
//     if (file.dom.files[0]) {
//         // Start a new part in our body's request
//         data += "--" + boundary + "\r\n";

//         // Describe it as form data
//         data += 'content-disposition: form-data; '
//             // Define the name of the form data
//             + 'name="' + file.dom.name + '"; '
//             // Provide the real name of the file
//             + 'filename="' + file.dom.files[0].name + '"\r\n';
//         // And the MIME type of the file
//         data += 'Content-Type: ' + file.dom.files[0].type + '\r\n';

//         // There's a blank line between the metadata and the data
//         data += '\r\n';

//         // Append the binary data to our body's request
//         data += file.binary + '\r\n';
//     }

//     xhr.onreadystatechange = function () {
//         if (this.readyState === 4 && this.status === 200) {
//             window.location = "http://localhost:8080/project-1/employeehome.html"
//         }
//     }
//     xhr.open("POST", "http://localhost:8080/project-1/new-expense");
//     xhr.send(JSON.stringify(expense));

// }
