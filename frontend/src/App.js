import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import GameInfoPage from "./components/GameInfoPage";
import Homepage from "./components/Homepage";
import Navbar from "./components/Navbar";
import SearchForGames from "./components/SearchForGames";

function App() {
  return (
    <BrowserRouter>
      <div>
        <Navbar />
        <Routes>
          <Route path="/" element={<Homepage />} />
          <Route path="/GameInfoPage/:itadId" element={<GameInfoPage />} />
          <Route path="/search" element={<SearchForGames />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
