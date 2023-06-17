package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.entity.HotelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IHotelTypeRepository extends PagingAndSortingRepository<HotelType, Long>,JpaRepository<HotelType, Long> {
    @Query("""
        SELECT r from HotelType r
        WHERE r.name LIKE %?1%
        """
    )
    Page<HotelType> searchHotelType(String keyword, Pageable pageable);
    @Query("select h from HotelType h where h.active = true")
    List<HotelType> findAllHotelTypeActive();
}
