package ecoza.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    void createAndGetItem() throws Exception {
        Item newItem = new Item(null, "Integration Test Item", "Description for integration test");

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name", is("Integration Test Item")));

        mockMvc.perform(get("/api/items")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Integration Test Item")));
    }

    @Test
    void updateItem() throws Exception {
        Item existingItem = new Item(null, "Original Item", "Original Description");
        Item savedItem = itemRepository.save(existingItem);

        Item updatedDetails = new Item(null, "Updated Integration Item", "Updated Description");

        mockMvc.perform(put("/api/items/{id}", savedItem.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedItem.getId().intValue())))
                .andExpect(jsonPath("$.name", is("Updated Integration Item")));

        mockMvc.perform(get("/api/items/{id}", savedItem.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Updated Integration Item")));
    }

    @Test
    void deleteItem() throws Exception {
        Item existingItem = new Item(null, "Item to Delete", "Description to Delete");
        Item savedItem = itemRepository.save(existingItem);

        mockMvc.perform(delete("/api/items/{id}", savedItem.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/items/{id}", savedItem.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
