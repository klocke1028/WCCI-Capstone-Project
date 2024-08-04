import "./App.css";
import SearchForGames from "./components/SearchForGames";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AccountRegistration from "./components/AccountRegistration";
import LoginPage from "./components/LoginPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/LoginPage" element={<LoginPage />} />
        <Route path="/AccountRegistration" element={<AccountRegistration />} />
        <Route path="/SearchForGames" element={<SearchForGames />} />
      </Routes>
    </Router>
  );
}

export default App;
