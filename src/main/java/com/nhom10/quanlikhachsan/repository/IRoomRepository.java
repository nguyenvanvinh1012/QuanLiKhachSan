package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends PagingAndSortingRepository<Room, Long>,JpaRepository<Room, Long> {
    @Query("select r from Room r where r.hotel.id = :id")
    List<Room> findAllByRoomIdHotel(@Param("id") Long id);
    @Query("SELECT COUNT(r) FROM Room r WHERE r.hotel.id = :id")
    Long countRoomsByHotelId(@Param("id")Long id);
    @Query("""
        SELECT r from Room r
        WHERE r.name LIKE %?1%
        """
    )
    Page<Room> searchRoom(String keyword, Pageable pageable);
}
