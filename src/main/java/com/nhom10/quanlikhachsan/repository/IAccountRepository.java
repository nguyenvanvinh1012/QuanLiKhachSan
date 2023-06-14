package com.nhom10.quanlikhachsan.repository;

import com.nhom10.quanlikhachsan.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<User, Long> {

}