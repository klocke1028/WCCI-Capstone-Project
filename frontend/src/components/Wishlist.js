import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Wishlist.css";
import PopularGamesImg from "./PopularGamesImg";
<<<<<<< HEAD
=======
import RemoveWishlistedGame from "./RemoveWishlistedGame"
>>>>>>> origin/main

const Wishlist = () => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleMenu = () => {
    setIsOpen((isOpen) => !isOpen);
  };
<<<<<<< HEAD
  const [games, setGames] = useState([]);
  const loggedInEmail = useState(localStorage.getItem("loggedInEmail"));
=======

  const [games, setGames] = useState([]);
  const loggedInEmail = useState(localStorage.getItem("loggedInEmail"));
  
>>>>>>> origin/main

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
<<<<<<< HEAD
    <div className="wishlist_games">
      <div className={`wishlist-container ${isOpen ? "is-open" : ""}`}>
        <ul className="wishlist">
=======
    <div className="wishlist-games">
      <div className={`wishlist-container ${isOpen ? "is-open" : ""}`}>
        <ul className="wishlist">
          <p>Wishlist</p>
>>>>>>> origin/main
          {games.map((game) => (
            <li key={game.itadId}>
              <Link to={`/GameInfoPage/${game.itadId}`}>
                <PopularGamesImg game={game} />
                <h4>{game.title}</h4>
              </Link>
<<<<<<< HEAD
              <h4 id="displayed-price">Best Price: ${game.priceWhenAdded}</h4>
=======
              <RemoveWishlistedGame gameToRemove={game} />
>>>>>>> origin/main
            </li>
          ))}
        </ul>
      </div>
      <div
        className={`hamburger no-user-select ${isOpen ? "active" : ""}`}
<<<<<<< HEAD
        onClick={toggleMenu}
      >
        <span className="bar"></span>
        <span className="bar"></span>
        <span className="bar"></span>
=======
        onClick={toggleMenu}>
        
        <span className="bar"></span>
        <span className="bar"></span>

>>>>>>> origin/main
      </div>
    </div>
  );
};

<<<<<<< HEAD
export default Wishlist;
=======
export default Wishlist;
>>>>>>> origin/main
