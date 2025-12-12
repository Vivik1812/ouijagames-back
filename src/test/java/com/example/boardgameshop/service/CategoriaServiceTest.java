package com.example.boardgameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.boardgameshop.model.Categoria;
import com.example.boardgameshop.repository.CategoriaRepository;

@SpringBootTest
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaRepository categoriaRepository;

    private Categoria createCategoria() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Estrategia");
        return categoria;
    }

    @Test
    public void testFindAllCategorias() {
        when(categoriaRepository.findAll()).thenReturn(List.of(createCategoria(), createCategoria()));
        List<Categoria> categorias = categoriaService.findAll();
        assertNotNull(categorias);
        assertEquals(2, categorias.size());
    }

    @Test
    public void testGetCategoriaById() {
        when(categoriaRepository.findById(1L)).thenReturn(java.util.Optional.of(createCategoria()));
        Categoria categoria = categoriaService.findById(1L);
        assertNotNull(categoria);
        assertEquals(Long.valueOf(1L), categoria.getId());
    }

    @Test
    public void testSaveCategoria() {
        Categoria categoria = createCategoria();
        when(categoriaRepository.save(categoria)).thenReturn(categoria);
        Categoria savedCategoria = categoriaService.save(categoria);
        assertNotNull(savedCategoria);
        assertEquals("Estrategia", savedCategoria.getNombre());
    }
}
