import React, { useState } from "react";
import "./LoginPage.css";

function LoginPage() {
  const [email, setEmail] = useState("");
  const handleSubmit = (e) => {
    e.preventDefault();
    fetch("http://localhost:8080/user?email=" + email, {
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
      .then((response) => {
        console.log("Log in successful.");
        window.location.href = "/TestHomePage";
      })
      .catch((error) => {
        console.error("There was a problem with the fetch operation:", +error);
      });
  };
  return (
    <div>
      <div className="login_container">
        <h4 className="login_form_header">Login</h4>
        <form className="login_form">
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
            onClick={handleSubmit}
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
