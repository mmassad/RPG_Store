package com.mauremar.item_shop.server.controllers;

import com.mauremar.item_shop.server.business.AdventurerService;
import com.mauremar.item_shop.server.business.OptimizeService;
import com.mauremar.item_shop.server.domain.Adventurer;
import com.mauremar.item_shop.server.domain.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("api/v1/adventurers")
public class AdventurerController {
    @Autowired
    private AdventurerService adventurerService;
    @Autowired
    private OptimizeService optimizeService;

    @PostMapping(consumes = "application/json")
    @Tag(name="Adventurer")
    @Operation(description= "Create a new adventurer", summary = "Create a new adventurer")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public Adventurer create(@RequestBody Adventurer e) {
        return adventurerService.createAdventurer(e.name, e.gold, e.job);
    }

    @GetMapping(produces = "application/json", path = "/{id}")
    @Tag(name="Adventurer")
    @Operation(description= "Read one adventurer by a certain id", summary = "Read one adventurer by a certain id")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public Adventurer readById(@PathVariable(name = "id") Integer id) {
        Adventurer adventurer = adventurerService.readById(id);

        if (adventurer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return adventurer;
    }

    @GetMapping(produces = "application/json")
    @Tag(name="Adventurer")
    @Operation(description = "Read all adventurers in the database", summary = "Read all adventurers in the database")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public List<Adventurer> read() {
        return adventurerService.readAll();
    }

    @PutMapping(consumes = "application/json")
    @Tag(name="Adventurer")
    @Operation(description = "Edit one adventurer's name, job and/or gold", summary = "Edit one adventurer's name, job and/or gold" )
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void update(@RequestBody Adventurer e) {
        if (!adventurerService.update(e)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    @Tag(name="Adventurer")
    @Operation(description = "Remove one adventurer from database", summary = "Remove one adventurer from database")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void delete(@PathVariable(name = "id") Integer id) {
        if (!adventurerService.delete(id)) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
    }

    @PostMapping(consumes = "application/json", path="/{advId}/items/{itemId}")
    @Tag(name="Adventurer")
    @Operation(description = "Add one item to adventurer's inventory", summary = "Add one item to adventurer's inventory")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Adventurer or Item not found", content = @Content),
            @ApiResponse(responseCode = "406", description = "Item already in use by another adventurer", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item already with adventurer", content = @Content),
            @ApiResponse(responseCode = "412", description = "Item from different job than adventurer", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void addItem(@PathVariable(name="advId") Integer advId, @PathVariable(name="itemId") Integer itemId)
    {
        adventurerService.addItem(advId, itemId);
    }

    @DeleteMapping(path="/{advId}/items/{itemId}")
    @PostMapping(consumes = "application/json", path="/{advId}/items/{itemId}")
    @Tag(name="Adventurer")
    @Operation( description = "Remove one item from adventurer's inventory", summary = "Remove one item from adventurer's inventory")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Adventurer or Item not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Item doesn't belong to adventurer", content = @Content),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public void deleteItem(@PathVariable(name="advId") Integer advId, @PathVariable(name="itemId") Integer itemId)
    {
        adventurerService.deleteItem(advId, itemId);
    }

    @GetMapping(produces = "application/json", path="/{advId}/shops/{shopId}")
    @Tag(name="Adventurer")
    @Operation(description = "Sort items from shop tailed to adventurer's stats", summary = "Sort items from shop tailed to adventurer's stats")
    @ResponseBody
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", content = @Content)
    })
    public Collection<Item> optimizeItem(@PathVariable(name="advId") Integer advId, @PathVariable(name="shopId") Integer shopId)
    {
        return optimizeService.optimizeItem(advId, shopId);
    }
}
