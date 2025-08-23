package ecoza.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@Valid @RequestBody Item item) {
        log.info("Creating item: {}", item);
        return itemService.createItem(item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        log.info("Getting all items");
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        log.info("Getting item by id: {}", id);
        return itemService.getItemById(id);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @Valid @RequestBody Item itemDetails) {
        log.info("Updating item with id: {}: {}", id, itemDetails);
        return itemService.updateItem(id, itemDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long id) {
        log.info("Deleting item with id: {}", id);
        itemService.deleteItem(id);
    }
}