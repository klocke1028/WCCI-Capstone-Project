<<<<<<< HEAD
import React from "react";
import PopularGames from "./PopularGames";
import Wishlist from "./Wishlist";
=======
import React from 'react';
import PopularGames from './PopularGames';
import "./Homepage.css"
>>>>>>> origin/main

const Homepage = () => {
  return (
    <div>
      <h1>All Things Games</h1>
      <Wishlist />
      <PopularGames />
    </div>
  );
};

export default Homepage;
