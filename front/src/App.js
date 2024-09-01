import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import TaskListPage from "./pages/TaskListPage";
import { Container } from "react-bootstrap";

function App() {
  return (
    <Router>
      <Container className="App">
        <h1>Personal Task Management Application</h1>
        <Routes>
          <Route path="/" element={<TaskListPage />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;
