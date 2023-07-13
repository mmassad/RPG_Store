package com.mauremar.item_shop.client.web;

import com.mauremar.item_shop.client.dto.ShopDto;
import com.mauremar.item_shop.client.service.ShopService;
import com.mauremar.item_shop.client.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.BadRequestException;

@Controller
@RequestMapping("/shops")
public class ShopController {

    private ShopService shopService;

    private ItemService itemService;

    public ShopController(ShopService shopService, ItemService itemService) {

        this.shopService = shopService;
        this.itemService = itemService;
    }

    @GetMapping
    public String shops(Model model) {
        model.addAttribute("allShops", shopService.readAll());
        return "shops";
    }

    @GetMapping("/edit")
    public String editShop(@RequestParam Integer id, Model model) {
        shopService.setActiveShop(id);
        model.addAttribute("shop", shopService.readOne().orElseThrow());
        return "shopEdit";
    }

    @PostMapping("/edit")
    public String submitEditShop(@ModelAttribute ShopDto shop, Model model) {
        shopService.setActiveShop(shop.getId());
        try {
            shopService.update(shop);
        } catch (BadRequestException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", e.getMessage());
        }
        model.addAttribute("shop", shop);
        return "redirect:/shops";
    }

    @GetMapping("/delete")
    public String deleteShop(@RequestParam Integer id)
    {
        shopService.setActiveShop(id);
        shopService.delete();
        return "redirect:/shops";
    }

    @GetMapping("/create")
    public String createShop() {
        return "shopNew";
    }

    @PostMapping("/create")
    public String createNewShop(@ModelAttribute ShopDto shop, Model model)
    {
        shopService.create(shop);
        return "redirect:/shops";
    }

    @GetMapping("/items")
    public String shopItems(@RequestParam Integer id, Model model) {
        shopService.setActiveShop(id);
        model.addAttribute("shopId", id);
        model.addAttribute("allItems", shopService.readOneItem());
        return "shopItems";
    }

    @GetMapping("/{shopId}/items/remove")
    public String removeItem(@PathVariable (name = "shopId") Integer shopId, @RequestParam (name = "id") Integer id) {
        shopService.setActiveShop(shopId);
        shopService.removeItem(shopId, id);
        return "redirect:/shops/items?id=" + shopId;
    }

    @GetMapping("/{shopId}/items/add")
    public String shopAddItems(@PathVariable Integer shopId, Model model) {
        shopService.setActiveShop(shopId);
        model.addAttribute("shopId", shopId);
        model.addAttribute("allItems", itemService.readAll());
        return "shopAddItems";
    }
    @GetMapping("/{shopId}/items/add/{itemId}")
    public String addItem(@PathVariable (name = "shopId") Integer shopId,
                          @PathVariable (name = "itemId") Integer itemId,
                          Model model) {
        shopService.setActiveShop(shopId);
        try {
            shopService.addItem(shopId, itemId);
        } catch (ResponseStatusException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", e.getReason());
            shopService.setActiveShop(shopId);
            model.addAttribute("shopId", shopId);
            model.addAttribute("allItems", itemService.readAll());
            return "shopAddItems";
        }
        return "redirect:/shops/items?id=" + shopId;
    }
}
