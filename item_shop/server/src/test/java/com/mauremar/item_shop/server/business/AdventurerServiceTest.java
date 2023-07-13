package com.mauremar.item_shop.server.business;


import com.mauremar.item_shop.server.ItemShopApplication;
import com.mauremar.item_shop.server.dao.AdventurerRepository;
import com.mauremar.item_shop.server.dao.ItemRepository;
import com.mauremar.item_shop.server.domain.Adventurer;
import com.mauremar.item_shop.server.domain.Item;
import org.junit.jupiter.api.Assertions;
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
public class AdventurerServiceTest {
    @Autowired
    AdventurerService adventurerService;

    @MockBean
    AdventurerRepository adventurerRepository;

    @MockBean
    ItemRepository itemRepository;

    @MockBean
    ItemService itemService;

    @Test
    void addItemAlreadyUsed() {
        Adventurer adv = new Adventurer(1,"Test", 0, "Mage");
        Item item = new Item(1, "Expensive", 100, 100, "Mage");

        Mockito.when(adventurerRepository.findById(adv.getId()))
                .thenReturn(Optional.of(adv));
        Mockito.when(itemRepository.findById(item.getId()))
                .thenReturn(Optional.of(item));

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE))
                .when(itemService)
                .checkOwnership(item.getId());

        assertThrows(ResponseStatusException.class, () ->
        {
            adventurerService.addItem(adv.getId(), item.getId());
        }, "Item already in use by another adventurer");
    }

}

