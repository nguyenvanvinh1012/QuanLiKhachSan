package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
}
