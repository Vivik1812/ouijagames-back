package com.example.boardgameshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 5000, nullable = false)
    private String description;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String img;

    // @ManyToOne
    // private Carrito carrito;

    // @OneToMany
    // @JoinTable(
    //     name = "productos_resenias",
    //     joinColumns = @JoinColumn(name = "producto_id"),
    //     inverseJoinColumns = @JoinColumn(name = "resenia_id")
    // )
    // private List<Resenia> resenias;

    @ManyToOne
    @JoinColumn(
        name = "categoria_id"
    )
    private Categoria categoria;
}
