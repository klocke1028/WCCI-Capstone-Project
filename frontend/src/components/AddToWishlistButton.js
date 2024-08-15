import React from "react";
import { fetchLoggedInUser } from "./LoggedInUserData";

function AddToWishlistButton({ gameToAdd }) {
  const addToWishlist = async () => {
    const loggedInUser = await fetchLoggedInUser();
    const userWishlistedGames = loggedInUser.wishlist.games;
    const gameToAddItadId = gameToAdd.itadId;
    console.log("Game to add ITAD ID: ~", gameToAddItadId, "~");
    let isAlreadyOnWishlist = false;

    userWishlistedGames.forEach((wishlistedGame) => {
      isAlreadyOnWishlist = wishlistedGame.itadId === gameToAddItadId;
      if (isAlreadyOnWishlist) return;
    });

    if (!isAlreadyOnWishlist) {
      console.log("Adding game with ITAD ID: ", { gameToAddItadId });

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
        })
        .catch((error) => {
          console.log(
            "There was a problem adding the game to the wishlist: " + error
          );
        });
    } else {
      window.alert("This game is already on your wishlist!");
    }
  };

  return (
    <div>
      <button onClick={addToWishlist}>Add To Wishlist</button>
    </div>
  );
}

export default AddToWishlistButton;
