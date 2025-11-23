package com.example.boardgameshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.boardgameshop.model.Noticia;
import com.example.boardgameshop.repository.NoticiaRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    public List<Noticia> findAll() {
        return noticiaRepository.findAll();
    }

    public Noticia findById(Long id) {
        return noticiaRepository.findById(id).orElse(null);
    }

    public Noticia save(Noticia noticia) {
        return noticiaRepository.save(noticia);
    }

    public void delete(Long id) {
        noticiaRepository.deleteById(id);
    }
}
