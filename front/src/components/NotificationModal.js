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
        {tasksDueToday.length > 0 ? (
          <ListGroup>
            {tasksDueToday.map((task) => (
              <ListGroup.Item key={task.id}>
                {task.title} - Due:{" "}
                {new Date(task.dueDate).toLocaleDateString()}
                {task.description.substring(0, 20)}
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
              <ListGroup.Item key={task.id}>
                {task.title} - Created:{" "}
                {new Date(task.creationDate).toLocaleDateString()}
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
