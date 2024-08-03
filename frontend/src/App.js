import logo from "./logo.svg";
import "./App.css";
import { useState } from "react";
import SearchForGames from "./components/SearchForGames";
//import LoginPage from "./components/LoginPage";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

function App() {
  return (
    <>
      <div className="background-container"></div>
      <Router>
        <Routes>
          <Route path="/" element={<SearchForGames />} />
        </Routes>
      </Router>
    </>
  );
}

// Add <Route path="/" element={<LoginPage>} />

export default App;
