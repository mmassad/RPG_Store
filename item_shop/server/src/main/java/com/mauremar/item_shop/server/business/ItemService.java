package com.mauremar.item_shop.server.business;

import com.mauremar.item_shop.server.dao.ItemRepository;
import com.mauremar.item_shop.server.dao.ShopRepository;
import com.mauremar.item_shop.server.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ShopRepository shopRepository;

    public Item createItem(String name, Integer price, Integer attack, String job) {
        return itemRepository.save(new Item(name, price, attack, job));
    }

    public Item readById(Integer id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        }
        return null;
    }

    public List<Item> readAll() {
        return itemRepository.findAll();
    }

    public boolean update(Item newIt) {
        Optional<Item> item = itemRepository.findById(newIt.id);
        if (item.isPresent()) {
            itemRepository.save(newIt);
            return true;
        }
        return false;
    }

    public boolean delete(Integer id) {
        if (itemRepository.findById(id).isPresent()) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void checkOwnership(Integer id){
        var item = itemRepository.findById(id);
        if(item.get().getAdventurer() != null)
        {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Item already in use by another adventurer");
        }
    }
}

