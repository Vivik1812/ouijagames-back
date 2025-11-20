package com.example.boardgameshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.boardgameshop.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

}