import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Wishlist.css";

const Wishlist = () => {
  const [games, setGames] = useState([]);
  const loggedInEmail = useState(localStorage.getItem("loggedInEmail"));

  useEffect(() => {
    const fetchWishlistGames = () => {
      fetch(
        `http://localhost:8080/user?email=${encodeURIComponent(
          loggedInEmail[0]
        )}`
      )
        .then((response1) => {
          if (!response1.ok) {
            throw new Error("Network response was not ok.");
          }
          return response1.json();
        })
        .then((data1) => {
          const wishlistId = data1.wishlist.id;

          const url = `http://localhost:8080/wishlist/${wishlistId}/games`;
          return fetch(url, {
            headers: {
              Accept: "application/json",
            },
          });
        })
        .then((response2) => {
          if (!response2.ok) {
            throw new Error("Network response was not ok.");
          }
          return response2.json();
        })
        .then((data2) => {
          setGames(data2);
        })
        .catch((error) => {
          console.log("There was a problem fetching the wishlist: " + error);
        });
    };

    fetchWishlistGames();
  }, [loggedInEmail]);

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
