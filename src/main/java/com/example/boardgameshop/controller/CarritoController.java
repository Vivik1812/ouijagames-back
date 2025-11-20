package com.example.boardgameshop.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;

import com.example.boardgameshop.model.Carrito;
import com.example.boardgameshop.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/carts")
@Tag(name = "Cart Management System")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @GetMapping
    @Operation(summary = "View a list of available carts")
    public ResponseEntity<List<Carrito>> getAllCarritos() {
        List<Carrito> carritos = carritoService.findAll();
        if (carritos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carritos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a cart by Id")
    public ResponseEntity<Carrito> getCarritoById(@PathVariable Long id) {
        Carrito carrito = carritoService.findById(id);
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrito);
    }

    @PostMapping
    @Operation(summary = "Add a new cart")
    public ResponseEntity<Carrito> createCarrito(@RequestBody Carrito carrito) {
        Carrito createdCarrito = carritoService.save(carrito);
        return ResponseEntity.status(201).body(createdCarrito);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing cart")
    public ResponseEntity<Carrito> updateCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        Carrito existingCarrito = carritoService.findById(id);
        if (existingCarrito == null) {
            return ResponseEntity.notFound().build();
        }
        existingCarrito.setProductos(carrito.getProductos());
        existingCarrito.setTotal(carrito.getTotal());
        Carrito updatedCarrito = carritoService.save(existingCarrito);
        return ResponseEntity.ok(updatedCarrito);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update an existing cart")
    public ResponseEntity<Carrito> partiallyUpdateCarrito(@PathVariable Long id, @RequestBody Carrito carrito) {
        Carrito existingCarrito = carritoService.findById(id);
        if (existingCarrito == null) {
            return ResponseEntity.notFound().build();
        }
        if (carrito.getProductos() != null) {
            existingCarrito.setProductos(carrito.getProductos());
        }
        if (carrito.getTotal() != null) {
            existingCarrito.setTotal(carrito.getTotal());
        }
        Carrito updatedCarrito = carritoService.save(existingCarrito);
        return ResponseEntity.ok(updatedCarrito);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cart")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Long id) {
        Carrito carrito = carritoService.findById(id);
        if (carrito == null) {
            return ResponseEntity.notFound().build();
        }
        carritoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
