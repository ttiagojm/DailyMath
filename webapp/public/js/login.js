document.addEventListener("DOMContentLoaded", function() {

    const form = document.getElementById("loginForm");

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const username = document.getElementById("username").value;

        digestMessage(document.getElementById("password").value)
        .then((passwd) =>
            fetch("/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    password: passwd
                })
            })
            .then(response => {
                console.log(response)
                if(response.ok){
                    url = new URL("/admin", window.location.href);
                    window.location.replace(url);
                } else if(response.status == 401) {
                    alert("Wrong credentials!");
                }
            })
            .catch(error => {
                console.error("Error:", error);
            })
        );
    });

});

async function digestMessage(message) {
    const msgUint8 = new TextEncoder().encode(message); // encode as (utf-8) Uint8Array
    const hashBuffer = await crypto.subtle.digest("SHA-256", msgUint8); // hash the message
    const hashArray = Array.from(new Uint8Array(hashBuffer)); // convert buffer to byte array
    const hashHex = hashArray
      .map((b) => b.toString(16).padStart(2, "0"))
      .join(""); // convert bytes to hex string
    return hashHex;
  }