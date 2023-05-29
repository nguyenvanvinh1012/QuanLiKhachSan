package com.nhom10.quanlikhachsan.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "book_room")
public class BookRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date check_in;
    private Date check_out;
    private String service;
    private String note;
    private double money;
    private Boolean isPaid;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
