package com.mauremar.item_shop.server.domain;

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
@Table(name = "adventurer")
public class Adventurer implements Serializable {
    @Id
    @SequenceGenerator(name = "adventurer_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adventurer_id_seq")
    public Integer id;

    @Column(name = "name")
    public String name;

    @Column(name = "gold")
    public Integer gold;

    @Column(name = "job")
    public String job;

    @OneToMany(mappedBy = "adventurer")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public List<Item> items = new ArrayList<>();

    public Adventurer() {}

    public Adventurer(String name, Integer gold, String job) {
        this.name = name;
        this.gold = gold;
        this.job = job;
    }

    public Adventurer(Integer id, String name, Integer gold, String job) {
        this.id = id;
        this.name = name;
        this.gold = gold;
        this.job = job;
    }
}
