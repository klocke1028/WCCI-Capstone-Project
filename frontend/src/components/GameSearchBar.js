import React from "react";
import "./Navbar.css";
import { Link } from "react-router-dom";

function GameSearchBar({
  searchTerm,
  handleSearchChange,
  handleKeyDown,
  results,
  isResultsVisible,
  clearSearchTerm,
}) {
  return (
    <div className="search-bar-container">
      <input
        className="search-bar"
        type="text"
        placeholder="Search for games"
        value={searchTerm}
        onChange={handleSearchChange}
        onKeyDown={handleKeyDown}
      />
      {isResultsVisible && (
        <ul>
          {results.map((game) => (
            <li key={game.itadId}>
              <Link
                to={`/GameInfoPage/${game.itadId}`}
                onClick={clearSearchTerm}
              >
                {game.title}
              </Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default GameSearchBar;
