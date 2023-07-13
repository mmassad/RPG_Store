package com.mauremar.item_shop.server.domain;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "shop")
@EqualsAndHashCode
public class Shop {
    @Id
    @SequenceGenerator(name = "shop_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shop_id_seq")
    private Integer id;

    @ManyToMany
    @JoinTable(name = "shop_items",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

    @Column(name = "location")
    private String location;

    public Shop() {}

    public Shop(String location) {
        this.location = location;
    }

    public Shop(Integer id, String location) {
        this.setId(id);
        this.setLocation(location);
    }
}
