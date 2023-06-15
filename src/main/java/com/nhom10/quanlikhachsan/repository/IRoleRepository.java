package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.City;
import com.nhom10.quanlikhachsan.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r.id from Role r where r.name = ?1")
    Long getRoleIdByName(String roleName);
    @Query("""
        SELECT r from Role r
        WHERE r.name LIKE %?1%
        """
    )
    Page<Role> searchRole(String keyword, Pageable pageable);
}
