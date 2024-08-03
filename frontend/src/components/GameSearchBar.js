import React from "react";
import "./Navbar.css"

function GameSearchBar({ searchTerm, handleSearchChange, results }) {
  return (
    <div className="search-bar-container">
      <input
        className="search-bar"
        type="text"
        placeholder="Search for games"
        value={searchTerm}
        onChange={handleSearchChange}
      />
      <ul>
        {results.map((game) => (
          <li key={game.appid}>{game.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default GameSearchBar;