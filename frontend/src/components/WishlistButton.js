import React from "react";

function WishlistButton({ gameTitle, setWishlistedGames }) {
  const addToWishList = () => {
    fetch("http://localhost8080/api/wishlist/{id}/add-game")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok.");
        }
        return response.json();
      })
      .then((wishlistedGames) => {
        if (wishlistedGames.some((fav) => fav.text === gameTitle)) {
          alert("This game is already on wishlist.");
          return;
        }
        fetch("http://localhost8080/{id}/add-game", {
          method: "POST",
          headers: {
            "Content-Type": "application.json",
          },
          body: JSON.stringify({ text: gameTitle }),
        })
          .then((response) => response.json())
          .then((data) => setWishlistedGames(data))
          .catch((error) => {
            console.error(
              "There was a problem adding the game to wishlist:" + error
            );
          });
      })
      .catch((error) => {
        console.error("There was a problem fetching the wishlist:" + error);
      });
  };

  return (
    <button className="wishlist-button" onClick={addToWishList}>
      Add to Wishlist
    </button>
  );
}

export default WishlistButton;
