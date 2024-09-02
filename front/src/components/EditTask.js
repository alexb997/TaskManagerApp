import React, { useState, useEffect } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import "../asset/EditComponent.css";
import instance from "../axios";

const EditTask = ({ show, handleClose, taskToEdit, onSave }) => {
  const token = localStorage.getItem("token");
  const getInitialDueDate = () => {
    const today = new Date();
    today.setDate(today.getDate() + 1);
    return today.toISOString().substring(0, 10);
  };

  const initialTaskState = taskToEdit || {
    name: "",
    description: "",
    status: "PENDING",
    dueDate: getInitialDueDate(),
    createdDate: new Date(),
    creator: localStorage.getItem("username"),
    assignedUser: "",
  };

  const [task, setTask] = useState(initialTaskState);

  useEffect(() => {
    if (taskToEdit) {
      setTask(taskToEdit);
    } else {
      setTask(initialTaskState);
    }
  }, [taskToEdit]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setTask({ ...task, [name]: value });
  };

  const handleSaveChanges = async () => {
    try {
      if (taskToEdit) {
        await instance.put(`/api/tasks/${taskToEdit.id}`, task);
        onSave(task);
      } else {
        task.createdDate = new Date();
        let date = task.dueDate;
        task.dueDate = new Date(date);
        const response = await instance.post("/api/tasks", task);
        onSave(response.data);
      }
      handleClose();
    } catch (err) {
      console.error(
        "Failed to save task",
        err.response ? err.response.data : err
      );
      alert("Failed to save task");
    }
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>{taskToEdit ? "Edit Task" : "New Task ToDo"}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form className="task-form">
          <Form.Group className="mb-3">
            <Form.Label className="task-label">Title Task</Form.Label>
            <Form.Control
              type="text"
              name="name"
              placeholder="Add Task Name..."
              value={task.name}
              onChange={handleChange}
              className="task-input"
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label className="task-label">Description</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              name="description"
              placeholder="Add Description..."
              value={task.description}
              onChange={handleChange}
              className="task-input"
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label className="task-label">Due Date</Form.Label>
            <Form.Control
              type="date"
              name="dueDate"
              value={
                task.dueDate
                  ? new Date(task.dueDate).toISOString().substring(0, 10)
                  : ""
              }
              onChange={handleChange}
              className="task-input"
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label className="task-label">Status</Form.Label>
            <Form.Control
              as="select"
              name="status"
              value={task.status}
              onChange={handleChange}
            >
              <option value="PENDING">PENDING</option>
              <option value="IN_PROGRESS">IN_PROGRESS</option>
              <option value="COMPLETED">COMPLETED</option>
            </Form.Control>
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label className="task-label">Assigned User</Form.Label>
            <Form.Control
              type="text"
              name="assignedUser"
              placeholder="Enter username to assign task"
              value={task.assignedUser}
              onChange={handleChange}
              className="task-input"
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label className="task-label">Creator</Form.Label>
            <Form.Control
              type="text"
              name="creator"
              value={task.creator}
              readOnly
              className="task-input"
            />
          </Form.Group>
        </Form>
      </Modal.Body>
      <Modal.Footer className="d-flex justify-content-between">
        <Button
          variant="light"
          onClick={handleClose}
          className="task-button-cancel"
        >
          Cancel
        </Button>
        <Button
          variant="primary"
          onClick={handleSaveChanges}
          className="task-button-create"
        >
          {taskToEdit ? "Save Changes" : "Create"}
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default EditTask;
