document.getElementById('loginForm').addEventListener('submit', sendLogin);

function sendLogin(event) {
    event.preventDefault();
	console.log("send login triggered");

    let uName = document.getElementById('uName').value;
    let pWord = document.getElementById('pWord').value;

    console.log(`Username: ${uName}`);
    console.log(`Password: ${pWord}`);

    // building an obj literal with the user credentials
    let loginTemplate = {
        username: uName,
        password: pWord
    }

    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            console.log("success");

            sessionStorage.setItem('currentUser', this.responseText)
            console.log(sessionStorage.getItem('currentUser'));

            var currentUser = JSON.parse(sessionStorage.getItem('currentUser'));
            console.log(currentUser);
            
            if (currentUser.roleId == 1) {
            
            window.location = "http://localhost:8080/project-1/managerhome.html";

            } else if (currentUser.roleId == 2) {
            
            window.location = "http://localhost:8080/project-1/employeehome.html";
            } 
        }

        if (this.readyState === 4 && this.status === 204) { // 204 means NO CONTENT FOUND (but connection was made)

            console.log("failed to find user");

            let childDiv = document.getElementById('warningText');
            childDiv.textContent = "Failed to login!  Username or Password is incorrect"
        }
    }    
    xhr.open("POST", "http://localhost:8080/project-1/login")
    xhr.send(JSON.stringify(loginTemplate))

}