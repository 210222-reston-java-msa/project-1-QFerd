//-------------Add event listener to go back
document.getElementById("go-back-btn").addEventListener("click", goBack);

function goBack() {
    window.location = "http://localhost:8080/project-1/employeehome.html";
  }

//Changing username in session to affect employee home page welcome sign: should do this
//a better way
document.getElementById("emp-info").addEventListener("submit", function() {
    var currentUser = JSON.parse(sessionStorage.getItem("currentUser"));
    currentUser.username = document.getElementById("username").innerHTML
    sessionStorage.setItem("currentUser", JSON.stringify(currentUser))

})