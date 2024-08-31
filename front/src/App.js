import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AddTaskPage from "./pages/AddTaskPage";
import TaskListPage from "./pages/TaskListPage";
import { Container } from "react-bootstrap";

function App() {
  return (
    <Router>
      <Container className="App">
        <h1>Personal Task Management Application</h1>
        <Routes>
          <Route path="/" element={<TaskListPage />} />
          <Route path="/add-task" element={<AddTaskPage />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;
