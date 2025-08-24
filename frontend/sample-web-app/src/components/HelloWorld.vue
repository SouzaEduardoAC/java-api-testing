<template>
  <div class="crud-container">
    <h1>{{ msg }}</h1>

    <!-- Form for Create/Update -->
    <div class="item-form">
      <input type="hidden" v-model="item.id" />
      <div class="form-group">
        <input type="text" placeholder="Name (max 100 chars)" v-model="item.name" maxlength="100" required />
      </div>
      <div class="form-group">
        <textarea placeholder="Description (max 255 chars)" v-model="item.description" maxlength="255"></textarea>
      </div>
      <div class="form-actions">
        <button @click="saveItem" :disabled="!item.name">{{ item.id ? 'Update' : 'Create' }}</button>
        <button @click="resetForm" v-if="item.id">Cancel</button>
      </div>
       <p v-if="error" class="error">{{ error }}</p>
    </div>

    <!-- Items List -->
    <div class="item-list">
      <h2>Items</h2>
      <div v-if="loading">Loading...</div>
      <div v-else-if="!sortedItems.length">
        <p>No items found. Create one above!</p>
      </div>
      <ul v-else>
        <!-- Use the new sortedItems computed property for the list -->
        <li v-for="i in sortedItems" :key="i.id">
          <div class="item-details">
            <strong>{{ i.name }}</strong>
            <p>{{ i.description }}</p>
          </div>
          <div class="item-actions">
            <button @click="editItem(i)">Edit</button>
            <button @click="deleteItem(i.id)">Delete</button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import apiService from '../services/apiService';

export default {
  name: 'ItemCrud',
  props: {
    msg: String
  },
  data() {
    return {
      items: [],
      item: {
        id: null,
        name: '',
        description: '',
        version: null
      },
      loading: false,
      error: null
    };
  },
  computed: {
    sortedItems() {
      // Create a shallow copy of the items array and sort it alphabetically by name
      return [...this.items].sort((a, b) => a.name.localeCompare(b.name));
    }
  },
  created() {
    this.fetchItems();
  },
  methods: {
    async fetchItems() {
      this.loading = true;
      this.error = null;
      try {
        const response = await apiService.getItems();
        this.items = response.data;
      } catch (err) {
        this.error = 'Failed to fetch items.';
        console.error(err);
      } finally {
        this.loading = false;
      }
    },
    editItem(itemToEdit) {
      this.item = { ...itemToEdit };
    },
    resetForm() {
      this.item = {
        id: null,
        name: '',
        description: '',
        version: null
      };
    },
    async saveItem() {
      if (!this.item.name) {
        this.error = "Name is mandatory.";
        return;
      }
      this.error = null;
      try {
        if (this.item.id) {
          // Update
          await apiService.updateItem(this.item.id, this.item);
        } else {
          // Create
          await apiService.createItem(this.item);
        }
        this.resetForm();
        await this.fetchItems();
      } catch (err) {
        if (err.response && err.response.status === 409) {
          this.error = err.response.data;
        } else {
          this.error = `Failed to ${this.item.id ? 'update' : 'create'} item.`;
        }
        console.error(err);
      }
    },
    async deleteItem(id) {
      this.error = null;
      try {
        await apiService.deleteItem(id);
        await this.fetchItems();
      } catch (err) {
        this.error = 'Failed to delete item.';
        console.error(err);
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.crud-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.item-form {
  background: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.form-group {
  margin-bottom: 15px;
}

.form-group input[type="text"],
.form-group textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

.form-actions button {
  padding: 10px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  margin-right: 10px;
  background-color: #42b983;
  color: white;
}

.form-actions button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.form-actions button:last-child {
  background-color: #f44336;
}

.item-list ul {
  list-style-type: none;
  padding: 0;
}

.item-list li {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 15px;
  border-bottom: 1px solid #eee;
}

.item-list li:last-child {
  border-bottom: none;
}

.item-details {
  text-align: justify;
}

.item-details p {
  margin: 5px 0 0;
  color: #666;
}

.item-actions button {
   margin-left: 10px;
   padding: 5px 10px;
   cursor: pointer;
}

.error {
  color: red;
  margin-top: 10px;
}
</style>
