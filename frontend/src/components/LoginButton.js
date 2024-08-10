import React from "react";
import "./LoginButton.css";

function LoginButton() {
  const login = () => {
    fetch("http://localhost:8080/api/user")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok.");
        }
        return response.json();
      })
      .catch((error) => {
        console.error("There was a problem logging in: " + error);
      });
  };
  return (
    <button id="login_button" onClick={login}>
      LOGIN
    </button>
  );
}

export default LoginButton;
