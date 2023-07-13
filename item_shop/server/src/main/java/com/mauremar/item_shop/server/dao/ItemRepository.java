package com.mauremar.item_shop.server.dao;

import com.mauremar.item_shop.server.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "SELECT i.* FROM item i " +
            "INNER JOIN shop_items si on si.item_id = i.id AND si.shop_id = :shopId " +
            "INNER JOIN (SELECT a.job as job, a.gold as gold FROM adventurer a " +
            "WHERE a.id = :advId) as adv on i.job = adv.job " +
            "WHERE i.price < adv.gold AND i.adventurer_id is null " +
            "ORDER BY i.attack DESC",
    nativeQuery = true)
    Collection<Item> optimizeItem (@Param("advId") Integer advId, @Param("shopId") Integer shopId);
}
