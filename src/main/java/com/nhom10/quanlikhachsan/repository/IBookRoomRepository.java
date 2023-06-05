package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.BookRoom;
import com.nhom10.quanlikhachsan.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRoomRepository extends JpaRepository<BookRoom, Long> {
}
