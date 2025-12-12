package com.example.boardgameshop.controller;

import com.example.boardgameshop.model.Rol;
import com.example.boardgameshop.repository.RolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RolControllerTest extends BaseControllerTest {

    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        rolRepository.deleteAll();
    }

    // GET /api/roles - Lista vacía
    
    @Test
    void testGetAllRoles_EmptyList_Returns200() throws Exception {
        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isNoContent());
    }

    // GET /api/roles - Con datos
    
    @Test
    void testGetAllRoles_Returns200WithList() throws Exception {
        createAndSaveRol("ADMIN");
        createAndSaveRol("USER");

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // GET /api/roles/{id} - Búsqueda por ID
    
    @Test
    void testGetRolById_ExistingId_Returns200() throws Exception {
        Rol rol = createAndSaveRol("MODERATOR");

        mockMvc.perform(get("/api/roles/{id}", rol.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("MODERATOR")));
    }

    // POST /api/roles - Crear rol
    
    @Test
    void testCreateRol_ValidData_Returns200() throws Exception {
        Rol rol = new Rol();
        rol.setNombre("EDITOR");

        mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isCreated());
    }

    // Método auxiliar
    
    private Rol createAndSaveRol(String nombre) {
        Rol rol = new Rol();
        rol.setNombre(nombre);
        return rolRepository.save(rol);
    }
}