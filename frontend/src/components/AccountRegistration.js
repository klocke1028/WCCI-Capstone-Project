import React from "react";
import "./AccountRegistration.css";

function AccountRegistration() {
  return (
    <div>
      <h2 className="website_name">Insert website name here :)</h2>
      <div className="registration">
        <form className="reg_form">
          <div className="text">
            <input
              type="text"
              id="username"
              name="username"
              defaultValue="E-mail"
              className="text_input"
            />
          </div>
          <input
            type="submit"
            value="Create Account!"
            className="register_button"
          />
        </form>
      </div>
    </div>
  );
}

export default AccountRegistration;
