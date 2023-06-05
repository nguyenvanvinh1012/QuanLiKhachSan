package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.HotelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelTypeRepository extends JpaRepository<HotelType, Long> {
}
