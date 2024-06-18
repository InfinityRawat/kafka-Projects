package com.Spring.JwtAuthentication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.JwtAuthentication.Entity.UserEntity;

public interface JwtRepository extends JpaRepository<UserEntity,String> {

}
