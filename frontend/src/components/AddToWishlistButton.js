import React from "react";
import { fetchLoggedInUser } from "./LoggedInUserData";

function AddToWishlistButton({ gameToAdd }) {
  const addToWishlist = async () => {
    const loggedInUser = await fetchLoggedInUser();
    const userWishlistId = loggedInUser.wishlist.id;

    const requestBody = {
      title: gameToAdd.title,
      itadId: gameToAdd.itadId,
      boxArtUrl: gameToAdd.boxArtLink,
    };

    fetch(
      `http://localhost:8080/wishlist/${encodeURIComponent(
        userWishlistId
      )}/add-game`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(requestBody),
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not okay.");
        }
        return response.json();
      })
      .then((data) => {
        console.log(data);
      })
      .catch((error) => {
        console.log(
          "There was a problem adding the game to the wishlist: " + error
        );
      });
  };

  return (
    <div>
      <button onClick={addToWishlist}>Add To Wishlist</button>
    </div>
  );
}

export default AddToWishlistButton;
