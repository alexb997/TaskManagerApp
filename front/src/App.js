import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AddTaskPage from "./pages/AddTaskPage";
import TaskListPage from "./pages/TaskListPage";

function App() {
  return (
    <Router>
      <div className="App">
        <h1>Personal Task Management Application</h1>
        <Routes>
          <Route path="/" element={<TaskListPage />} />
          <Route path="/add-task" element={<AddTaskPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
