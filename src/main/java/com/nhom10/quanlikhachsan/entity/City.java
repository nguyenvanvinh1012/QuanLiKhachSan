package com.nhom10.quanlikhachsan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "image", length = 64)
    private String image;
    @Column(name = "active", columnDefinition = "BIT(1)")
    private Boolean active;
    @OneToMany(mappedBy = "city")
    private List<Hotel> hotels;
    public String getImagesPath(){
        if(image == null || id == null) return null;
        return "/city-images/" + id + "/" + image;
    }
}
