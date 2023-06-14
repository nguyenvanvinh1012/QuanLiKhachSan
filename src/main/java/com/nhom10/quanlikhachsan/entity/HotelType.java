package com.nhom10.quanlikhachsan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "hotel_type")
public class HotelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Hotel type must not be empty")
    private String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(name = "description" ,length = 1000)
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
