import React from "react";
//W3Schools formats the import like this
import { useState, useEffect } from "react";
import GameSearchBar from "./GameSearchBar";

function SearchForGames() {
  const [results, setResults] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [allGames, setAllGames] = useState([]);

  useEffect(() => {
    // Fetch the list of all games from Steam DB
    const fetchAllGames = () => {
      const url = `http://localhost:8080/api/games/search`; 
      fetch(url, {
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
          console.log("Fetched games: ", data.title);
          setAllGames(data.title);
        })
        .catch((error) => {
          console.error("There was a problem fetching the app list: " + error);
        });
    };
    //Runs only on first render
    fetchAllGames();
  }, []);

  useEffect(() => {
    // Filter the results based on the search term
    /*The useEffect hook is used to run side-effects in a functional component. It takes two arguments: 
    a function to execute and an array of dependencies that control when the effect runs.
    allGames should now hold all the games that were fetched with the updater function setAllGames.
    .title should grab the title of the games based on what I see from postman JSON body results.
    threw in lowercase to make sure the search is not case sensitive.*/
    const filteredResults = allGames.filter((title) =>
      title.toLowerCase().includes(searchTerm.toLowerCase())
    );
    //setResults updater function will update results to be a filtered array based on the search
    //Runs on first render and any time a dependency value changes
    setResults(filteredResults);
  }, [searchTerm, allGames]);

  /*Event is the parameter passed to the function when input value changes.
  setSearchTerm will update the searchTerm state.*/
  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  return (
    <div>
      <GameSearchBar
        searchTerm={searchTerm}
        handleSearchChange={handleSearchChange}
        results={results}
        />
    </div>
  );
}

export default SearchForGames;
