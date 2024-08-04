//import logo from "./logo.svg";
import "./App.css";
import Homepage from "./components/Homepage";
//import { useState } from "react";
//import SearchForGames from "./components/SearchForGames";
//import LoginPage from "./components/LoginPage";
//import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
  import Navbar from "./components/Navbar"

function App() {
  return (
    <div>
      <Navbar />
      <Homepage />
    </div>
  );
}

// Add <Route path="/" element={<LoginPage>} />

export default App;
