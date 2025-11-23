package com.example.boardgameshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.boardgameshop.model.Noticia;
import com.example.boardgameshop.service.NoticiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/news")
@Tag(name = "Cart Management System")
public class NoticiaController {

    @Autowired
    private NoticiaService noticiaService;

    @GetMapping
    @Operation(summary = "View a list of available news")
    public ResponseEntity<List<Noticia>> getAllNoticias() {
        List<Noticia> noticias = noticiaService.findAll();
        if (noticias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(noticias);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a noticia by Id")
    public ResponseEntity<Noticia> getNoticiaById(@PathVariable Long id) {
        Noticia noticia = noticiaService.findById(id);
        if (noticia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(noticia);
    }

    @PostMapping
    @Operation(summary = "Add a new new")
    public ResponseEntity<Noticia> createCarrito(@RequestBody Noticia noticia) {
        Noticia createdNoticia = noticiaService.save(noticia);
        return ResponseEntity.status(201).body(createdNoticia);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing new")
    public ResponseEntity<Noticia> updateCarrito(@PathVariable Long id, @RequestBody Noticia noticia) {
        Noticia existingNoticia = noticiaService.findById(id);
        if (existingNoticia == null) {
            return ResponseEntity.notFound().build();
        }
        existingNoticia.setTitulo(noticia.getTitulo());
        existingNoticia.setTexto(noticia.getTexto());
        existingNoticia.setImg(noticia.getImg());
        existingNoticia.setUrlRef(noticia.getUrlRef());
        Noticia updatedNoticia = noticiaService.save(existingNoticia);
        return ResponseEntity.ok(updatedNoticia);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an existing new")
    public ResponseEntity<Noticia> partiallyUpdateCarrito(@PathVariable Long id, @RequestBody Noticia noticia) {
        Noticia existingNoticia = noticiaService.findById(id);
        if (existingNoticia == null) {
            return ResponseEntity.notFound().build();
        }
        if (noticia.getTitulo() != null) {
            existingNoticia.setTitulo(noticia.getTitulo());
        }
        if (noticia.getTexto() != null) {
            existingNoticia.setTexto(noticia.getTexto());
        }
        if (noticia.getImg() != null) {
            existingNoticia.setImg(noticia.getImg());
        }
        if (noticia.getUrlRef() != null) {
            existingNoticia.setUrlRef(noticia.getUrlRef());
        }
        Noticia updatedNoticia = noticiaService.save(existingNoticia);
        return ResponseEntity.ok(updatedNoticia);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a new")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        Noticia noticia = noticiaService.findById(id);
        if (noticia == null) {
            return ResponseEntity.notFound().build();
        }
        noticiaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
