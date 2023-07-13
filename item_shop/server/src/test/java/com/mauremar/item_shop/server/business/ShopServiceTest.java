package com.mauremar.item_shop.server.business;



import com.mauremar.item_shop.server.ItemShopApplication;
import com.mauremar.item_shop.server.dao.ItemRepository;
import com.mauremar.item_shop.server.dao.ShopRepository;
import com.mauremar.item_shop.server.domain.Item;
import com.mauremar.item_shop.server.domain.Shop;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = ItemShopApplication.class)
public class ShopServiceTest {
    @Autowired
    ShopService shopService;

    @MockBean
    ShopRepository shopRepository;

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    ItemService itemService;

    @Test
    void removeItemNotInInventory() {

        Shop shop = new Shop(1,"Test");
        var optionalShop = Optional.of(shop);

        Item item1 = new Item(1, "Expensive", 100, 100, "Mage");
        Item item2 = new Item(2, "Cheap", 1, 1, "Mage");

        shop.setItems(Arrays.asList(item2));

        Mockito.when(shopRepository.findById(shop.getId()))
                .thenReturn(optionalShop);
        Mockito.when(itemRepository.findById(item1.getId()))
                .thenReturn(Optional.of(item1));

        assertThrows(ResponseStatusException.class, () ->
        {
            shopService.deleteItem(shop.getId(), item1.getId());
        }, "Item doesn't belong to shop");
    }

}


