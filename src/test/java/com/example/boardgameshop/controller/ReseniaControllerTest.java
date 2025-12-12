package com.example.boardgameshop.controller;

import com.example.boardgameshop.model.Resenia;
import com.example.boardgameshop.repository.ReseniaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReseniaControllerTest extends BaseControllerTest {

    @Autowired
    private ReseniaRepository reseniaRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        reseniaRepository.deleteAll();
    }

    
    // GET /api/reviews - Lista vacía
    
    @Test
    void testGetAllResenias_EmptyList_Returns200() throws Exception {
        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    
    // GET /api/reviews - Con datos
    
    @Test
    void testGetAllResenias_Returns200WithList() throws Exception {
        createAndSaveResenia("Excelente juego, muy entretenido");
        createAndSaveResenia("Buena calidad pero algo caro");

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    
    // GET /api/reviews/{id} - Búsqueda por ID
    
    @Test
    void testGetReseniaById_ExistingId_Returns200() throws Exception {
        Resenia resenia = createAndSaveResenia("Increíble experiencia de juego");

        mockMvc.perform(get("/api/reviews/{id}", resenia.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenido", is("Increíble experiencia de juego")));
    }

    
    // POST /api/reviews - Crear reseña
    
    @Test
    void testCreateResenia_ValidData_Returns200() throws Exception {
        Resenia resenia = new Resenia();
        resenia.setContenido("Muy buena compra, lo recomiendo");

        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resenia)))
                .andExpect(status().isOk());
    }

    
    // PUT /api/reviews/{id} - Actualizar reseña
    
    @Test
    void testUpdateResenia_ExistingId_Returns200() throws Exception {
        Resenia resenia = createAndSaveResenia("Comentario inicial");
        resenia.setContenido("Comentario actualizado");

        mockMvc.perform(put("/api/reviews/{id}", resenia.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resenia)))
                .andExpect(status().isOk());
    }

    
    // DELETE /api/reviews/{id} - Eliminar reseña
    
    @Test
    void testDeleteResenia_ExistingId_Returns200() throws Exception {
        Resenia resenia = createAndSaveResenia("Esta reseña será eliminada");

        mockMvc.perform(delete("/api/reviews/{id}", resenia.getId()))
                .andExpect(status().isOk());
    }

    
    // Método auxiliar
    
    private Resenia createAndSaveResenia(String contenido) {
        Resenia resenia = new Resenia();
        resenia.setContenido(contenido);
        return reseniaRepository.save(resenia);
    }
}