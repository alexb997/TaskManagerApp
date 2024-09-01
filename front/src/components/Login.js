import React, { useState } from "react";
import { Modal, Button, Form, Alert } from "react-bootstrap";
import instance from "../axios";
import "../asset/LoginModal.css";

const LoginModal = ({ show, handleClose }) => {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
  });

  const [error, setError] = useState("");
  const [isCreatingUser, setIsCreatingUser] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await instance.post("/api/users/login", credentials);
      const username = credentials.username;
      localStorage.setItem("username", username);
      alert(response.data);
      handleClose();
    } catch (err) {
      setError(
        err.response
          ? err.response.data
          : "Invalid username or password. Please try again."
      );
    }
  };

  const handleCreateUser = async (e) => {
    e.preventDefault();
    try {
      const response = await instance.post("/api/users", credentials);
      setSuccessMessage("User created successfully! You can now log in.");
      setIsCreatingUser(false);
      setError("");
    } catch (err) {
      setError(
        err.response
          ? err.response.data
          : "Failed to create user. Please try again."
      );
    }
  };

  const handleCreationSwitch = (value) => {
    setIsCreatingUser(value);
    setSuccessMessage("");
    setError("");
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>{isCreatingUser ? "Create Account" : "Login"}</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        {error && (
          <Alert variant="danger" className="error-message">
            {error}
          </Alert>
        )}
        {successMessage && (
          <Alert variant="success" className="success-message">
            {successMessage}
          </Alert>
        )}

        <Form
          onSubmit={isCreatingUser ? handleCreateUser : handleLogin}
          className="login-form"
        >
          <Form.Group controlId="username" className="mb-3">
            <Form.Label className="login-label">Username</Form.Label>
            <Form.Control
              type="text"
              name="username"
              value={credentials.username}
              onChange={handleChange}
              placeholder="Enter your username"
              required
              className="login-input"
            />
          </Form.Group>
          <Form.Group controlId="password" className="mb-3">
            <Form.Label className="login-label">Password</Form.Label>
            <Form.Control
              type="password"
              name="password"
              value={credentials.password}
              onChange={handleChange}
              placeholder="Enter your password"
              required
              className="login-input"
            />
          </Form.Group>
          <Button
            variant="primary"
            type="submit"
            className="w-100 login-button"
          >
            {isCreatingUser ? "Create Account" : "Login"}
          </Button>
        </Form>

        <div className="text-center mt-3">
          {isCreatingUser ? (
            <Button
              variant="link"
              onClick={() => handleCreationSwitch(false)}
              className="login-switch-btn"
            >
              Already have an account? Login
            </Button>
          ) : (
            <Button
              variant="link"
              onClick={() => handleCreationSwitch(true)}
              className="login-switch-btn"
            >
              Don't have an account? Create one
            </Button>
          )}
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default LoginModal;
