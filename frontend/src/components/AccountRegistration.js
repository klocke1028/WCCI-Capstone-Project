import React, { useState } from "react";
import "./AccountRegistration.css";

function AccountRegistration() {
  //use a hook to just set the email
  const [email, setEmail] = useState("");

  //handles form submission, breaks without it
  const handleSubmit = (e) => {
    e.preventDefault();

    const userPayload = {
      email: email,
      reviewIds: [],
      wishlistId: null,
    };

    fetch("http://localhost:8080/user", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(userPayload),
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        console.log("User successfully added:", data);

        window.location.href = "/LoginPage";
      })
      .catch((error) => {
        console.error("There was a problem with the fetch operation:", error);
      });
  };

  return (
    <div className="reg_page_container">
      <div>
        <h1 className="reg_page_header">Level Up Your Savings Today!</h1>
      </div>
      <div className="reg_form_container">
        <h4 className="reg_form_header">Create An Account</h4>
        <form className="reg_form">
          <div className="reg_email_input">
            <input
              type="text"
              id="username"
              name="username"
              placeholder="E-mail"
              className="reg_email_text"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <input
            type="submit"
            value="Submit"
            className="submit_button"
            onClick={handleSubmit}
          />
        </form>
      </div>
    </div>
  );
}

export default AccountRegistration;
