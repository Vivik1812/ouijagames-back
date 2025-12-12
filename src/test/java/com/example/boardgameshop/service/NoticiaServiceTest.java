package com.example.boardgameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.boardgameshop.model.Noticia;
import com.example.boardgameshop.repository.NoticiaRepository;

@SpringBootTest
public class NoticiaServiceTest {
    
    @Autowired
    private NoticiaService noticiaService;

    @MockBean
    private NoticiaRepository noticiaRepository;

    public Noticia createNoticia() {
        Noticia noticia = new Noticia();
        noticia.setId(1L);
        noticia.setTitulo("Nueva tienda abierta");
        noticia.setTexto("Hemos abierto una nueva tienda en la ciudad.");
        noticia.setImg("tienda.jpg");
        noticia.setUrlRef(null);
        return noticia;
    }

    @Test
    public void testFindAllNoticias() {
        when(noticiaRepository.findAll()).thenReturn(List.of(createNoticia(), createNoticia()));
        List<Noticia> noticias = noticiaService.findAll();
        assertNotNull(noticias);
        assertEquals(2, noticias.size());
    }

    @Test
    public void testFindNoticiaById() {
        when(noticiaRepository.findById(1L)).thenReturn(java.util.Optional.of(createNoticia()));
        Noticia noticia = noticiaService.findById(1L);
        assertNotNull(noticia);
        assertEquals(Long.valueOf(1L), noticia.getId());
    }

    @Test
    public void testSaveNoticia() {
        Noticia noticia = createNoticia();
        when(noticiaRepository.save(noticia)).thenReturn(noticia);
        Noticia savedNoticia = noticiaService.save(noticia);
        assertNotNull(savedNoticia);
        assertEquals("Nueva tienda abierta", savedNoticia.getTitulo());
    }
}
