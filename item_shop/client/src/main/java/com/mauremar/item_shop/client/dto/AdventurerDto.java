package com.mauremar.item_shop.client.dto;

import java.util.List;

public class AdventurerDto {
    private Integer id;
    private String name;
    private String job;
    private Integer gold;

    public List<ItemDto> items;

    public AdventurerDto(Integer id, String name, String job, Integer gold) {
        this.id =  id;
        this.name = name;
        this.job = job;
        this.gold = gold;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
