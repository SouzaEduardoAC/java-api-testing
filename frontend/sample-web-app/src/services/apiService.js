import axios from 'axios';

const apiClient = axios.create({
  baseURL: '/api', // Use relative path for proxying through Nginx
  headers: {
    'Content-Type': 'application/json'
  }
});

export default {
  getItems() {
    return apiClient.get('/items');
  },
  getItem(id) {
    return apiClient.get(`/items/${id}`);
  },
  createItem(data) {
    return apiClient.post('/items', data);
  },
  updateItem(id, data) {
    return apiClient.put(`/items/${id}`, data);
  },
  deleteItem(id) {
    return apiClient.delete(`/items/${id}`);
  }
};