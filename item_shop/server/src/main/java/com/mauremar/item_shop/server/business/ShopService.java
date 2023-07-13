package com.mauremar.item_shop.server.business;

import com.mauremar.item_shop.server.dao.ItemRepository;
import com.mauremar.item_shop.server.domain.Shop;
import com.mauremar.item_shop.server.dao.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Shop createShop(String location) {
        return shopRepository.save(new Shop(location));
    }

    public Shop readById(Integer id) {
        Optional<Shop> shop = shopRepository.findById(id);
        if (shop.isPresent()) {
            return shop.get();
        }
        return null;
    }

    public List<Shop> readAll() {
        return shopRepository.findAll();
    }

    public boolean update(Shop newShp) {
        Optional<Shop> shop = shopRepository.findById(newShp.getId());
        if (shop.isPresent()) {
            shopRepository.save(newShp);
            return true;
        }
        return false;
    }

    public boolean delete(Integer id) {
        if (shopRepository.findById(id).isPresent()) {
            shopRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void addItem(Integer shopId, Integer itemId)
    {
        var shop = shopRepository.findById(shopId);
        var item = itemRepository.findById(itemId);

        if (!shop.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found");
        }
        if(!item.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }

        if (shop.get().getItems().contains(item.get()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already in shop");
        }

        shop.get().getItems().add(item.get());
        shopRepository.save(shop.get());
    }

    public void deleteItem(Integer shopId, Integer itemId)
    {
        var shop = shopRepository.findById(shopId);
        var item = itemRepository.findById(itemId);

        if (!shop.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shop not found");
        }
        if(!item.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }

        if(shop.get().getItems()!=null && !shop.get().getItems().contains(item.get()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item doesn't belong to shop");
        }

        shop.get().getItems().remove(item.get());
        shopRepository.save(shop.get());
    }
}

