import axios from 'axios';

const http = axios.create({
  baseURL: '/api',
  headers: {
    'Cache-Control': 'no-cache, no-store, must-revalidate',
    Pragma: 'no-cache',
    Expires: '-1'
  },
  credentials: true
});

http.interceptors.request.use((config) => {
  // TODO
  return config;
}, (error) => {
  // TODO
  return Promise.reject(error);
});

http.interceptors.response.use((response) => {
  // TODO
  return response;
}, (error) => {
  // TODO
  return Promise.reject(error);
});

export default http;