import React from "react";
import "./RemoveWishlistedGameButton.css";
const RemoveWishlistedGame = ({ gameToRemove, onGameRemoval }) => {
  const loggedInEmail = localStorage.getItem("loggedInEmail");

  const removeFromWishlist = () => {
    console.log("Removing from wishlist, loggedInEmail:", loggedInEmail);
    fetch(
      `http://localhost:8080/user?email=${encodeURIComponent(loggedInEmail)}`
    )
      .then((response1) => {
        console.log("response1:", response1);
        if (!response1.ok) {
          throw new Error("Network response was not ok.");
        }
        return response1.json();
      })
      .then((data1) => {
        console.log("data1:", data1);
        const wishlistId = data1.wishlist.id;

        const url = `http://localhost:8080/wishlist/${encodeURIComponent(
          wishlistId
        )}`;

        return fetch(url, {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({ itadId: gameToRemove.itadId }),
        })
          .then((response2) => {
            if (!response2.ok) {
              throw new Error("Network response was not ok.");
            }
            return response2.json();
          })
          .then((data2) => {
            console.log("Updated wishlist:", data2);

            onGameRemoval();
          })
          .catch((error2) => {
            console.error(
              "There was a problem removing the game from the wishlist:",
              error2
            );
          });
      })
      .catch((error1) => {
        console.error("There was a problem fetching the wishlist:", error1);
      });
  };

  return (
    <div>
      <button className="remove-game-button" onClick={removeFromWishlist}>
        Remove From Wishlist
      </button>
    </div>
  );
};

export default RemoveWishlistedGame;
