import axios from 'axios';

const instance = axios.create({
  baseURL: 'http://localhost:8080/', // Backend base URL
  headers: {
    'Content-Type': 'application/json',
  },
});

export default instance;