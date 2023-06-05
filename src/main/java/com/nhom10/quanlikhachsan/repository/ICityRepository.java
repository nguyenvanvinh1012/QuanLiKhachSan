package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
    @Query("select c from City c where c.active = true")
    List<City> findAllByActive();
}
