package com.example.boardgameshop.controller;

import com.example.boardgameshop.model.Noticia;
import com.example.boardgameshop.repository.NoticiaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NoticiaControllerTest extends BaseControllerTest {

    @Autowired
    private NoticiaRepository noticiaRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        noticiaRepository.deleteAll();
    }

    // GET /api/news - Lista vacía
    
    @Test
    void testGetAllNoticias_EmptyList_Returns200() throws Exception {
        mockMvc.perform(get("/api/news"))
                .andExpect(status().isNoContent());
    }
    
    // GET /api/news - Con datos
    
    @Test
    void testGetAllNoticias_Returns200WithList() throws Exception {
        createAndSaveNoticia("Nueva expansión", "Detalles...");
        createAndSaveNoticia("Torneo regional", "Información...");

        mockMvc.perform(get("/api/news"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // GET /api/news/{id} - Búsqueda por ID
    
    @Test
    void testGetNoticiaById_ExistingId_Returns200() throws Exception {
        Noticia noticia = createAndSaveNoticia("Lanzamiento", "Nuevo juego");

        mockMvc.perform(get("/api/news/{id}", noticia.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Lanzamiento")));
    }

    // POST /api/news - Crear noticia
    
    @Test
    void testCreateNoticia_ValidData_Returns200() throws Exception {
        Noticia noticia = new Noticia();
        noticia.setTitulo("Oferta especial");
        noticia.setTexto("Descuentos en toda la tienda");
        noticia.setImg("oferta.jpg");
        noticia.setUrlRef("https://example.com/oferta");

        mockMvc.perform(post("/api/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noticia)))
                .andExpect(status().isCreated());
    }

    // Método auxiliar
    
    private Noticia createAndSaveNoticia(String titulo, String texto) {
        Noticia noticia = new Noticia();
        noticia.setTitulo(titulo);
        noticia.setTexto(texto);
        noticia.setImg("noticia.jpg");
        noticia.setUrlRef("https://example.com");
        return noticiaRepository.save(noticia);
    }
}