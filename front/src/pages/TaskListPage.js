import React, { useEffect, useState } from "react";
import instance from "../axios";
import { Row, Col, Container, Badge, Button } from "react-bootstrap";
import EditTask from "../components/EditTask";
import LoginModal from "../components/Login";
import TaskList from "../components/TasksList";
import NotificationModal from "../components/NotificationModal";

const TaskListPage = () => {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState("");
  const [tasksDueToday, setTasksDueToday] = useState([]);
  const [showNotificationModal, setShowNotificationModal] = useState(false);
  const [showModal, setShowModal] = useState(false);
  const [currentTask, setCurrentTask] = useState(null);
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [newTasks, setNewTasks] = useState([]);

  useEffect(() => {
    cleanUpLocalStorage();
    if (localStorage.getItem("username")) {
      let username = localStorage.getItem("username");
      fetchTasks(username);
      checkTasksDueToday(tasks);
    } else {
      setShowLoginModal(true);
    }
  }, []);

  useEffect(() => {
    if (tasks.length > 0) {
      checkNewTasks();
    }
    checkTasksDueToday(tasks);
  }, [tasks]);

  const checkTasksDueToday = (tasks) => {
    const today = new Date().toISOString().split("T")[0];
    if (tasks != []) {
      const dueToday = tasks.filter(
        (task) =>
          task.dueDate &&
          task.dueDate.startsWith(today) &&
          task.status !== "COMPLETED"
      );
      setTasksDueToday(dueToday);

      if (dueToday.length > 0) {
        setShowNotificationModal(true);
      }
    } else {
      setShowNotificationModal(false);
    }
  };

  const checkNewTasks = () => {
    const username = localStorage.getItem("username");
    if (!username) return;

    const lastLoginKey = `${username}_lastLoginTime`;
    const lastLoginTime = localStorage.getItem(lastLoginKey);

    if (lastLoginTime) {
      const lastLoginDate = new Date(lastLoginTime);
      const newTasksList = tasks.filter((task) => {
        const taskDate = new Date(task.createdDate);
        return taskDate > lastLoginDate;
      });

      setNewTasks(newTasksList);
    } else {
      setNewTasks([]);
    }
  };

  const fetchTasks = async (username) => {
    try {
      const response = await instance.get(`/api/tasks/user/${username}`);
      setTasks(response.data);
    } catch (err) {
      setError("Failed to fetch tasks");
    }
  };

  const handleDelete = async (id) => {
    try {
      await instance.delete(`/api/tasks/${id}`);
      setTasks(tasks.filter((task) => task.id !== id));
      alert("Task deleted successfully");
      let updatedUsername = localStorage.getItem("username");
      if (updatedUsername) {
        fetchTasks(updatedUsername);
      }
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
  const handleCloseLogin = () => {
    setShowLoginModal(false);
    let updatedUsername = localStorage.getItem("username");
    if (updatedUsername) {
      fetchTasks(updatedUsername);
    }
    setShowNotificationModal(true);
  };

  const handleCloseNotificationModal = () => {
    setShowNotificationModal(false);
  };

  const pendingTasks = tasks
    ? tasks.filter((task) => task.status === "PENDING")
    : [];
  const inProgressTasks = tasks
    ? tasks.filter((task) => task.status === "IN_PROGRESS")
    : [];
  const completedTasks = tasks
    ? tasks.filter((task) => task.status === "COMPLETED")
    : [];

  const cleanUpLocalStorage = () => {
    const expirationDays = 30;
    const now = new Date();

    Object.keys(localStorage).forEach((key) => {
      if (key.endsWith("_lastLoginTime")) {
        const lastLoginTime = new Date(localStorage.getItem(key));
        const daysSinceLastLogin =
          (now - lastLoginTime) / (1000 * 60 * 60 * 24);

        if (daysSinceLastLogin > expirationDays) {
          localStorage.removeItem(key);
        }
      }
    });
  };

  return (
    <Container className="mt-4">
      {error && <div className="alert alert-danger">{error}</div>}
      <Button variant="primary" onClick={handleShowLogin}>
        Open Login
      </Button>

      <LoginModal show={showLoginModal} handleClose={handleCloseLogin} />

      <NotificationModal
        show={showNotificationModal}
        handleClose={handleCloseNotificationModal}
        tasksDueToday={tasksDueToday}
      />

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
                {pendingTasks.length}
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
        handleClose={() => {
          setShowModal(false);
          let updatedUsername = localStorage.getItem("username");
          if (updatedUsername) {
            fetchTasks(updatedUsername);
          }
        }}
        taskToEdit={currentTask}
        onSave={handleSave}
      />
    </Container>
  );
};

export default TaskListPage;
