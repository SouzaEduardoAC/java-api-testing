package ecoza.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createItem() throws Exception {
        Item newItem = new Item(null, "Test Item", "Test Description");
        Item savedItem = new Item(1L, "Test Item", "Test Description");

        when(itemService.createItem(any(Item.class))).thenReturn(savedItem);

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Item")));

        verify(itemService, times(1)).createItem(any(Item.class));
    }

    @Test
    void getAllItems() throws Exception {
        Item item1 = new Item(1L, "Item 1", "Description 1");
        Item item2 = new Item(2L, "Item 2", "Description 2");
        when(itemService.getAllItems()).thenReturn(Arrays.asList(item1, item2));

        mockMvc.perform(get("/api/items")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Item 1")));

        verify(itemService, times(1)).getAllItems();
    }

    @Test
    void getItemByIdFound() throws Exception {
        Item item = new Item(1L, "Test Item", "Test Description");
        when(itemService.getItemById(1L)).thenReturn(item);

        mockMvc.perform(get("/api/items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Item")));

        verify(itemService, times(1)).getItemById(1L);
    }

    @Test
    void getItemByIdNotFound() throws Exception {
        when(itemService.getItemById(1L)).thenThrow(new ItemNotFoundException(1L));

        mockMvc.perform(get("/api/items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(itemService, times(1)).getItemById(1L);
    }

    @Test
    void updateItem() throws Exception {
        Item updatedDetails = new Item(null, "Updated Item", "Updated Description");
        Item updatedItem = new Item(1L, "Updated Item", "Updated Description");

        when(itemService.updateItem(any(Long.class), any(Item.class))).thenReturn(updatedItem);

        mockMvc.perform(put("/api/items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Item")));

        verify(itemService, times(1)).updateItem(any(Long.class), any(Item.class));
    }

    @Test
    void deleteItem() throws Exception {
        doNothing().when(itemService).deleteItem(1L);

        mockMvc.perform(delete("/api/items/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(itemService, times(1)).deleteItem(1L);
    }
}