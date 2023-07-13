package com.mauremar.item_shop.client.dto;

import java.util.List;
public class ShopDto {
    private Integer id;
    private String location;

    public List<ItemDto> items;

    public ShopDto(Integer id, String location) {
        this.id =  id;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
