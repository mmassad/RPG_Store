package com.mauremar.item_shop.server.business;

import com.mauremar.item_shop.server.dao.AdventurerRepository;
import com.mauremar.item_shop.server.dao.ItemRepository;
import com.mauremar.item_shop.server.domain.Adventurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AdventurerService {

    @Autowired
    private AdventurerRepository adventurerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    public Adventurer createAdventurer(String name, Integer gold, String job) {
        return adventurerRepository.save(new Adventurer(name, gold, job));
    }

    public Adventurer readById(Integer id) {
        Optional<Adventurer> adventurer = adventurerRepository.findById(id);
        if (adventurer.isPresent()) {
            return adventurer.get();
        }
        return null;
    }

    public List<Adventurer> readAll() {
        return adventurerRepository.findAll();
    }

    public boolean update(Adventurer newAdv) {
        Optional<Adventurer> adventurer = adventurerRepository.findById(newAdv.id);
        if (adventurer.isPresent()) {
            adventurerRepository.save(newAdv);
            return true;
        }
        return false;
    }

    public boolean delete(Integer id) {
        if (adventurerRepository.findById(id).isPresent()) {
            adventurerRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public void addItem(Integer adventurerId, Integer itemId)
    {
        var adventurer = adventurerRepository.findById(adventurerId);
        var item = itemRepository.findById(itemId);

        if (!adventurer.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adventurer not found");
        }
        if (!item.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }

        if (adventurer.get().getItems() != null && adventurer.get().getItems().contains(item.get()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item already with adventurer");
        }

        if(!adventurer.get().getJob().equals(item.get().getJob()))
        {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Item from different job than adventurer");
        }

        itemService.checkOwnership(itemId); //Check if item is with another adventurer
        var i = item.get();
        i.setAdventurer(adventurer.get());
        itemRepository.saveAndFlush(i);

        //Remove item price from adventurer's gold
        var adv = adventurer.get();
        var gold = adv.getGold();
        adv.setGold(gold - i.getPrice());
        adventurerRepository.saveAndFlush(adv);
    }

    public void deleteItem(Integer adventurerId, Integer itemId)
    {
        var adventurer = adventurerRepository.findById(adventurerId);
        var item = itemRepository.findById(itemId);

        if (!adventurer.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adventurer not found");
        }
        if (!item.isPresent())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }

        if(!adventurer.get().getItems().contains(item.get()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Item doesn't belong to adventurer");
        }

        var i = item.get();
        i.setAdventurer(null);
        itemRepository.saveAndFlush(i);
    }
}
