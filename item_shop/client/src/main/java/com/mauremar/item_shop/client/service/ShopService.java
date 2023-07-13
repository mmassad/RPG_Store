package com.mauremar.item_shop.client.service;

import com.mauremar.item_shop.client.client.ShopClient;
import com.mauremar.item_shop.client.dto.ItemDto;
import com.mauremar.item_shop.client.dto.ShopDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {
    private ShopClient shopClient;
    private boolean isShopActive = false;

    public ShopService(ShopClient shopClient) {
        this.shopClient = shopClient;
    }

    public void create(ShopDto shop) {
        shopClient.create(shop);
    }

    public void setActiveShop(Integer id) {
        shopClient.setActiveShop(id);
        isShopActive = true;
    }

    public boolean isShopActive() {
        return isShopActive;
    }

    public Optional<ShopDto> readOne() {
        return shopClient.readOne();
    }

    public Collection<ShopDto> readAll() {
        return shopClient.readAll();
    }

    public void update(ShopDto shop) {
        shopClient.updateOne(shop);
    }

    public void delete(){shopClient.delete();}

    public List<ItemDto> readOneItem() {return shopClient.readOneItem();}

    public void removeItem(Integer shopId, Integer itemId) {shopClient.removeItem(shopId, itemId);}

    public void addItem(Integer shopId, Integer itemId) {shopClient.addItem(shopId, itemId);}
}
