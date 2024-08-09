import React, { useState } from "react";
import "./AccountRegistration.css";

/**NEED TO GET THIS TO STORE A UNIQUE USER ID, SO THAT WHEN LOGGING INTO THE ACCOUNT, THE EMAIL WILL BE RECOGNIZED. 
 * ONLY OTHER THING I CAN THINK OF IS CREATING A NEW BACKEND METHOD TO findByEmail OR SOMETHING. 
 * ONCE THIS IS DONE THE LOGIN AUTHENTICATION WILL BE GOOD ENOUGH FOR TECH DEMO.
 */

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
      wishlistId: null
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
        const userId = data.userId;
        //This will just take you to the login page after submitting the form
        window.location.href = "/LoginPage";
      })
      .catch((error) => {
        console.error("There was a problem with the fetch operation:", error);
      });
  };

  return (
    <div>
      <h2 className="website_name">Insert website name here:</h2>
      <div className="registration">
        <form className="reg_form">
          <div className="text">
            <input
              type="text"
              id="username"
              name="username"
              placeholder="E-mail"
              className="text_input"
              value={email}
              //sets the email state, like we created above whenever the value inside the input field changes
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <input
            type="submit"
            value="Create Account!"
            className="register_button"
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