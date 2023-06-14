package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.HotelType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHotelTypeRepository extends PagingAndSortingRepository<HotelType, Long>,JpaRepository<HotelType, Long> {
    @Query("""
        SELECT r from HotelType r
        WHERE r.name LIKE %?1%
        """
    )
    Page<HotelType> searchHotelType(String keyword, Pageable pageable);
}
