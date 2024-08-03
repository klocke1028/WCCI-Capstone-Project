import React from "react";
import SearchForGames from "./SearchForGames";
import "./Navbar.css";

function NavBar() {
  return (
    <div className="navbar-wrapper">
      <nav className="navbar">
        <SearchForGames />
      </nav>
    </div>
  );
}

export default NavBar;
