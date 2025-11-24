package com.example.boardgameshop.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
    private String img;
    private Long categoriaId;
}
