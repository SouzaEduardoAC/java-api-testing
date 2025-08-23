package ecoza.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllItems() {
        Item item1 = new Item(1L, "Item 1", "Description 1", null);
        Item item2 = new Item(2L, "Item 2", "Description 2", null);
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.findAll()).thenReturn(items);

        List<Item> result = itemService.getAllItems();

        assertEquals(2, result.size());
        assertTrue(result.contains(item1));
        assertTrue(result.contains(item2));
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    void getItemByIdFound() {
        Item item = new Item(1L, "Item 1", "Description 1", null);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Item result = itemService.getItemById(1L);

        assertEquals(item, result);
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void getItemByIdNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.getItemById(1L));
        verify(itemRepository, times(1)).findById(1L);
    }

    @Test
    void createItem() {
        Item item = new Item(null, "New Item", "New Description", null);
        Item savedItem = new Item(1L, "New Item", "New Description", null);

        when(itemRepository.save(item)).thenReturn(savedItem);

        Item result = itemService.createItem(item);

        assertEquals(savedItem, result);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    void updateItemFound() {
        Item existingItem = new Item(1L, "Old Item", "Old Description", 0L); // Add initial version
        Item updatedDetails = new Item(1L, "Updated Item", "Updated Description", 0L); // Pass ID and version
        Item savedItem = new Item(1L, "Updated Item", "Updated Description", 1L); // Mock the saved item with incremented version

        when(itemRepository.save(any(Item.class))).thenReturn(savedItem);

        Item result = itemService.updateItem(1L, updatedDetails);

        assertEquals(savedItem, result);
        assertEquals("Updated Item", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(itemRepository, times(1)).save(any(Item.class));
    }

    @Test
    void deleteItemFound() {
        Item item = new Item(1L, "Item to Delete", "Description to Delete", null);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        doNothing().when(itemRepository).delete(item);

        itemService.deleteItem(1L);

        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    void deleteItemNotFound() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> itemService.deleteItem(1L));
        verify(itemRepository, times(1)).findById(1L);
        verify(itemRepository, never()).delete(any(Item.class));
    }
}