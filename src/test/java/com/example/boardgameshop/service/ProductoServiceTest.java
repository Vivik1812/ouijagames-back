package com.example.boardgameshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.boardgameshop.model.Producto;
import com.example.boardgameshop.repository.ProductoRepository;

@SpringBootTest
public class ProductoServiceTest {

    @Autowired
    private ProductoService productoService;

    @MockBean
    private ProductoRepository productoRepository;

    private Producto createProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setName("Catan");
        producto.setDescription("A popular board game");
        producto.setPrice(2990);
        return producto;
    }

    @Test
    public void testFindById() {

        when(productoRepository.findById(1L))
            .thenReturn(Optional.of(createProducto()));

        Producto producto = productoService.getProductoById(1L);

        assertNotNull(producto);
        assertEquals("Catan", producto.getName());
        assertEquals(1L, producto.getId());
    }

    @Test
    public void testSaveProducto() {
        Producto producto = createProducto();

        when(productoRepository.save(producto)).thenReturn(producto);

        Producto savedProducto = productoService.saveProducto(producto);

        assertNotNull(savedProducto);
        assertEquals("Catan", savedProducto.getName());
    }

    @Test
    public void testFindAllProductos() {
        when(productoRepository.findAll()).thenReturn(java.util.Arrays.asList(createProducto()));

        var productos = productoService.getAllProductos();

        assertNotNull(productos);
        assertEquals(1, productos.size());
    }

    @Test
    public void testFindByIdNotFound() {
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());

        Producto producto = productoService.getProductoById(2L);

        assertEquals(null, producto);
    }
}
