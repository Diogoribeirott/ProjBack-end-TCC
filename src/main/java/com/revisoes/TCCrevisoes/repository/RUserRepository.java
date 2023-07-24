package com.revisoes.TCCrevisoes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.revisoes.TCCrevisoes.dominio.RUser;

@Repository
public interface RUserRepository extends JpaRepository<RUser,Long> {

   UserDetails findByLogin(String login);
}