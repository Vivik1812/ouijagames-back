package com.example.boardgameshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boardgameshop.model.Categoria;
import com.example.boardgameshop.model.Producto;
import com.example.boardgameshop.model.ProductRequest;
import com.example.boardgameshop.repository.ProductoRepository;
import com.example.boardgameshop.repository.CategoriaRepository;
import com.example.boardgameshop.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management System")
public class ProductoController {

    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "View a list of available products")
    public List<Producto> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by Id")
    public Producto getProductoById(@PathVariable Long id) {
        return productoService.getProductoById(id);
    }

    @PostMapping
    @Operation(summary = "Add a new product")
    public ResponseEntity<Producto> createProducto(@RequestBody ProductRequest request) {
        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setName(request.getName());
        producto.setDescription(request.getDescription());
        producto.setPrice(request.getPrice().intValue());
        producto.setStock(request.getStock());
        producto.setImg(request.getImg());
        producto.setCategoria(categoria);

        Producto saved = productoRepository.save(producto);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product")
    public Producto updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto existingProducto = productoService.getProductoById(id);
        if (existingProducto != null) {
            existingProducto.setName(producto.getName());
            existingProducto.setDescription(producto.getDescription());
            existingProducto.setPrice(producto.getPrice());
            existingProducto.setStock(producto.getStock());
            return productoService.saveProducto(existingProducto);
        }
        return null;
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an existing product")
    public Producto partiallyUpdateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto existingProducto = productoService.getProductoById(id);
        if (existingProducto != null) {
            if (producto.getName() != null) {
                existingProducto.setName(producto.getName());
            }
            if (producto.getDescription() != null) {
                existingProducto.setDescription(producto.getDescription());
            }
            if (producto.getPrice() != null) {
                existingProducto.setPrice(producto.getPrice());
            }
            if (producto.getStock() != null) {
                existingProducto.setStock(producto.getStock());
            }
            return productoService.saveProducto(existingProducto);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product")
    public void deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
    }
}
