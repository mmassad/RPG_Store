package com.mauremar.item_shop.server.controller;

import com.mauremar.item_shop.server.business.AdventurerService;
import com.mauremar.item_shop.server.business.OptimizeService;
import com.mauremar.item_shop.server.controllers.AdventurerController;
import com.mauremar.item_shop.server.domain.Adventurer;
import com.mauremar.item_shop.server.domain.Item;
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

import java.util.Optional;

@WebMvcTest(AdventurerController.class)
public class AdventurerControllerTest {
@Autowired
MockMvc mockMvc;

@MockBean
AdventurerService adventurerService;

@MockBean
OptimizeService optimizeService;

@Test
    void addItemDifferentJob() throws Exception{
    Integer advId = 1;
    Integer itemId = 1;
    Adventurer adv = new Adventurer(advId,"Test", 0, "Mage");
    Item item = new Item(itemId, "Expensive", 100, 100, "Warrior");

    Mockito.doThrow(new ResponseStatusException(HttpStatus.PRECONDITION_FAILED))
            .when(adventurerService)
            .addItem(advId,itemId);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/adventurers/" + advId + "/items/" + itemId)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());

}

}
