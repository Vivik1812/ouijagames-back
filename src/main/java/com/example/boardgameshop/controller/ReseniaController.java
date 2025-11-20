package com.example.boardgameshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boardgameshop.model.Resenia;
import com.example.boardgameshop.service.ReseniaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review Management System")
public class ReseniaController {

	@Autowired
	private ReseniaService reseniaService;

	@GetMapping
	@Operation(summary = "View a list of available reviews")
	public List<Resenia> getAllResenias() {
		return reseniaService.getAllResenias();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a review by Id")
	public Resenia getReseniaById(@PathVariable Long id) {
		return reseniaService.getReseniaById(id);
	}

	@PostMapping
	@Operation(summary = "Add a new review")
	public Resenia createResenia(@RequestBody Resenia resenia) {
		return reseniaService.saveResenia(resenia);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an existing review")
	public Resenia updateResenia(@PathVariable Long id, @RequestBody Resenia resenia) {
		Resenia existing = reseniaService.getReseniaById(id);
		if (existing != null) {
			existing.setContenido(resenia.getContenido());
			return reseniaService.saveResenia(existing);
		}
		return null;
	}

	@PatchMapping("/{id}")
	@Operation(summary = "Partially update an existing review")
	public Resenia partiallyUpdateResenia(@PathVariable Long id, @RequestBody Resenia resenia) {
		Resenia existing = reseniaService.getReseniaById(id);
		if (existing != null) {
			if (resenia.getContenido() != null) {
				existing.setContenido(resenia.getContenido());
			}
			return reseniaService.saveResenia(existing);
		}
		return null;
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a review")
	public void deleteResenia(@PathVariable Long id) {
		reseniaService.deleteResenia(id);
	}
}