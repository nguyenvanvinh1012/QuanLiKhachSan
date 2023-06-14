package com.nhom10.quanlikhachsan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "name is required")
    @Size(min = 1, max = 15)
    private String name;
    @Column(name = "description")
    @NotBlank(message = "description is required")
    @Size(min = 1, max = 50)
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
