package com.mauremar.item_shop.server.controllers;

import com.mauremar.item_shop.server.business.ShopService;
import com.mauremar.item_shop.server.domain.Shop;
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
@RequestMapping("api/v1/shops")
public class ShopController {
    @Autowired
    private ShopService shopService;

    @PostMapping(consumes = "application/json")
    @Tag(name="Shop")
    @Operation(description = "Create a new shop",
            summary = "Create a new shop")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public Shop create(@RequestBody Shop s) {
        return shopService.createShop(s.getLocation());
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    @Tag(name="Shop")
    @Operation(description = "Read one shop by a certain id",
            summary = "Read one shop by a certain id")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public Shop readById(@PathVariable(name = "id") Integer id) {
        Shop shop = shopService.readById(id);

        if (shop == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return shop;
    }

    @GetMapping(produces = "application/json")
    @Tag(name="Shop")
    @Operation(description = "Read all shops in the database",
            summary = "Read all shops in the database")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public List<Shop> read() {
        return shopService.readAll();
    }

    @PutMapping(consumes = "application/json")
    @Tag(name="Shop")
    @Operation(description = "Edit one shop's location",
            summary = "Edit one shop's location")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void update(@RequestBody Shop s) {
        if (!shopService.update(s)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @Tag(name="Shop")
    @Operation(description = "Delete shop from the database",
            summary = "Delete shop from the database")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void delete(@PathVariable(name = "id") Integer id) {
        if (!shopService.delete(id)) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping(consumes = "application/json", path="/{shopId}/items/{itemId}")
    @Tag(name="Shop")
    @Operation(description = "Add one item to the shop's inventory",
            summary = "Add one item to the shop's inventory")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Shop or Item not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item already in shop", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void addItem(@PathVariable(name="shopId") Integer shopId, @PathVariable(name="itemId") Integer itemId)
    {
        shopService.addItem(shopId, itemId);
    }

    @DeleteMapping(path="/{shopId}/items/{itemId}")
    @Tag(name="Shop")
    @Operation(description = "Remove one item from shop's inventory",
            summary = "Remove one item from shop's inventory")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Shop or Item not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item doesn't belong to shop", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void deleteItem(@PathVariable(name="shopId") Integer shopId, @PathVariable(name="itemId") Integer itemId)
    {
        shopService.deleteItem(shopId, itemId);
    }
}
