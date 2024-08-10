import React, { useState, useRef, useEffect } from "react";
import GameSearchBar from "./GameSearchBar";

function SearchForGames() {
  const [results, setResults] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [isResultsVisible, setIsResultsVisible] = useState(false);
  const searchBarRef = useRef(null);

  const fetchSearchResults = (searchTerm) => {
    if (!searchTerm.trim()) {
      setResults([]);
      setIsResultsVisible(false);
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
        setIsResultsVisible(data.length > 0);
      })
      .catch((error) => {
        console.error("There was a problem fetching the app list: " + error);
      });
  };

  const handleSearchChange = (event) => {
    const newSearchTerm = event.target.value;
    setSearchTerm(newSearchTerm);
    if (!newSearchTerm.trim()) {
      setResults([]);
      setIsResultsVisible(false);
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === "Enter") {
      fetchSearchResults(searchTerm);
      setIsResultsVisible(true);
    }
  };

  const handleClickOutside = (event) => {
    if (searchBarRef.current && !searchBarRef.current.contains(event.target)) {
      setSearchTerm("");
      setResults([]);
      setIsResultsVisible(false);
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
        isResultsVisible={isResultsVisible}
      />
    </div>
  );
}

export default SearchForGames;
