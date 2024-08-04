import React from "react";

function AccountRegistration() {
  return (
    <div className="registration_container">
      <h2 className="website_name">Insert website name here :)</h2>
      <div className="registration">
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
