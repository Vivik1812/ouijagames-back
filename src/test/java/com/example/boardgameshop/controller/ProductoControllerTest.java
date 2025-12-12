package com.example.boardgameshop.controller;

import com.example.boardgameshop.model.Categoria;
import com.example.boardgameshop.model.Producto;
import com.example.boardgameshop.repository.CategoriaRepository;
import com.example.boardgameshop.repository.ProductoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductoControllerTest extends BaseControllerTest{

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Categoria categoria;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();
        categoriaRepository.deleteAll();

        categoria = new Categoria();
        categoria.setNombre("Estrategia");
        categoria.setDescripcion("Juegos de estrategia");
        categoria = categoriaRepository.save(categoria);
    }

    //GET /api/products - lista vacia

    @Test
    void testGetAllProductos_EmptyList_returns200() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    //GET /api/products - con datos

    @Test
    void testGetAllProductos_Returns200WithList() throws Exception {
        createAndSaveProducto("Catan", "Juego de mesa popular", 30, 10, "catan.jpg", categoria);
        createAndSaveProducto("Carcassonne", "Juego de colocación de losetas", 25, 15, "carcassonne.jpg", categoria);

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Catan")))
                .andExpect(jsonPath("$[1].name", is("Carcassonne")));
    }

    // POST /api/products - crear producto

    @Test
    void testCreateProducto_ValidData_returns201() throws Exception {
        String requestBody = String.format("""
                {
                    "name": "Dixit",
                    "description": "Juego de cartas creativo",
                    "price": 20,
                    "stock": 5,
                    "img": "dixit.jpg",
                    "categoriaId": %d
                    }
                }
                """, categoria.getId());
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is("Dixit")))
                        .andExpect(jsonPath("$.price", is(20)));
    }

    // PUT /api/products/{id} - actualizar producto

    @Test
    void testUpdateProducto_ValidData_returns200() throws Exception {
        Producto producto = createAndSaveProducto("Azul", "Juego de azulejos", 35, 8, "azul.jpg", categoria);

        String requestBody = String.format("""
                {
                    "name": "Azul - Edición Especial",
                    "description": "Juego de azulejos con nuevas reglas",
                    "price": 40,
                    "stock": 10,
                    "img": "azul_especial.jpg",
                    "categoria": {
                        "id": %d
                    }
                }
                """, categoria.getId());

        mockMvc.perform(put("/api/products/{id}", producto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", is("Azul - Edición Especial")))
                        .andExpect(jsonPath("$.price", is(40)));
    }

    //DELETE /api/products/{id} - eliminar producto

    @Test
    void testDeleteProducto_ExistingId_returns204() throws Exception {
        Producto producto = createAndSaveProducto("Pandemic", "Juego cooperativo", 28, 12, "pandemic.jpg", categoria);

        mockMvc.perform(delete("/api/products/{id}", producto.getId()))
                .andExpect(status().isOk());
    }

    //Metodo auxiliar

    private Producto createAndSaveProducto(String name, String description, Integer price, Integer stock, String img, Categoria categoria) {
        Producto producto = new Producto();
        producto.setName(name);
        producto.setDescription(description);
        producto.setPrice(price);
        producto.setStock(stock);
        producto.setImg(img);
        producto.setCategoria(categoria);
        return productoRepository.save(producto);
    }
    
}

