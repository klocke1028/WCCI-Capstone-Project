import React from "react";
//W3Schools formats the import like this
import { useState, useEffect } from "react";

function SearchForGames() {
  const [results, setResults] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [allGames, setAllGames] = useState([]);

  useEffect(() => {
    // Fetch the list of all games from Steam DB
    const fetchAllGames = () => {
      const url = `https://api.steampowered.com/ISteamApps/GetAppList/v2/`; /**Access to fetch at 'https://api.steampowered.com/ISteamApps/GetAppList/v2/' 
      from origin 'http://localhost:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource. 
      If an opaque response serves your needs, set the request's mode to 'no-cors' to fetch the resource with CORS disabled. */
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
          console.log("Fetched games: ", data.applist.apps);
          setAllGames(data.applist.apps);
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
    /*The useEffect hook is used to run side-effects in a functional component. It takes two arguments: a function to execute and an array of dependencies that control when the effect runs.
    allGames should now hold all the games that were fetched with the updater function setAllGames.
    game.name should grab the title of the games based on what I see from postman JSON body results.
    threw in lowercase to make sure the search is not case sensitive.*/
    const filteredResults = allGames.filter((game) =>
      game.name.toLowerCase().includes(searchTerm.toLowerCase())
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
      <input
        type="text"
        placeholder="Search for games"
        value={searchTerm}
        onChange={handleSearchChange}
      />
      {/*results being an array of filtered game objects and parameter 'game' being equivalent to a single game object*/}
      <ul>
        {results.map((game) => (
          <li key={game.appid}>{game.name}</li>
        ))}
      </ul>
    </div>
  );
}

export default SearchForGames;
