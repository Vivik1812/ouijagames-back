package com.example.boardgameshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.boardgameshop.model.Noticia;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

}
