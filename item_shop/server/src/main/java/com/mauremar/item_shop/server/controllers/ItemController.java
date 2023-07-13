package com.mauremar.item_shop.server.controllers;

import com.mauremar.item_shop.server.business.ItemService;
import com.mauremar.item_shop.server.domain.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping(consumes = "application/json")
    @Tag(name="Item")
    @Operation(description = "Create a new item", summary = "Create a new item")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public Item create(@RequestBody Item i) {
        return itemService.createItem(i.name, i.price, i.attack, i.job);
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    @Tag(name="Item")
    @Operation(description = "Read one item by a certain id", summary = "Read one item by a certain id")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public Item readById(@PathVariable(name = "id") Integer id) {
        Item item = itemService.readById(id);

        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return item;
    }

    @GetMapping(produces = "application/json")
    @Tag(name="Item")
    @Operation(description = "Read all items in the database",
                summary = "Read all items in the database")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public List<Item> read() {
        return itemService.readAll();
    }

    @PutMapping(consumes = "application/json")
    @Tag(name="Item")
    @Operation(description = "Edit item's name, job, attack or price",
            summary = "Edit item's name, job, attack or price")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void update(@RequestBody Item i) {
        if (!itemService.update(i)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @Tag(name="Item")
    @Operation(description = "Remove one item from database",
            summary = "Remove one item from database")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void delete(@PathVariable(name = "id") Integer id) {
        if (!itemService.delete(id)) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
    }

}

