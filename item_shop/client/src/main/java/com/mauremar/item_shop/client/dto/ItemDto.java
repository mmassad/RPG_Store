package com.mauremar.item_shop.client.dto;

public class ItemDto {
    private Integer id;
    private String name;
    private String job;
    private Integer price;
    private Integer attack;



    public ItemDto(Integer id, String name, String job, Integer price, Integer attack) {
        this.id =  id;
        this.name = name;
        this.job = job;
        this.price = price;
        this.attack = attack;

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }


}
