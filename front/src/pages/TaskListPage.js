import React, { useEffect, useState } from "react";
import instance from "../axios";
import { Card, Row, Col, Container, Badge, Button } from "react-bootstrap";
import EditTask from "../components/EditTask";
import { FaFlag, FaLink, FaRegEdit } from "react-icons/fa";
import { MdOutlineDelete } from "react-icons/md";

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

  const pendingTasks = tasks.filter((task) => task.status === "PENDING");
  const inProgressTasks = tasks.filter((task) => task.status === "IN_PROGRESS");
  const completedTasks = tasks.filter((task) => task.status === "COMPLETED");

  return (
    <Container className="mt-4">
      {error && <div className="alert alert-danger">{error}</div>}

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
            <>
              {pendingTasks.map((task) => (
                <Card
                  key={task.id}
                  className="shadow-sm p-3 mb-3 bg-white rounded"
                >
                  <Card.Subtitle className="mb-2 text-muted">
                    Task Management Template
                  </Card.Subtitle>
                  <Card.Title>
                    {task.name}
                    {task.dueDate}
                    {task.createdDate}
                  </Card.Title>
                  <Card.Text>{task.description}</Card.Text>
                  <div className="d-flex align-items-center mb-2">
                    <span className="pe-2">
                      <FaLink className="mr-1" />
                      <span className="ml-2 mr-3">4</span>
                    </span>
                    <span>
                      <FaFlag className="mr-2 text-danger" />
                      <span className="ml-2">Jan 20, 12pm</span>
                    </span>
                  </div>
                  <div className="d-flex justify-content-end align-items-center">
                    <FaRegEdit
                      size={28}
                      cursor={"pointer"}
                      onClick={() => handleEdit(task)}
                    >
                      Edit
                    </FaRegEdit>
                    <MdOutlineDelete
                      size={32}
                      cursor={"pointer"}
                      onClick={() => handleDelete(task.id)}
                    >
                      Delete
                    </MdOutlineDelete>
                  </div>
                </Card>
              ))}
              <Button
                variant="light"
                className="w-100 text-uppercase"
                onClick={handleAdd}
              >
                + New Task
              </Button>
            </>
          ) : (
            <p>No pending tasks</p>
          )}
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
            <>
              {inProgressTasks.map((task) => (
                <Card
                  key={task.id}
                  className="shadow-sm p-3 mb-3 bg-white rounded"
                >
                  <Card.Subtitle className="mb-2 text-muted">
                    Task Management Template
                  </Card.Subtitle>
                  <Card.Title>{task.name}</Card.Title>
                  <Card.Text>{task.description}</Card.Text>
                  <div className="d-flex align-items-center mb-2">
                    <span className="pe-2">
                      <FaLink className="mr-1" />
                      <span className="ml-2 mr-3">4</span>
                    </span>
                    <span>
                      <FaFlag className="mr-2 text-danger" />
                      <span className="ml-2">Jan 20, 12pm</span>
                    </span>
                  </div>
                  <div className="d-flex justify-content-end align-items-center">
                    <FaRegEdit
                      size={28}
                      cursor={"pointer"}
                      onClick={() => handleEdit(task)}
                    >
                      Edit
                    </FaRegEdit>
                    <MdOutlineDelete
                      size={32}
                      cursor={"pointer"}
                      onClick={() => handleDelete(task.id)}
                    >
                      Delete
                    </MdOutlineDelete>
                  </div>
                </Card>
              ))}
              <Button
                variant="light"
                className="w-100 text-uppercase"
                onClick={handleAdd}
              >
                + New Task
              </Button>
            </>
          ) : (
            <p>No tasks in progress</p>
          )}
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
            <>
              {completedTasks.map((task) => (
                <Card
                  key={task.id}
                  className="shadow-sm p-3 mb-3 bg-white rounded"
                >
                  <Card.Subtitle className="mb-2 text-muted">
                    Task Management Template
                  </Card.Subtitle>
                  <Card.Title>{task.name}</Card.Title>
                  <Card.Text>{task.description}</Card.Text>
                  <div className="d-flex align-items-center mb-2">
                    <span className="pe-2">
                      <FaLink className="mr-1" />
                      <span className="ml-2 mr-3">4</span>
                    </span>
                    <span>
                      <FaFlag className="mr-2 text-danger" />
                      <span className="ml-2">Jan 20, 12pm</span>
                    </span>
                  </div>
                  <div className="d-flex justify-content-end align-items-center">
                    <FaRegEdit
                      size={28}
                      cursor={"pointer"}
                      onClick={() => handleEdit(task)}
                    >
                      Edit
                    </FaRegEdit>
                    <MdOutlineDelete
                      size={32}
                      cursor={"pointer"}
                      onClick={() => handleDelete(task.id)}
                    >
                      Delete
                    </MdOutlineDelete>
                  </div>
                </Card>
              ))}
              <Button
                variant="light"
                className="w-100 text-uppercase"
                onClick={handleAdd}
              >
                + New Task
              </Button>
            </>
          ) : (
            <p>No completed tasks</p>
          )}
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
