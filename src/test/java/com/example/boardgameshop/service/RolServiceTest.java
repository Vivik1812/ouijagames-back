package com.example.boardgameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.boardgameshop.model.Rol;
import com.example.boardgameshop.repository.RolRepository;
import com.example.boardgameshop.service.RolService;

@SpringBootTest
public class RolServiceTest {

    @Autowired
    private RolService rolService;

    @MockBean
    private RolRepository rolRepository;

    private Rol createRol() {
        Rol rol = new Rol();
        rol.setId(1);
        rol.setNombre("ADMIN");
        return rol;
    }

    @Test
    public void testFindAllRoles() {
        when(rolRepository.findAll()).thenReturn(List.of(createRol(), createRol()));
        List<Rol> roles = rolService.findAll();
        assertNotNull(roles);
        assertEquals(2, roles.size());
    }

    @Test
    public void testFindRolById() {
        when(rolRepository.findById(1)).thenReturn(java.util.Optional.of(createRol()));
        Rol rol = rolService.findById(1);
        assertNotNull(rol);
        assertEquals(Long.valueOf(1L), rol.getId());
    }

    @Test
    public void testSaveRol() {
        Rol rol = createRol();
        when(rolRepository.save(rol)).thenReturn(rol);
        Rol savedRol = rolService.save(rol);
        assertNotNull(savedRol);
        assertEquals("ADMIN", savedRol.getNombre());
    }
}
