import React from "react";
import "./LoginPage.css";
import LoginButton from "./LoginButton";

function LoginPage() {
  return (
    <div>
      <div className="login">
        <h4 className="login_form_header">Login</h4>
        <form className="login_form">
          <div className="login_text_area">
            <input
              type="text"
              id="username"
              name="username"
              defaultValue="E-mail"
              className="login_email_input"
            />
          </div>
          <div className="password_text_area">
            <input
              type="text"
              id="password"
              name="password"
              defaultValue="Password"
              className="login_password_input"
            />
          </div>

          <LoginButton />
        </form>
        <a className="account_creation_link" href="/AccountRegistration">
          No account? Click here to sign Up!
        </a>
      </div>
    </div>
  );
}

export default LoginPage;
