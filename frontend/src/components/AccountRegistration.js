import React, { useState } from "react";
import "./AccountRegistration.css";

// Includes Ross notes

function AccountRegistration() {
  //use a hook to just set the email
  const [email, setEmail] = useState("");

  //handles form submission, breaks without it
  const handleSubmit = (e) => {
    e.preventDefault();

    //Not 100% if this is right, but creating this object matches the backend
    //Wondering if wishlist should be automatically created along with user,
    //but that's not what the backend is currently doing
    const userPayload = {
      email: email,
      reviewIds: [],
      wishlistId: null,
    };

    //Need to specify POST because we are creating a user here
    //stringify the userPayload we created so the JSON reads it correctly
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
        //This will just take you to the login page after submitting the form
        window.location.href = "/LoginPage";
      })
      .catch((error) => {
        console.error("There was a problem with the fetch operation:", error);
      });
  };

  return (
    <div>
      <div className="reg_container">
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
              //sets the email state, like we created above whenever the value inside the input field changes
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <input
            type="submit"
            value="Submit"
            className="submit_button"
            //calls handleSubmit function when clicked
            //This will work if enter is pressed as well
            onClick={handleSubmit}
          />
        </form>
      </div>
    </div>
  );
}

export default AccountRegistration;
