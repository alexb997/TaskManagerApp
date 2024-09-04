import React from "react";
import { Modal, ListGroup, Button } from "react-bootstrap";

const NotificationModal = ({ show, handleClose, tasksDueToday, newTasks }) => {
  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Notifications</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <h5>Tasks Due Today</h5>
        {tasksDueToday ? (
          <ListGroup>
            {tasksDueToday.map((task) => (
              <ListGroup.Item
                key={task.id}
                className="mb-2 p-3 rounded shadow-sm bg-light"
                style={{ borderLeft: "5px solid #007bff" }}
              >
                <div className="d-flex justify-content-between align-items-center">
                  <div>
                    <strong>{task.name}</strong> <br />
                    <small className="text-muted">
                      Due: {new Date(task.dueDate).toLocaleDateString()}
                    </small>
                  </div>
                </div>
                <div className="mt-2 text-muted">
                  {task.description.substring(0, 30)}...
                </div>
              </ListGroup.Item>
            ))}
          </ListGroup>
        ) : (
          <p>No tasks due today!</p>
        )}

        <h5 className="mt-4">New Tasks Since Last Login</h5>
        {newTasks ? (
          <ListGroup>
            {newTasks.map((task) => (
              <ListGroup.Item
                key={task.id}
                className="mb-2 p-3 rounded shadow-sm bg-light"
                style={{ borderLeft: "5px solid #28a745" }}
              >
                <div className="d-flex justify-content-between align-items-center">
                  <div>
                    <strong>{task.name}</strong> <br />
                    <small className="text-muted">
                      Due Date: {new Date(task.dueDate).toLocaleDateString()}
                    </small>
                  </div>
                </div>
                <div className="mt-2 text-muted">
                  {task.description.substring(0, 30)}...
                </div>
              </ListGroup.Item>
            ))}
          </ListGroup>
        ) : (
          <p>No new tasks since last login!</p>
        )}
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default NotificationModal;
