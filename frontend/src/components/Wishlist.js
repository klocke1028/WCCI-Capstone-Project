import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Wishlist.css";

const Wishlist = () => {
  const [games, setGames] = useState([]);

  useEffect(() => {
    const fetchWishlistGames = () => {
      const url = "http://localhost:8080/wishlist/{id}";
      fetch(url, {
        headers: {
          Accept: "application/json",
        },
      })
        .then((response) => {
          if (!response.ok) {
            throw new Error("Network response was not ok.");
          }
          return response.json();
        })
        .then((data) => {
          setGames(data);
        })
        .catch((error) => {
          console.log("There was a problem fetching the wishlist:" + error);
        });
    };
    fetchWishlistGames();
  }, []);

  return (
    <div className="wishlist-games-header">
      <h3 className="wishlist-games-header">Wishlist</h3>
      <ul>
        {games.map((game) => (
          <li key={game.itadId}>
            <Link to={`/GameInfoPage/${game.itadId}`}>
              <h4>{game.title}</h4>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Wishlist;
