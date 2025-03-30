package com.example.server.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Publisher> publishers;

    public City(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public City(String name) {
        this.name = name;
    }

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }
}
