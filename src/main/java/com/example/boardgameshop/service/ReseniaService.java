package com.example.boardgameshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boardgameshop.model.Resenia;
import com.example.boardgameshop.repository.ReseniaRepository;

import java.util.List;

@Service
public class ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    public List<Resenia> getAllResenias() {
        return reseniaRepository.findAll();
    }

    public Resenia getReseniaById(Long id) {
        return reseniaRepository.findById(id).orElse(null);
    }

    public Resenia saveResenia(Resenia resenia) {
        return reseniaRepository.save(resenia);
    }

    public void deleteResenia(Long id) {
        reseniaRepository.deleteById(id);
    }
}