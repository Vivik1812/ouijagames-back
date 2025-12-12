package com.example.boardgameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.boardgameshop.model.Usuario;
import com.example.boardgameshop.repository.UsuarioRepository;
import com.example.boardgameshop.service.UsuarioService;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");
        usuario.setEmail("testuser@example.com");
        return usuario;
    }

    @Test
    public void testFindAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario(), createUsuario()));
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(2, usuarios.size());
    }

    @Test
    public void testFindUsuarioById() {
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioService.findById(1L);
        assertNotNull(usuario);
        assertEquals(Long.valueOf(1L), usuario.getId());
    }

    @Test
    public void testSaveUsuario() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        Usuario savedUsuario = usuarioService.save(usuario);
        assertNotNull(savedUsuario);
        assertEquals("testuser", savedUsuario.getUsername());
    }
    
}