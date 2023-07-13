package com.mauremar.item_shop.client.service;

import com.mauremar.item_shop.client.client.ItemClient;
import com.mauremar.item_shop.client.dto.ItemDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ItemService {
    private ItemClient itemClient;
    private boolean isItemActive = false;

    public ItemService(ItemClient itemClient) {
        this.itemClient = itemClient;
    }

    public void create(ItemDto item) {
        itemClient.create(item);
    }

    public void setActiveItem(Integer id) {
        itemClient.setActiveItem(id);
        isItemActive = true;
    }

    public boolean isItemActive() {
        return isItemActive;
    }

    public Optional<ItemDto> readOne() {
        return itemClient.readOne();
    }

    public Collection<ItemDto> readAll() {
        return itemClient.readAll();
    }

    public void update(ItemDto item) {
        itemClient.updateOne(item);
    }

    public void delete(){itemClient.delete();}


}
