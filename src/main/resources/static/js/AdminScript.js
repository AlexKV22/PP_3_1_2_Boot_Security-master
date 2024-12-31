document.addEventListener('DOMContentLoaded', function fetchUsers () {
    fetch(`/rest`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Does not load all users");
            }
            return response.json();
        })
        .then(users => {
            const tableBody = document.getElementById("tableBody");
            tableBody.innerHTML = '';
            users.forEach((user, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `        <td>${user.id}</td>
                                         <td>${user.username}</td>
                                         <td>${user.lastName}</td>
                                         <td>${user.age}</td>
                                         <td>${user.email}</td>
                                         <td>${user.roles.map(role => role.name).join(",")}</td>
                                         <td><button onclick="editUser(${user.id})">Edit</button></td>
                                         <td><button onclick="confirmDeleteUser(${user.id})">Delete</button></td>`;
                index % 2 === 0? row.classList.add('even-row') : row.classList.add('odd-row');
                tableBody.appendChild(row)
            });
        })
        .catch(error => {
            console.error("error", error);
        });
})


function editUser(id) {
    fetch(`rest/findUser/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Does not load user by id");
            }
            return response.json();
        })
        .then(user => {
            document.getElementById("userId").value = user.id;
            document.getElementById("username").value = user.username;
            document.getElementById("lastName").value = user.lastName;
            document.getElementById("age").value = user.age;
            document.getElementById("email").value = user.email;

            $("#editModal").modal("show");


        })
        .catch(error => {
            console.error("Ошибка", error)
        });

}

function updateUser() {
    // const userId = document.getElementById("userId").value
    const updatedUser = {
        id: document.getElementById("userId").value,
        username: document.getElementById("username").value,
        lastName: document.getElementById("lastName").value,
        age: document.getElementById("age").value,
        email: document.getElementById("email").value,
        password: document.getElementById("passwordEdit").value,
        roles: []
    };
    const selectedRoleId = document.getElementById('rolesEdit').value;
    if (selectedRoleId) {
        updatedUser.roles.push({ id: selectedRoleId });
    }
    fetch(`rest/updateUser`, {
        method:"PUT",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedUser)
    })
        .then(responce => {
            if (!responce.ok) {
                throw new Error("Error then save user")
            }
            location.reload();
        })
        .catch(error => {
            console.error("error" + error)
        })
}



function confirmDeleteUser(id) {
    fetch(`rest/findUser/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Does not load user by id");
            }
            return response.json();
        })
        .then(user => {
            document.getElementById("userIdDelete").value = user.id;
            document.getElementById("usernameDelete").value = user.username;
            document.getElementById("lastNameDelete").value = user.lastName;
            document.getElementById("ageDelete").value = user.age;
            document.getElementById("emailDelete").value = user.email;

            $("#deleteModal").modal("show");


        })
        .catch(error => {
            console.error("Ошибка", error)
        });
}

function deleteUser() {
    const userId = document.getElementById("userIdDelete").value
    fetch(`rest/delete/${userId}`, {
        method:"DELETE",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then(responce => {
            if (!responce.ok) {
                throw new Error("Error then delete user")
            }
            location.reload();
        })
        .catch(error => {
            console.error("error" + error)
        })
}

function saveUser() {
    const savedUser = {
        username: document.getElementById("usernameSave").value,
        lastName: document.getElementById("lastNameSave").value,
        age: document.getElementById("ageSave").value,
        email: document.getElementById("emailSave").value,
        password: document.getElementById("passwordSave").value,
        roles: []
    };
    const selectedRoleId = document.getElementById('rolesSave').value;
    if (selectedRoleId) {
        savedUser.roles.push({ id: selectedRoleId });
    }
    fetch(`rest/addUser`, {
        method:"POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(savedUser)
    })
        .then(responce => {
            if (!responce.ok) {
                throw new Error("Error then save user")
            }
            location.reload();
        })
        .catch(error => {
            console.error("error" + error)
        })
}


function closeModal() {
    $('#editModal').modal('hide');
    $('#deleteModal').modal('hide');
    $('#saveModal').modal('hide');
}


document.addEventListener('DOMContentLoaded', function getRoles () {
    fetch(`rest/roles`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Does not load all roles");
            }
            return response.json();
        })
        .then(roles => {
            const selectRoles = document.getElementById("rolesSave");
            const updatedRoles = document.getElementById("rolesEdit");
            roles.forEach(role => {
                const optionEdit = document.createElement('option');
                optionEdit.value = role.id;
                optionEdit.textContent = role.name;
                const optionSave = document.createElement('option');
                optionSave.value = role.id;
                optionSave.textContent = role.name;
                selectRoles.appendChild(optionSave);
                updatedRoles.appendChild(optionEdit);
            });
        })
        .catch(error => {
            console.error("error", error);
        });
})

document.addEventListener('DOMContentLoaded', function getPrincipalInfo() {
    fetch(`rest/getPrincipal`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Does not load principal info");
            }
            return response.json();
        })
        .then(user => {
             const roles = Array.from(user.roles).map(role => role.name);
            document.getElementById("principalName").innerText = user.username;
            document.getElementById("principalRoles").innerText = roles.join(',');
        })
        .catch(error => {
            console.error("error", error);
        });
})





