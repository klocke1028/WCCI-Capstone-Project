import React, { Component } from "react";
import "./LoginPage.css";

class Login extends Component {
  render() {
    return (
      <div className="login">
        <h4>Login</h4>
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
          <input type="submit" value="LOGIN" className="button" />
        </form>
        <a className="link" href="/signup">
          Sign Up
        </a>
      </div>
    );
  }
}

export default Login;
