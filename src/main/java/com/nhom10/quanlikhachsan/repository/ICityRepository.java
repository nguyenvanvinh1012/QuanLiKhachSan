package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.City;
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
public interface ICityRepository extends PagingAndSortingRepository<City, Long>,JpaRepository<City, Long> {
    @Query("select c from City c where c.active = true")
    List<City> findAllByActive();

    @Query("select c from City c where c.id = :id")
    City findCitylByIdHotel(@Param("id") Long id);

    @Query("select c from City c where c.name like %?1%")
    City searchCity(String keyword);

    @Query("""
        SELECT c from City c
        WHERE c.name LIKE %?1%
        """)
    Page<City> searchCity2(String keyword, Pageable pageable);
}
