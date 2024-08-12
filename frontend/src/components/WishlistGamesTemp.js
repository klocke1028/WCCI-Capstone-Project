import { useState, useEffect } from "react";
import { fetchLoggedInUser } from "./LoggedInUserFetch";
import { Link } from "react-router-dom";
import PopularGamesImg from "./PopularGamesImg";

function WishlistGamesTemp() {
  const [wishlistGames, setWishlistGames] = useState([]);
  const [loggedInUserWishlistId, setLoggedInUserWishlistId] = useState(null);
  const loggedInEmail = useState(localStorage.getItem("loggedInEmail"));

  useEffect(() => {
    const fetchLoggedInUserData = async () => {
      try {
        const loggedInUser = await fetchLoggedInUser();
        setLoggedInUserWishlistId(loggedInUser.wishlist.id);
      } catch (error) {
        console.log(
          "There was a problem fetching the logged-in user: " + error
        );
      }
    };

    fetchLoggedInUserData();
  }, []);

  useEffect(() => {
    if (loggedInUserWishlistId === null) return;

    const fetchWishlistGames = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/wishlist/${encodeURIComponent(
            loggedInUserWishlistId
          )}/games`
        );
        if (!response.ok) {
          throw new Error("Network response was not ok.");
        }
        const data = await response.json();
        setWishlistGames(data);
      } catch (error) {
        console.log(
          "There was a problem fetching the user's wishlisted games: " + error
        );
      }
    };

    fetchWishlistGames();
  }, [loggedInUserWishlistId]);

  return (
    <div>
      <div>
        <h3>{loggedInEmail}'s Wishlisted Games</h3>
      </div>
      <ul>
        {wishlistGames.map((wishlistGame) => (
          <li id={wishlistGame.itadId}>
            <Link to={`/GameInfoPage/${wishlistGame.itadId}`}>
              <PopularGamesImg game={wishlistGame} />
              <h3>{wishlistGame.title}</h3>
            </Link>
            <h4 id="displayed-price">
              Best Price: ${wishlistGame.priceWhenAdded}
            </h4>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default WishlistGamesTemp;