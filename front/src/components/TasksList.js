import React from "react";
import { Card } from "react-bootstrap";
import { FaLink, FaFlag, FaRegEdit } from "react-icons/fa";
import { MdOutlineDelete } from "react-icons/md";

const TaskList = ({ tasks, handleEdit, handleDelete }) => {
  return (
    <>
      {tasks.map((task) => (
        <Card key={task.id} className="shadow-sm p-3 mb-3 bg-white rounded">
          <Card.Subtitle className="mb-2 text-muted">
            Task Management Template
          </Card.Subtitle>
          <Card.Title>
            {task.name} - Due: {new Date(task.dueDate).toLocaleDateString()} - Created:{" "}
            {new Date(task.createdDate).toLocaleDateString()}
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
            />
            <MdOutlineDelete
              size={32}
              cursor={"pointer"}
              onClick={() => handleDelete(task.id)}
            />
          </div>
        </Card>
      ))}
    </>
  );
};

export default TaskList;
