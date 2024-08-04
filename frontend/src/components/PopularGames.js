import React, { useState, useEffect } from 'react';

const PopularGames = () => {
    const [games, setGames] = useState([]);

    useEffect(() => {
        const fetchPopularGames = () => {
            const url = 'http://localhost:8080/api/games/popular';
            fetch(url, {
                headers: {
                    Accept: "application/json",
                },
            })
            .then ((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok.");
                }
                return response.json();
            })
            .then((data) => {
                setGames(data);
            })
            .catch((error) => {
                console.log("There was a problem fetching popular games: " + error);
            });
        };

        fetchPopularGames();
    }, []);

    return (
        <div className="popular-games">
            <h2>Popular Games</h2>
            <ul>
                {games.map((game) => (
                    <li key={game.itadId}>
                        <img src={game.boxArtLink} alt={game.title} />
                        <h3>{game.title}</h3>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default PopularGames;