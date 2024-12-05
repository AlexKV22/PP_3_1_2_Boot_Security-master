document.addEventListener('DOMContentLoaded', function fetchUser () {
    fetch(`/restUser`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Does not load user");
            }
            return response.json();
        })
        .then(user => {
            const roles = Array.from(user.roles).map(role => role.name);
            document.getElementById("principalNameUser").innerText = user.username;
            document.getElementById("principalRolesUser").innerText = roles.join(',');
            const tableBody = document.getElementById("tableBodyUser");
            tableBody.innerHTML = '';
            const row = document.createElement('tr');
            row.innerHTML = `        <td>${user.id}</td>
                                         <td>${user.username}</td>
                                         <td>${user.lastName}</td>
                                         <td>${user.age}</td>
                                         <td>${user.email}</td>
                                         <td>${user.roles.map(role => role.name).join(",")}</td>`;

            tableBody.appendChild(row)
        })
        .catch(error => {
            console.error("error", error);
        });
})
