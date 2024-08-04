import React from "react";
import "./LoginPage.css";

function LoginPage({ login }) {
  return (
    <div className="login_container">
      <h2 className="login_page_header">Insert website name here :)</h2>
      <div className="login">
        <h4 className="login_form_header">Login</h4>
        <form>
          <div className="text_area">
            <input
              type="text"
              id="username"
              name="username"
              defaultValue="E-mail"
              className="text_input"
            />
          </div>
        </form>
        <a className="link" href="/AccountRegistration">
          No account? Click here to sign Up!
        </a>
      </div>
    </div>
  );
}

export default LoginPage;
