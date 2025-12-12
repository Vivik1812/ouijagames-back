package com.example.boardgameshop.controller;

import com.example.boardgameshop.model.Usuario;
import com.example.boardgameshop.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UsuarioControllerTest extends BaseControllerTest {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();
    }

    
    // GET /api/users - Lista vacía
    
    @Test
    void testGetAllUsuarios_EmptyList_Returns204() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isNoContent());
    }

    
    // GET /api/users - Con datos
    
    @Test
    void testGetAllUsuarios_Returns200WithList() throws Exception {
        createAndSaveUsuario("juan", "juan@test.com");
        createAndSaveUsuario("maria", "maria@test.com");

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].username", is("juan")))
                .andExpect(jsonPath("$[1].username", is("maria")));
    }

    
    // GET /api/users/{id} - Usuario existente
    
    @Test
    void testGetUsuarioById_ExistingId_Returns200() throws Exception {
        Usuario usuario = createAndSaveUsuario("pedro", "pedro@test.com");

        mockMvc.perform(get("/api/users/{id}", usuario.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("pedro")))
                .andExpect(jsonPath("$.email", is("pedro@test.com")));
    }

    
    // GET /api/users/{id} - Usuario inexistente
    
    @Test
    void testGetUsuarioById_NonExistingId_Returns404() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    // DELETE /api/users/{id} - Eliminar usuario
    
    @Test
    void testDeleteUsuario_ExistingId_Returns204() throws Exception {
        Usuario usuario = createAndSaveUsuario("carlos", "carlos@test.com");

        mockMvc.perform(delete("/api/users/{id}", usuario.getId()))
                .andExpect(status().isNoContent());
    }

    
    // Métodos auxiliares
    
    private Usuario createUsuario(String username, String email) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPassword("password123");
        return usuario;
    }

    private Usuario createAndSaveUsuario(String username, String email) {
        return usuarioRepository.save(createUsuario(username, email));
    }
}