import React from "react";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import "./GameInfoPage.css";
import AddToWishlistButton from "./AddToWishlistButton";

function GameInfoPage({ gameTitle, setWishlistedGames }) {
  const { itadId } = useParams();
  const [gameInfo, setGameInfo] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/games/game-info?itadId=${itadId}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok.");
        }
        return response.json();
      })
      .then((data) => {
        console.log("Fetched game data:", data);
        setGameInfo(data);
      })
      .catch((error) => {
        console.error("There was a problem fetching the app list: " + error);
      });
  }, [itadId]);

  if (!gameInfo) {
    return <div>Loading...</div>;
  }

  return (
    <div className="game-info-container">
      <h1>{gameInfo.title}</h1>
      <img src={gameInfo.boxArtLink} alt={gameInfo.title} />
      <p>Genres: {gameInfo.tags.join(", ")}</p>
      <AddToWishlistButton gameToAdd={gameInfo} />
    </div>
  );
}

export default GameInfoPage;
