package com.example.boardgameshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.boardgameshop.model.Carrito;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

}
