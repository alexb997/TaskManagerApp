import React from "react";
import { Modal, ListGroup, Button } from "react-bootstrap";

const NotificationModal = ({ show, handleClose, tasksDueToday }) => {
  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>Tasks Due Today</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {tasksDueToday.length > 0 ? (
          <ListGroup>
            {tasksDueToday.map((task) => (
              <ListGroup.Item key={task.id}>
                {task.title} - Due: {new Date(task.dueDate).toLocaleDateString()}
              </ListGroup.Item>
            ))}
          </ListGroup>
        ) : (
          <p>No tasks due today!</p>
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