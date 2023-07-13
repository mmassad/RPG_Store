package com.mauremar.item_shop.client.web;

import com.mauremar.item_shop.client.dto.AdventurerDto;
import com.mauremar.item_shop.client.service.AdventurerService;
import com.mauremar.item_shop.client.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.BadRequestException;

@Controller
@RequestMapping("/adventurers")
public class AdventurerController {

    private AdventurerService adventurerService;
    private ShopService shopService;

    public AdventurerController(AdventurerService adventurerService, ShopService shopService)
    {
        this.adventurerService = adventurerService;
        this.shopService = shopService;
    }

    @GetMapping
    public String adventurers(Model model) {
        model.addAttribute("allAdventurers", adventurerService.readAll());
        return "adventurers";
    }

    @GetMapping("/edit")
    public String editAdventurer(@RequestParam Integer id, Model model) {
        adventurerService.setActiveAdventurer(id);
        model.addAttribute("adventurer", adventurerService.readOne().orElseThrow());
        return "adventurerEdit";
    }

    @PostMapping("/edit")
    public String submitEditAdventurer(@ModelAttribute AdventurerDto adv, Model model) {
        adventurerService.setActiveAdventurer(adv.getId());
        try {
            adventurerService.update(adv);
        } catch (BadRequestException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", e.getMessage());
        }
        model.addAttribute("adventurer", adv);
        return "redirect:/adventurers";
    }

    @GetMapping("/delete")
    public String deleteAdventurer(@RequestParam Integer id)
    {
        adventurerService.setActiveAdventurer(id);
        adventurerService.delete();
        return "redirect:/adventurers";
    }

    @GetMapping("/create")
    public String createAdventurer() {

        return "adventurerNew";
    }

    @PostMapping("/create")
    public String createNewAdventurer(@ModelAttribute AdventurerDto adv, Model model)
    {
        adventurerService.create(adv);
        return "redirect:/adventurers";
    }

    @GetMapping("/items")
    public String adventurerItems(@RequestParam Integer id, Model model) {
        adventurerService.setActiveAdventurer(id);
        model.addAttribute("adventurerId", id);
        model.addAttribute("allItems", adventurerService.readOneItem());
        return "adventurerItems";
    }

    @GetMapping("/{advId}/items/remove")
    public String removeItem(@PathVariable (name = "advId") Integer advId, @RequestParam (name = "id") Integer id) {
        adventurerService.setActiveAdventurer(advId);
        adventurerService.removeItem(advId, id);
        return "redirect:/adventurers/items?id=" + advId;
    }

    @GetMapping("/{advId}/items/optimize")
    public String adventurerOptimizeShop(@PathVariable Integer advId, Model model) {
        adventurerService.setActiveAdventurer(advId);
        model.addAttribute("advId", advId);
        model.addAttribute("allShops", shopService.readAll());
        return "adventurerOptimizeItems";
    }

    @GetMapping("/{advId}/shops/{shopId}")
    public String shopBestItems(@PathVariable Integer advId,
                               @PathVariable Integer shopId, Model model) {
        adventurerService.setActiveAdventurer(advId);
        model.addAttribute("advId", advId);
        model.addAttribute("allItems", adventurerService.bestItem(advId, shopId));
        return "adventurerAddItems";
    }

    @GetMapping("/{advId}/{shopId}/items/add/{itemId}")
    public String addItem(@PathVariable (name = "advId") Integer advId,
                          @PathVariable (name = "shopId") Integer shopId,
                          @PathVariable (name = "itemId") Integer itemId,
                          Model model) {
        adventurerService.setActiveAdventurer(advId);
        try {
            adventurerService.addItem(advId, itemId);
        } catch (ResponseStatusException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMsg", e.getReason());
            adventurerService.setActiveAdventurer(advId);
            model.addAttribute("advId", advId);
            model.addAttribute("allItems", adventurerService.bestItem(advId, shopId));
            return "adventurerAddItems";
        }
        return "redirect:/adventurers/items?id=" + advId;
    }
}
