import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import PopularGamesImg from "./PopularGamesImg";
import RemoveWishlistedGame from "./RemoveWishlistedGame";
import { fetchLoggedInUsersWishlistedGames } from "./LoggedInUserData";

const Wishlist = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [usersWishlistedGames, setUsersWishlistedGames] = useState([]);

  const toggleMenu = () => {
    setIsOpen((isOpen) => !isOpen);
  };

  async function getUsersGames() {
    const games = await fetchLoggedInUsersWishlistedGames();
    setUsersWishlistedGames(games);
  }

  useEffect(() => {
    getUsersGames();
  }, []);

  return (
    <div className="wishlist-games">
      <div className={`wishlist-container ${isOpen ? "is-open" : ""}`}>
        <ul className="wishlist">
          <p className="wishlist-title">Your Wishlist</p>
          {usersWishlistedGames.length > 0 ? (
            usersWishlistedGames.map((game) => (
              <li className="wishlist-item" key={game.id}>
                <Link to={`/GameInfoPage/${game.id}`}>
                  <PopularGamesImg game={game} />
                  <h4>{game.title}</h4>
                </Link>
                <RemoveWishlistedGame gameToRemove={game} />
              </li>
            ))
          ) : (
            <p>Your wishlist is currently empty.</p>
          )}
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
