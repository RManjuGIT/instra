package com.instra.superrduperr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instra.superrduperr.dao.bean.Users;

public interface UserRepository extends JpaRepository<Users, Integer> {

}
