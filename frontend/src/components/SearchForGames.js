import React, { useState, useRef, useEffect } from "react";
import GameSearchBar from "./GameSearchBar";
import { useNavigate } from "react-router-dom";

function SearchForGames() {
  const [results, setResults] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const searchBarRef = useRef(null);
  const navigate = useNavigate();

  const fetchSearchResults = (searchTerm) => {
    if (!searchTerm.trim()) {
      setResults([]);
      return;
    }

    const url = `http://localhost:8080/games/search?searchTerm=${encodeURIComponent(
      searchTerm
    )}`;
    fetch(url, {
      method: "GET",
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
        setResults(data);
        navigate("/SearchPage", { state: { results: data } });
      })
      .catch((error) => {
        console.error("There was a problem fetching the app list: " + error);
      });
  };

  const handleSearchChange = (event) => {
    const newSearchTerm = event.target.value;
    setSearchTerm(newSearchTerm);
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      fetchSearchResults(searchTerm);
    }
  };

  const handleClickOutside = (event) => {
    if (searchBarRef.current && !searchBarRef.current.contains(event.target)) {
      setSearchTerm("");
      setResults([]);
    }
  };

  useEffect(() => {
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  return (
    <div ref={searchBarRef}>
      <GameSearchBar
        searchTerm={searchTerm}
        handleSearchChange={handleSearchChange}
        handleKeyDown={handleKeyDown}
        results={results}
      />
    </div>
  );
}

export default SearchForGames;
