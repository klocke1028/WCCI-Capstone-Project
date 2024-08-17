import React from "react";
import { Link, useLocation } from "react-router-dom";
import "./SearchPage.css";

function SearchPage() {
  const location = useLocation();
  const results = location.state?.results || [];
  const searchTerm = location.state?.searchTerm || "";

  return (
    <div className="search-page">
      <h2>Search Results for "{searchTerm}"</h2>
      <div className="search-container">
        <ul className="search-results-list">
          {results.map((game) => (
            <li key={game.itadId} className="search-result-item">
              <Link
                to={`/GameInfoPage/${game.itadId}`}
                className="search-result-link"
              >
                <img
                  src={game.boxArtLink}
                  alt={game.title}
                  className="search-game-image"
                />
                <p className="search-game-title">{game.title}</p>
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default SearchPage;