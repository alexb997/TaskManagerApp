import React, { useEffect, useState } from "react";
import instance from "../axios";
import { Button, Card, Row, Col, Container } from "react-bootstrap";
import { Link } from "react-router-dom";
import EditTask from "../components/EditTask";

const TaskListPage = () => {
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [currentTask, setCurrentTask] = useState(null);

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

  const handleDelete = async (id) => {
    try {
      await instance.delete(`/tasks/${id}`);
      setTasks(tasks.filter((task) => task.id !== id)); // Remove the deleted task from state
      alert("Task deleted successfully");
    } catch (err) {
      setError("Failed to delete task");
    }
  };

  const handleEdit = (task) => {
    setCurrentTask(task);
    setShowModal(true);
  };

  const handleSave = (updatedTask) => {
    setTasks(
      tasks.map((task) => (task.id === updatedTask.id ? updatedTask : task))
    );
  };

  return (
    <Container className="mt-4">
      <h1 className="mb-4">All Tasks</h1>
      {error && <div className="alert alert-danger">{error}</div>}
      <div className="mb-3">
        <Link to="/add-task" className="btn btn-success">
          Add New Task
        </Link>
      </div>
      <Row xs={1} md={2} lg={3} className="g-4">
        {tasks.map((task) => (
          <Col md={4} mb={3} key={task.id}>
            <Card>
              <Card.Header as="h5">{task.name}</Card.Header>
              <Card.Body>
                <Card.Text>{task.description}</Card.Text>
                <Card.Text>
                  <small className="text-muted">Status: {task.status}</small>
                </Card.Text>
                <Button
                  variant="primary"
                  onClick={() => handleEdit(task)}
                  className="me-2"
                >
                  Edit
                </Button>
                <Button variant="danger" onClick={() => handleDelete(task.id)}>
                  Delete
                </Button>
              </Card.Body>
            </Card>
          </Col>
        ))}
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
