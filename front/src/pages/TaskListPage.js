import React, { useEffect, useState } from "react";
import instance from "../axios";
import { Link } from "react-router-dom";

const TaskListPage = () => {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const response = await instance.get("/tasks");
        setTasks(response.data);
      } catch (err) {
        setError("Failed to fetch tasks");
      }
    };

    fetchTasks();
  }, []);

  return (
    <div className="container mt-4">
      <h1 className="mb-4">All Tasks</h1>
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="mb-3">
        <Link to="/add-task" className="btn btn-success">
          Add New Task
        </Link>
      </div>
      <div className="row">
        {tasks.map((task) => (
          <div className="col-md-4 mb-3" key={task.id}>
            <div className="card">
              <div className="card-body">
                <h5 className="card-title">{task.name}</h5>
                <p className="card-text">{task.description}</p>
                <p className="card-text">
                  <small className="text-muted">Status: {task.status}</small>
                </p>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TaskListPage;
