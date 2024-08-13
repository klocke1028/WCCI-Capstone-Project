import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Wishlist.css";
import PopularGamesImg from "./PopularGamesImg";
import RemoveWishlistedGame from "./RemoveWishlistedGame";

const Wishlist = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleMenu = () => {
    setIsOpen((isOpen) => !isOpen);
  };

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
    <div className="wishlist-games">
      <div className={`wishlist-container ${isOpen ? "is-open" : ""}`}>
        <ul className="wishlist">
          <p>Wishlist</p>
          {games.map((game) => (
            <li key={game.itadId}>
              <Link to={`/GameInfoPage/${game.itadId}`}>
                <PopularGamesImg game={game} />
                <h4>{game.title}</h4>
              </Link>
              <RemoveWishlistedGame gameToRemove={game} />
            </li>
          ))}
        </ul>
      </div>
      <div
        className={`hamburger no-user-select ${isOpen ? "active" : ""}`}
        onClick={toggleMenu}
      >
        <span className="bar"></span>
        <span className="bar"></span>
      </div>
    </div>
  );
};

export default Wishlist;
