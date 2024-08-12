import React, { useState } from "react";
import "./LoginPage.css";

function LoginPage() {
  const [email, setEmail] = useState("");
  localStorage.setItem("loggedInEmail", email);

  const handleSubmit = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8080/user?email=${encodeURIComponent(email)}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok.");
        }
        return response.json();
      })
<<<<<<< HEAD
      .then((response) => {
        console.log("Log in successful.");
=======
      .then((data) => {
        console.log("Log in successful.", data);
>>>>>>> origin/main
        window.location.href = "/";
      })
      .catch((error) => {
        console.error("There was a problem with the fetch operation:", +error);
      });
  };
  return (
    <div>
      <div className="login_container">
        <h4 className="login_form_header">Login</h4>
        <form className="login_form" onSubmit={handleSubmit}> 
          <div className="email_input">
            <input
              type="text"
              id="username"
              name="username"
              placeholder="E-mail"
              className="login_email_text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <input
            type="submit"
            value="Login"
            className="login_button"
          />
        </form>
        <a className="account_creation_link" href="/AccountRegistration">
          No account? Click here to sign Up!
        </a>
      </div>
    </div>
  );
}
export default LoginPage;
