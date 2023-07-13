package com.mauremar.item_shop.client.web;

import com.mauremar.item_shop.client.dto.ItemDto;
import com.mauremar.item_shop.client.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;

@Controller
@RequestMapping("/items")
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String items(Model model) {
        model.addAttribute("allItems", itemService.readAll());
        return "items";
    }

    @GetMapping("/edit")
    public String editItem(@RequestParam Integer id, Model model) {
        itemService.setActiveItem(id);
        model.addAttribute("item", itemService.readOne().orElseThrow());
        return "itemEdit";
    }

    @PostMapping("/edit")
    public String submitEditItem(@ModelAttribute ItemDto item, Model model) {
        itemService.setActiveItem(item.getId());
        try {
            itemService.update(item);
        } catch (BadRequestException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", e.getMessage());
        }
        model.addAttribute("item", item);
        return "redirect:/items";
    }

    @GetMapping("/delete")
    public String deleteItem(@RequestParam Integer id)
    {
        itemService.setActiveItem(id);
        itemService.delete();
        return "redirect:/items";
    }

    @GetMapping("/create")
    public String createItem() {
        return "itemNew";
    }

    @PostMapping("/create")
    public String createNewItem(@ModelAttribute ItemDto item, Model model)
    {
        itemService.create(item);
        return "redirect:/items";
    }


}
