package com.nhom10.quanlikhachsan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "hotel_type")
public class HotelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "img", length = 64)
    private String image;
    private Boolean active;
    @OneToMany(mappedBy = "hotel_type")
    private List<Hotel> hotels;
    public String getImagesPath(){
        if(image == null || id == null) return null;
        return "/hotelType-images/" + id + "/" + image;
    }
}
