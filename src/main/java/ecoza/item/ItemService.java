package ecoza.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import ecoza.infra.cache.CacheConfig;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final ItemRepository itemRepository;

    @Cacheable(value = "itemsList")
    public List<Item> getAllItems() {
        log.info("Getting all items from database");
        return itemRepository.findAll();
    }


    @Cacheable(value = CacheConfig.ITEMS_CACHE, key = "#id")
    public Item getItemById(Long id) {
        log.info("Getting item by id from database: {}", id);
        return itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @Caching(put = @CachePut(value = CacheConfig.ITEMS_CACHE, key = "#result.id"), evict = @CacheEvict(value = "itemsList", allEntries = true))
    public Item createItem(Item item) {
        log.info("Creating item in database: {}", item);
        return itemRepository.save(item);
    }

    @Caching(put = @CachePut(value = CacheConfig.ITEMS_CACHE, key = "#id"), evict = @CacheEvict(value = "itemsList", allEntries = true))
    public Item updateItem(Long id, Item itemDetails) {
        log.info("Updating item with id in database: {}: {}", id, itemDetails);
        itemDetails.setId(id);
        return itemRepository.save(itemDetails);
    }

    @Caching(evict = {
        @CacheEvict(value = CacheConfig.ITEMS_CACHE, key = "#id"),
        @CacheEvict(value = "itemsList", allEntries = true)
    })
    public void deleteItem(Long id) {
        log.info("Deleting item with id from database: {}", id);
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
        itemRepository.delete(item);
    }
}