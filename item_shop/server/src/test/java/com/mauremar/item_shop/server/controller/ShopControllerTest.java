package com.mauremar.item_shop.server.controller;

import com.mauremar.item_shop.server.business.ShopService;
import com.mauremar.item_shop.server.controllers.ShopController;
import com.mauremar.item_shop.server.domain.Adventurer;
import com.mauremar.item_shop.server.domain.Item;
import com.mauremar.item_shop.server.domain.Shop;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(ShopController.class)
public class ShopControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    ShopService shopService;

    @Test
    void addItemAlreadyInInventory() throws Exception{
        Integer shopId = 1;
        Integer itemId = 1;
        Shop shop = new Shop(shopId,"Test");
        Item item = new Item(itemId, "Expensive", 100, 100, "Mage");

        Mockito.doThrow(new ResponseStatusException(HttpStatus.CONFLICT))
                .when(shopService)
                .addItem(shopId,itemId);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shops/" + shopId + "/items/" + itemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());

    }
}
