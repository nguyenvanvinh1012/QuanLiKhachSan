package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHotelRepository extends PagingAndSortingRepository<Hotel, Long>,JpaRepository<Hotel, Long> {
    @Query("select h from Hotel h where h.active = true and h.city.id = :id")
    List<Hotel> findAllByActiveIdCity(@Param("id") Long id);
    @Query("select h from Hotel h where h.active = true and h.city.id = :id")
    Page<Hotel> findAllByActiveIdCity2(@Param("id") Long id, Pageable pageable);
    @Query("SELECT COUNT(h) FROM Hotel h WHERE h.active and h.city.id = :cityId")
    Long countHotelsByCityId(@Param("cityId") Long cityId);
    @Query("select h from Hotel h where h.id = :id")
    Hotel findHotelByIdRoom(@Param("id") Long id);
    @Query("""
        SELECT r from Hotel r
        WHERE r.name LIKE %?1%
        """
    )
    Page<Hotel> searchHotel(String keyword, Pageable pageable);
}
