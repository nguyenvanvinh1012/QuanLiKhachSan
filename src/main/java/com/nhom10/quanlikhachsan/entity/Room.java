package com.nhom10.quanlikhachsan.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Room name must not be empty")
    private String name;


    @Column(name = "img", length = 64)
    private String image;
    @Min(value = 1, message = "Area should be greater than or equal to 1")
    private double area; //dien tich

    @Min(value = 1, message = "Rent should be greater than or equal to 1")
    private double rent;

    @NotBlank(message = "Convenient must not be empty")
    private String convenient;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(name = "description" ,length = 1000)
    private String description;
    private int bed_type;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<BookRoom> bookRooms = new ArrayList<>();
    public String getImagesPath(){
        if(image == null || id == null) return null;
        return "/room-images/" + id + "/" + image;
    }
}
