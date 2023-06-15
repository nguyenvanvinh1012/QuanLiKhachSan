package com.nhom10.quanlikhachsan.entity;

import com.nhom10.quanlikhachsan.validatiors.annotations.ValidUsername;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.*;

@Data
@Entity
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, unique = true)
    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 50, message = "Username must be between 1 and 50 characters")
    @ValidUsername
    private String username;
    @Column(length = 250)
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Full name is required")
    private String full_name;
    @Column(name = "phone", length = 20, unique = true)
    @Length(min = 10, max = 10, message = "Phone must be 10 characters")
    @Pattern(regexp = "^[0-9]*$", message = "Phone must be number")
    private String phone;
    @Column(name = "email", length = 50, unique = true)
    @NotBlank(message = "Email is required")
    @Size(min = 1, max = 50, message = "Email must be between 1 and 50 characters")
    @Email
    private String email;
    private Boolean active;
    @Column(name = "provider", length = 50)
    private String provider;
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user")
    private List<BookRoom> bookRooms = new ArrayList<>();
}
