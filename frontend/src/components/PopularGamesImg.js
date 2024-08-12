import React from "react";

function PopularGamesImg({ game }) {
  return (
    <div>
      <img src={game.boxArtLink} alt={game.title} />
    </div>
  );
}

export default PopularGamesImg;
