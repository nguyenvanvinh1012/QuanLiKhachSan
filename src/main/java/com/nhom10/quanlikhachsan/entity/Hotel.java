package com.nhom10.quanlikhachsan.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Hotel name must not be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Address is required")
    @Column(name = "address")
    private String address;


    @Column(name = "phone",length = 10,unique = true)
    @Length(min=10,max = 10,message = "Phone number must be 10 characters")
    @Pattern(regexp = "^[0-9]*$",message = "Phone must be number !")
    private String phone;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Column(name = "description" ,length = 1000)
    private String description;

    @Min(value = 1, message = "Center should be greater than or equal to 1")
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
    public String getImagesPath1(){
        if(img1 == null || id == null) return null;
        return "/hotel-images/" + id + "/" + img1;
    }
    public String getImagesPath2(){
        if(img2 == null || id == null) return null;
        return "/hotel-images/" + id + "/" + img2;
    }
    public String getImagesPath3(){
        if(img3 == null || id == null) return null;
        return "/hotel-images/" + id + "/" + img3;
    }
    public String getImagesPath4(){
        if(img4 == null || id == null) return null;
        return "/hotel-images/" + id + "/" + img4;
    }
}
