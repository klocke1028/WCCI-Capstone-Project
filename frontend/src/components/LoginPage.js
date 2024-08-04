import React from "react";
import "./LoginPage.css";

function LoginPage() {
  return (
    <div>
      <h2 className="login_page_header">Insert website name here :)</h2>
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
          <button type="submit">LOGIN</button>
        </form>
        <a className="link" href="/AccountRegistration">
          No account? Click here to sign Up!
        </a>
      </div>
    </div>
  );
}

export default LoginPage;
