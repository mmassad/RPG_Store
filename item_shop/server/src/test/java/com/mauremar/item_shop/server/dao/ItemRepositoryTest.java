package com.mauremar.item_shop.server.dao;

import com.mauremar.item_shop.server.dao.AdventurerRepository;
import com.mauremar.item_shop.server.dao.ItemRepository;
import com.mauremar.item_shop.server.dao.ShopRepository;
import com.mauremar.item_shop.server.domain.Adventurer;
import com.mauremar.item_shop.server.domain.Item;
import com.mauremar.item_shop.server.domain.Shop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    AdventurerRepository adventurerRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    ItemRepository itemRepository;

    @AfterEach
    void tearDown(){
        adventurerRepository.deleteAll();
        shopRepository.deleteAll();
    }

    @Test
    void adventurerHasNoMoney(){
        var adv = new Adventurer(1,"Test",1,"Mage");
        adventurerRepository.save(adv);

        var shop = new Shop(1,"Endgame");
        var item = new Item(1,"Expensive", 100, 100, "Mage");

        shopRepository.save(shop);
        itemRepository.save(item);

        // ADDED ON 30-JAN
        item.setShops(Arrays.asList(shop));
        itemRepository.save(item);
        //

        shop.setItems(Arrays.asList(item));
        shopRepository.save(shop);


        Assertions.assertEquals(0, itemRepository.optimizeItem(1, 1).size());
    }

    @Test
    void adventurerWithDifferentJob(){
        var adv = new Adventurer(1,"Test",101,"Warrior");
        adventurerRepository.save(adv);

        var shop = new Shop(1,"Endgame");
        var item = new Item(1,"Expensive", 100, 100, "Mage");

        shopRepository.save(shop);
        itemRepository.save(item);

        // ADDED ON 30-JAN
        item.setShops(Arrays.asList(shop));
        itemRepository.save(item);
        //

        shop.setItems(Arrays.asList(item));
        shopRepository.save(shop);

        Assertions.assertEquals(0, itemRepository.optimizeItem(1, 1).size());
    }

    @Test
    void adventurerMatchesItem(){
        var adv = new Adventurer(1,"Test",11,"Mage");
        adventurerRepository.save(adv);

        var shop = new Shop(1,"Endgame");
        var item = new Item(1,"Expensive", 10, 100, "Mage");

        shopRepository.save(shop);
        itemRepository.save(item);

        shop.setItems(Arrays.asList(item));
        shopRepository.save(shop);

        Assertions.assertEquals(1, itemRepository.optimizeItem(1, 1).size());
    }
}
