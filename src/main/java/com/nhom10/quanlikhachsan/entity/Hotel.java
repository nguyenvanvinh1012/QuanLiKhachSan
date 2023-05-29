package com.nhom10.quanlikhachsan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "description")
    private String description;
    @Column(name = "center")
    private double center; // cach trung tam
    @Column(name = "bordering_sea")
    private Boolean bordering_sea; //giap bien
    @Column(name = "vote")
    private int vote;
    @Column(name = "meal")
    private int meal;
    @Column(name = "img1",length = 64)
    private String img1;
    @Column(name = "img2", length = 64)
    private String img2;
    @Column(name = "img3", length = 64)
    private String img3;
    @Column(name = "img4", length = 64)
    private String img4;
    @Column(name = "active")
    private Boolean active;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "hotelType_id")
    private HotelType hotel_type;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();
}
