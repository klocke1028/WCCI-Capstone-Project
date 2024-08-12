import React, { useState } from "react";

function AddGameToWishlistButton({ gameToAdd }) {
  const loggedInEmail = useState(localStorage.getItem("loggedInEmail"));

  const addGameToWishlist = () => {
    fetch(
      `http://localhost:8080/user?email=${encodeURIComponent(loggedInEmail[0])}`
    )
      .then((response1) => {
        if (!response1.ok) {
          throw new Error("Network response was not okay.");
        }
        return response1.json();
      })
      .then((data1) => {
        const wishlistId = data1.wishlist.id;

        const requestBody = {
          title: gameToAdd.title,
          itadId: gameToAdd.itadId,
          boxArtUrl: gameToAdd.boxArtLink,
        };

        fetch(
          `http://localhost:8080/wishlist/${encodeURIComponent(
            wishlistId
          )}/add-game`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(requestBody),
          }
        )
          .then((response2) => {
            if (!response2.ok) {
              throw new Error("Network response was not okay.");
            }
            return response2.json();
          })
          .then((data2) => {
            console.log(data2);
          })
          .catch((error2) => {
            console.log(
              "There was a problem adding the game to the wishlist: " + error2
            );
          });
      })
      .catch((error1) => {
        console.error(
          "There was a problem fetching the logged in user: " + error1
        );
      });
  };

  return (
    <div>
      <button onClick={addGameToWishlist}>Add To Wishlist</button>
    </div>
  );
}

export default AddGameToWishlistButton;
