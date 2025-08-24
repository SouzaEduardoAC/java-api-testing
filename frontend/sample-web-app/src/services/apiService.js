import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/items', // Adjust if your backend URL is different
  headers: {
    'Content-Type': 'application/json'
  }
});

export default {
  getItems() {
    return apiClient.get('/');
  },
  getItem(id) {
    return apiClient.get(`/${id}`);
  },
  createItem(data) {
    return apiClient.post('/', data);
  },
  updateItem(id, data) {
    return apiClient.put(`/${id}`, data);
  },
  deleteItem(id) {
    return apiClient.delete(`/${id}`);
  }
};