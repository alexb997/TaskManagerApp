import React, { useEffect, useState } from "react";
import instance from "../axios";
import { Card, Row, Col, Container, Badge, Button } from "react-bootstrap";
import EditTask from "../components/EditTask";
import { FaFlag, FaLink, FaRegEdit } from "react-icons/fa";
import { MdOutlineDelete } from "react-icons/md";
import LoginModal from "../components/Login";
import TaskList from "../components/TasksList";

const TaskListPage = () => {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [currentTask, setCurrentTask] = useState(null);
  const [showLoginModal, setShowLoginModal] = useState(false);

  useEffect(() => {
    if (localStorage.getItem("username")) {
      let username = localStorage.getItem("username"); // Get the logged-in user's username
      const fetchTasks = async (username) => {
        try {
          const response = await instance.get(`/api/tasks/user/${username}`);
          setTasks(response.data);
        } catch (err) {
          setError("Failed to fetch tasks");
        }
      };
      fetchTasks(username);
    } else {
      setShowLoginModal(true);
    }
  }, []);

  const handleDelete = async (id) => {
    try {
      await instance.delete(`/api/tasks/${id}`);
      setTasks(tasks.filter((task) => task.id !== id));
      alert("Task deleted successfully");
    } catch (err) {
      setError("Failed to delete task");
    }
  };

  const handleEdit = (task) => {
    setCurrentTask(task);
    setShowModal(true);
  };

  const handleAdd = () => {
    setCurrentTask(null);
    setShowModal(true);
  };

  const handleSave = (updatedTask) => {
    setTasks(
      tasks.map((task) => (task.id === updatedTask.id ? updatedTask : task))
    );
  };

  const handleShowLogin = () => setShowLoginModal(true);
  const handleCloseLogin = () => setShowLoginModal(false);

  const pendingTasks = tasks.filter((task) => task.status === "PENDING");
  const inProgressTasks = tasks.filter((task) => task.status === "IN_PROGRESS");
  const completedTasks = tasks.filter((task) => task.status === "COMPLETED");

  return (
    <Container className="mt-4">
      {error && <div className="alert alert-danger">{error}</div>}
      <Button variant="primary" onClick={handleShowLogin}>
        Open Login
      </Button>

      <LoginModal show={showLoginModal} handleClose={handleCloseLogin} />
      <Row xs={1} md={2} lg={3} className="g-4">
        <Col md={4}>
          <Container
            style={{
              border: "1px solid #e6e6e6",
              borderRadius: "8px",
              padding: "5px",
              backgroundColor: "#fff",
              marginBottom: 10,
            }}
          >
            <span
              style={{
                margin: 0,
                fontWeight: "600",
                color: "#6c757d",
              }}
            >
              Pending{" "}
              <Badge pill bg="secondary">
                9
              </Badge>
            </span>
          </Container>
          {pendingTasks.length > 0 ? (
            <TaskList
              tasks={pendingTasks}
              handleEdit={handleEdit}
              handleDelete={handleDelete}
            />
          ) : (
            <p>No pending tasks</p>
          )}
          <Button
            variant="light"
            className="w-100 text-uppercase"
            onClick={handleAdd}
          >
            + New Task
          </Button>
        </Col>
        <Col md={4}>
          <Container
            style={{
              border: "1px solid #e6e6e6",
              borderRadius: "8px",
              padding: "5px",
              backgroundColor: "#fff",
              marginBottom: 10,
            }}
          >
            <span
              style={{
                margin: 0,
                fontWeight: "600",
                color: "#6c757d",
              }}
            >
              In Progress{" "}
              <Badge pill bg="secondary">
                {inProgressTasks.length}
              </Badge>
            </span>
          </Container>
          {inProgressTasks.length > 0 ? (
            <TaskList
            tasks={inProgressTasks}
            handleEdit={handleEdit}
            handleDelete={handleDelete}
          />
          ) : (
            <p>No tasks in progress</p>
          )}
          <Button
            variant="light"
            className="w-100 text-uppercase"
            onClick={handleAdd}
          >
            + New Task
          </Button>
        </Col>
        <Col md={4}>
          <Container
            style={{
              border: "1px solid #e6e6e6",
              borderRadius: "8px",
              padding: "5px",
              backgroundColor: "#fff",
              marginBottom: 10,
            }}
          >
            <span
              style={{
                margin: 0,
                fontWeight: "600",
                color: "#6c757d",
              }}
            >
              Completed{" "}
              <Badge pill bg="secondary">
                {completedTasks.length}
              </Badge>
            </span>
          </Container>
          {completedTasks.length > 0 ? (
            <TaskList
            tasks={completedTasks}
            handleEdit={handleEdit}
            handleDelete={handleDelete}
          />
          ) : (
            <p>No completed tasks</p>
          )}
          <Button
            variant="light"
            className="w-100 text-uppercase"
            onClick={handleAdd}
          >
            + New Task
          </Button>
        </Col>
      </Row>
      <EditTask
        show={showModal}
        handleClose={() => setShowModal(false)}
        taskToEdit={currentTask}
        onSave={handleSave}
      />
    </Container>
  );
};

export default TaskListPage;
