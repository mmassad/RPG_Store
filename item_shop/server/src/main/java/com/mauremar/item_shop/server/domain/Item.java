package com.mauremar.item_shop.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



@Entity
@Data
@Getter
@Setter
@Table(name = "item")
@EqualsAndHashCode
@JsonIgnoreProperties("shop")
public class Item implements Serializable {
    @Id
    @SequenceGenerator(name = "item_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "price")
    public Integer price;

    @Column(name = "attack")
    public Integer attack;

    @Column(name = "job")
    public String job;

    @ManyToOne
    @JsonBackReference
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public Adventurer adventurer;

    @ManyToMany(mappedBy = "items")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @JsonIgnore
    public List<Shop> shops = new ArrayList<>();

    public Item() {}

    public Item(String name, Integer price, Integer attack, String job) {
        this.name = name;
        this.price = price;
        this.attack = attack;
        this.job = job;
    }

    public Item(Integer id, String name, Integer price, Integer attack, String job) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.attack = attack;
        this.job = job;
    }
}

