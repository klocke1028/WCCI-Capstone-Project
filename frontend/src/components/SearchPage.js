import React from "react";
import { Link, useLocation } from "react-router-dom";


function SearchPage() {
  const location = useLocation();
  const results = location.state?.results || [];

  return (
    <div className="search-page">
      <h2>Search Results</h2>
      <ul>
        {results.map((game) => (
          <li key={game.itadId}>
            <Link to={`/GameInfoPage/${game.itadId}`}>
              <div className="search-result-item">
                <p>{game.title}</p>
                <img src={game.boxArtLink} alt={game.title} />
              </div>
            </Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default SearchPage;