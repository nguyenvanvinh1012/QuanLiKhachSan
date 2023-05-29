package com.nhom10.quanlikhachsan.entity;


import jakarta.persistence.*;
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
    private String name;
    private double area; //dien tich
    private double rent;
    private String convenient;
    private String description;
    private int bed_type;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @OneToMany(mappedBy = "room")
    private List<BookRoom> bookRooms = new ArrayList<>();
}
