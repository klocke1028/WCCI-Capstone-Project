import React from "react";
import "./App.css";
import { useEffect } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import GameInfoPage from "./components/GameInfoPage";
import Homepage from "./components/Homepage";
import Navbar from "./components/Navbar";
import SearchPage from "./components/SearchPage";
import AccountRegistration from "./components/AccountRegistration";
import LoginPage from "./components/LoginPage";
import Wishlist from "./components/Wishlist";
import WishlistGamesTemp from "./components/WishlistGamesTemp";
import About from "./components/About";
import WishlistPriceUpdater from "./components/WishlistPriceUpdater";

function App() {
  useEffect(() => {
    const stopUpdater = WishlistPriceUpdater();

    return () => {
      stopUpdater();
    };
  }, []);

  return (
    <Router>
      <div>
        <Navbar />
        <Routes>
          <Route path="/" element={<Homepage />} />
          <Route path="/GameInfoPage/:itadId" element={<GameInfoPage />} />
          <Route path="/SearchPage" element={<SearchPage />} />
          <Route path="/LoginPage" element={<LoginPage />} />
          <Route path="/WishlistGamesTemp" element={<WishlistGamesTemp />} />
          <Route
            path="/AccountRegistration"
            element={<AccountRegistration />}
          />
          <Route path="/Wishlist" element={<Wishlist />} />
          <Route path="/About" element={<About />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
