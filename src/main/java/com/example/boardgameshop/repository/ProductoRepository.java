package com.example.boardgameshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import com.example.boardgameshop.model.Producto;


public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
