package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.BookRoom;
import com.nhom10.quanlikhachsan.entity.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRoomRepository extends JpaRepository<BookRoom, Long> {
    @Query("""
        SELECT b from BookRoom b
        WHERE b.note LIKE %?1%
        """
    )
    Page<BookRoom> searchBookRoom(String keyword, Pageable pageable);
    @Query("select b from BookRoom b where b.user.id = ?1")
    List<BookRoom> findAllBookedRoomOfUser(Long userId);
}
