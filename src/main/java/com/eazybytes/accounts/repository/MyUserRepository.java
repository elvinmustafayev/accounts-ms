package com.eazybytes.accounts.repository;

import com.eazybytes.accounts.entity.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MyUserRepository  extends JpaRepository<MyUser, Long> {
}
