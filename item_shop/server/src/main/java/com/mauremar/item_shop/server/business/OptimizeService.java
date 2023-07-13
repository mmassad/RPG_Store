package com.mauremar.item_shop.server.business;

import com.mauremar.item_shop.server.dao.ItemRepository;
import com.mauremar.item_shop.server.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
public class OptimizeService {
    @Autowired
    private ItemRepository itemRepository;

    public Collection<Item> optimizeItem(Integer advId, Integer shopId)
    {
        return itemRepository.optimizeItem(advId, shopId);
    }
}
