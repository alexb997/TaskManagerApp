import React, { useState } from "react";
import instance from "../axios";
import { useNavigate } from "react-router-dom";

const AddTaskPage = () => {
  const [task, setTask] = useState({
    name: "",
    description: "",
    status: "PENDING",
    dueDate: "",
    createdDate: new Date(),
  });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTask({ ...task, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      let date = task.dueDate;
      task.dueDate = new Date(date);
      await instance.post("/tasks", task);
      navigate("/");
    } catch (error) {
      setError("Failed to create task");
    }
  };

  return (
    <div className="container mt-4">
      <h1 className="mb-4">Add New Task</h1>
      {error && <div className="alert alert-danger">{error}</div>}
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">
            Task Name:
          </label>
          <input
            type="text"
            id="name"
            name="name"
            className="form-control"
            value={task.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="description" className="form-label">
            Description:
          </label>
          <textarea
            id="description"
            name="description"
            className="form-control"
            value={task.description}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="dueDate" className="form-label">
            Due Date:
          </label>
          <input
            type="date"
            id="dueDate"
            name="dueDate"
            className="form-control"
            value={task.dueDate}
            onChange={handleChange}
          />
        </div>
        <div className="mb-3">
          <label htmlFor="status" className="form-label">
            Status:
          </label>
          <select
            id="status"
            name="status"
            className="form-select"
            value={task.status}
            onChange={handleChange}
          >
            <option value="PENDING">PENDING</option>
            <option value="IN_PROGRESS">IN_PROGRESS</option>
            <option value="COMPLETED">COMPLETED</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">
          Add Task
        </button>
      </form>
    </div>
  );
};

export default AddTaskPage;
