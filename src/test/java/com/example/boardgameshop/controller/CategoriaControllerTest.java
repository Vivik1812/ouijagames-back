package com.example.boardgameshop.controller;

import com.example.boardgameshop.model.Categoria;
import com.example.boardgameshop.repository.CategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoriaControllerTest extends BaseControllerTest {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        categoriaRepository.deleteAll();
    }

    
    // GET /api/categories - Lista vacía
    
    @Test
    void testGetAllCategorias_EmptyList_Returns204() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isNoContent());
    }

    
    // GET /api/categories - Con datos
    
    @Test
    void testGetAllCategorias_Returns200WithList() throws Exception {
        createAndSaveCategoria("Estrategia", "Juegos de estrategia");
        createAndSaveCategoria("Familiar", "Juegos familiares");

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    
    // GET /api/categories/{id} - Búsqueda por ID
    
    @Test
    void testGetCategoriaById_ExistingId_Returns200() throws Exception {
        Categoria categoria = createAndSaveCategoria("Party", "Juegos de fiesta");

        mockMvc.perform(get("/api/categories/{id}", categoria.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Party")));
    }

    
    // POST /api/categories - Crear categoría
    
    @Test
    void testCreateCategoria_ValidData_Returns201() throws Exception {
        Categoria categoria = new Categoria(null, "Cooperativo", "Juegos cooperativos", null);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is("Cooperativo")));
    }

    
    // Método auxiliar
    
    private Categoria createAndSaveCategoria(String nombre, String descripcion) {
        Categoria categoria = new Categoria(null, nombre, descripcion, null);
        return categoriaRepository.save(categoria);
    }
}