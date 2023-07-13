package com.mauremar.item_shop.client.service;

import com.mauremar.item_shop.client.client.AdventurerClient;
import com.mauremar.item_shop.client.dto.AdventurerDto;
import com.mauremar.item_shop.client.dto.ItemDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AdventurerService {
    private AdventurerClient adventurerClient;
    private boolean isAdvActive = false;

    public AdventurerService(AdventurerClient adventurerClient) {
        this.adventurerClient = adventurerClient;
    }

    public void create(AdventurerDto adv) {
        adventurerClient.create(adv);
    }

    public void setActiveAdventurer(Integer id) {
        adventurerClient.setActiveAdventurer(id);
        isAdvActive = true;
    }

    public boolean isAdvActive() {
        return isAdvActive;
    }

    public Optional<AdventurerDto> readOne() {
        return adventurerClient.readOne();
    }

    public Collection<AdventurerDto> readAll() {
        return adventurerClient.readAll();
    }

    public void update(AdventurerDto adv) {
        adventurerClient.updateOne(adv);
    }

    public void delete(){adventurerClient.delete();}

    public List<ItemDto> readOneItem() {return adventurerClient.readOneItem();}

    public void removeItem(Integer adventurerId, Integer itemId) {adventurerClient.removeItem(adventurerId, itemId);}

    public List<ItemDto> bestItem(Integer adventurerId, Integer shopId){return adventurerClient.bestItem(adventurerId, shopId);}

    public void addItem(Integer advId, Integer itemId) {adventurerClient.addItem(advId, itemId);}
}
