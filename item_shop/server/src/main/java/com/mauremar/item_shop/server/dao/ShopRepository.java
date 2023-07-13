package com.mauremar.item_shop.server.dao;

import com.mauremar.item_shop.server.domain.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Integer> { }
