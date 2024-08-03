import logo from './logo.svg';
import './App.css';
import { useState } from "react";
import React from 'react';
import Navbar from "./components/Navbar";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";


function App() {
  return (
    <div>
      <Navbar />
    </div>
  );
}


export default App;
