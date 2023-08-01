package com.revisoes.TCCrevisoes.repository;

import com.revisoes.TCCrevisoes.dominio.RUser;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

@Repository
public interface RUserRepository extends JpaRepository<RUser,Long> {

   UserDetails findByLogin(String login);
}