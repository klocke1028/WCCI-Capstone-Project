import React from "react";
import "./LoginPage.css";
import LoginButton from "./LoginButton";

function LoginPage() {
  return (
    <div>
      <div className="login">
        <h4 className="login_form_header">Login</h4>
        <form className="login_form">
          <div className="text_area">
            <input
              type="text"
              id="username"
              name="username"
              defaultValue="E-mail"
              className="text_input"
            />
          </div>
          <LoginButton />
        </form>
        <a className="link" href="/AccountRegistration">
          No account? Click here to sign Up!
        </a>
      </div>
    </div>
  );
}

export default LoginPage;
