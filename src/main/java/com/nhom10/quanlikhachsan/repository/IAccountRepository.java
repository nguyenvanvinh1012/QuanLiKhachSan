package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.BookRoom;
import com.nhom10.quanlikhachsan.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<User, Long> {
    @Query("""
            SELECT r from User r
            WHERE r.id LIKE %?1%
            OR r.username LIKE %?1%
            OR r.full_name LIKE %?1%
            OR r.phone LIKE %?1%
            OR r.email LIKE %?1%
            """
    )
    Page<User> searchAccount(String keyword, Pageable pageable);
}