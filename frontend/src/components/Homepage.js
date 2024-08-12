import React from "react";
import PopularGames from "./PopularGames";
import Wishlist from "./Wishlist";

const Homepage = () => {
  return (
    <div>
      <h1>All Things Games</h1>
      <a href="./LoginPage">Login</a>
      <Wishlist />
      <PopularGames />
    </div>
  );
};

export default Homepage;
