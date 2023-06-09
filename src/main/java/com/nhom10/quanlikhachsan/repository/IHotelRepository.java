package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHotelRepository extends JpaRepository<Hotel, Long> {
    @Query("select h from Hotel h where h.active = true and h.city.id = :id")
    List<Hotel> findAllByActiveIdCity(@Param("id") Long id);
    @Query("SELECT COUNT(h) FROM Hotel h WHERE h.active and h.city.id = :cityId")
    Long countHotelsByCityId(@Param("cityId") Long cityId);
}
