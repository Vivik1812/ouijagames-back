package com.example.boardgameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.boardgameshop.model.Resenia;
import com.example.boardgameshop.repository.ReseniaRepository;

@SpringBootTest
public class ReseniaServiceTest {
    @Autowired
    private ReseniaService reseniaService;

    @MockBean
    private ReseniaRepository reseniaRepository;

    private Resenia createResenia() {
        Resenia resenia = new Resenia();
        resenia.setId(1L);
        resenia.setContenido("wen juego");
        return resenia;
    }

    @Test
    public void testFindAllResenias() {
        when(reseniaRepository.findAll()).thenReturn(List.of(createResenia(), createResenia()));
        List<Resenia> resenias = reseniaService.getAllResenias();
        assertNotNull(resenias);
        assertEquals(2, resenias.size());
    }

    @Test
    public void testFindReseniaById() {
        when(reseniaRepository.findById(1L)).thenReturn(java.util.Optional.of(createResenia()));
        Resenia resenia = reseniaService.getReseniaById(1L);
        assertNotNull(resenia);
        assertEquals(Long.valueOf(1L), resenia.getId());
    }
    @Test
    public void testSaveResenia() {
        Resenia resenia = createResenia();
        when(reseniaRepository.save(resenia)).thenReturn(resenia);
        Resenia savedResenia = reseniaService.saveResenia(resenia);
        assertNotNull(savedResenia);
        assertEquals("wen juego", savedResenia.getContenido());
    }

    @Test
    public void testDeleteResenia() {
        Resenia resenia = createResenia();
        reseniaService.deleteResenia(resenia.getId());
        when(reseniaRepository.findById(resenia.getId())).thenReturn(java.util.Optional.empty());
        Resenia deletedResenia = reseniaService.getReseniaById(resenia.getId());
        assertEquals(null, deletedResenia);
    }
    
}
