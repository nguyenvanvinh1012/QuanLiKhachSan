package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.Hotel;
import com.nhom10.quanlikhachsan.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
    @Query("select r from Room r where r.hotel.id = :id")
    List<Room> findAllByRoomIdHotel(@Param("id") Long id);
    @Query("SELECT COUNT(r) FROM Room r WHERE r.hotel.id = :id")
    Long countRoomsByHotelId(@Param("id")Long id);
}
