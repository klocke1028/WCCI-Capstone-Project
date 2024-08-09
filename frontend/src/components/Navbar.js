import React from "react";
import SearchForGames from "./SearchForGames";
import { Link } from "react-router-dom";
import "./Navbar.css";

function NavBar() {
  return (
    <div className="navbar-wrapper">
      <nav className="navbar">
      <div className="homelink-container">
        <Link to="/">
          <p>All Things Games</p>
        </Link>
      </div>
      <div className="search-container">
      <SearchForGames />
      </div>
      </nav>
    </div>
  );
}

export default NavBar;
