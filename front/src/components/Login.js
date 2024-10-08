import React, { useState, useEffect } from "react";
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
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLoggedIn(true);
    }
  }, [show]);

  const handleChange = (e) => {
    setError("");
    setSuccessMessage("");
    const { name, value } = e.target;
    setCredentials({ ...credentials, [name]: value });
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await instance.post("/api/users/login", credentials);
      const { token } = response.data;

      if (token) {
        localStorage.setItem("username", credentials.username);
        localStorage.setItem("token", token);
        setIsLoggedIn(true);
      } else {
        throw new Error("Token not received");
      }
    } catch (err) {
      console.error("Login error:", err);
      setError(
        err.response
          ? err.response.data.error
          : "Invalid username or password. Please try again."
      );
    }
  };

  const handleCreateUser = async (e) => {
    e.preventDefault();
    try {
      const response = await instance.post("/api/users", credentials);
      alert(response.data);
      setSuccessMessage("User created successfully! You can now log in.");
      setIsCreatingUser(false);
      setError("");
      localStorage.setItem("username", credentials.username);
      handleClose();
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

  const handleLogout = () => {
    localStorage.removeItem("username");
    localStorage.removeItem("token");
    setIsLoggedIn(false);
  };

  return (
    <Modal show={show} onHide={handleClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>
          {isCreatingUser ? "Create Account" : isLoggedIn ? "Logout" : "Login"}
        </Modal.Title>
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

        {isLoggedIn ? (
          <div>
            <p>Welcome back, {credentials.username}</p>
            <Button
              variant="danger"
              onClick={handleLogout}
              className="w-100 login-button"
            >
              Logout
            </Button>
          </div>
        ) : (
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
        )}

        <div className="text-center mt-3">
          {isCreatingUser ? (
            <Button
              variant="link"
              onClick={() => handleCreationSwitch(false)}
              className="login-switch-btn"
            >
              Already have an account? Login
            </Button>
          ) : !isLoggedIn ? (
            <Button
              variant="link"
              onClick={() => handleCreationSwitch(true)}
              className="login-switch-btn"
            >
              Don't have an account? Create one
            </Button>
          ) : null}
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default LoginModal;
