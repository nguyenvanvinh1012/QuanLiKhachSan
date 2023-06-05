package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room, Long> {
}
