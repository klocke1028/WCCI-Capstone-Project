import React, { useState, useEffect } from "react";

function BestPrice({ itadId }) {
  const [game, setGame] = useState(null);

  function FetchGameByItadId() {
    fetch(`http://localhost:8080/games/?itadId=${itadId}`, {
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
        console.log("Fetched database games:", data);
        setGame(data);
      })
      .catch((error) => {
        console.error(
          "There was a problem fetching all database games: " + error
        );
      });
  }

  if (itadId != null) {
    setInterval(FetchGameByItadId(), 1000);
  }

  return <p>{game.title}</p>;
}

export default BestPrice;
