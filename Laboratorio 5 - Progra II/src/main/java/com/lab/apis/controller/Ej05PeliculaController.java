package com.lab.apis.controller;

import com.lab.apis.model.Ej05Pelicula;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/peliculas")
public class Ej05PeliculaController {

	private final List<Ej05Pelicula> peliculas = new ArrayList<>(List.of(
		new Ej05Pelicula(1L, "Inception", "Christopher Nolan", "Ciencia ficcion", 2010),
		new Ej05Pelicula(2L, "Matrix", "Lana Wachowski", "Accion", 1999),
		new Ej05Pelicula(3L, "El padrino", "Francis Ford Coppola", "Drama", 1972),
		new Ej05Pelicula(4L, "Toy Story", "John Lasseter", "Animacion", 1995)
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de peliculas obtenida correctamente", "peliculas", peliculas));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej05Pelicula pelicula : peliculas) {
			if (pelicula.getId().equals(id)) return ResponseEntity.ok(body("Pelicula encontrada", pelicula));
		}
		return noEncontrado("Pelicula no encontrada");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej05Pelicula pelicula) {
		if (!valido(pelicula)) return invalidos();
		pelicula.setId((long) peliculas.size() + 1);
		peliculas.add(pelicula);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Pelicula creada correctamente", 201, pelicula));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej05Pelicula actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej05Pelicula pelicula : peliculas) {
			if (pelicula.getId().equals(id)) {
				pelicula.setTitulo(actualizado.getTitulo());
				pelicula.setDirector(actualizado.getDirector());
				pelicula.setGenero(actualizado.getGenero());
				pelicula.setAnio(actualizado.getAnio());
				return ResponseEntity.ok(codigo("Pelicula actualizada correctamente", 200, pelicula));
			}
		}
		return noEncontrado("Pelicula no encontrada");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej05Pelicula actualizado) {
		for (Ej05Pelicula pelicula : peliculas) {
			if (pelicula.getId().equals(id)) {
				if (actualizado.getTitulo() != null) pelicula.setTitulo(actualizado.getTitulo());
				if (actualizado.getDirector() != null) pelicula.setDirector(actualizado.getDirector());
				if (actualizado.getGenero() != null) pelicula.setGenero(actualizado.getGenero());
				if (actualizado.getAnio() != null) pelicula.setAnio(actualizado.getAnio());
				return ResponseEntity.ok(codigo("Pelicula actualizada parcialmente", 200, pelicula));
			}
		}
		return noEncontrado("Pelicula no encontrada");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return peliculas.removeIf(pelicula -> pelicula.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Pelicula no encontrada");
	}

	private boolean valido(Ej05Pelicula pelicula) {
		return pelicula != null && texto(pelicula.getTitulo()) && texto(pelicula.getDirector())
			&& texto(pelicula.getGenero()) && pelicula.getAnio() != null && pelicula.getAnio() > 0;
	}

	private boolean texto(String valor) { return valor != null && !valor.isBlank(); }

	private Map<String, Object> body(String mensaje, Object datos) {
		Map<String, Object> respuesta = new LinkedHashMap<>();
		respuesta.put("mensaje", mensaje);
		respuesta.put("datos", datos);
		return respuesta;
	}

	private Map<String, Object> codigo(String mensaje, int codigo, Object datos) {
		Map<String, Object> respuesta = new LinkedHashMap<>();
		respuesta.put("mensaje", mensaje);
		respuesta.put("codigo", codigo);
		respuesta.put("datos", datos);
		return respuesta;
	}

	private Map<String, Object> lista(String mensaje, String clave, List<?> datos) {
		Map<String, Object> respuesta = new LinkedHashMap<>();
		respuesta.put("mensaje", mensaje);
		respuesta.put(clave, datos.size());
		respuesta.put("datos", datos);
		return respuesta;
	}

	private ResponseEntity<Map<String, Object>> noEncontrado(String mensaje) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(codigo(mensaje, 404, null));
	}

	private ResponseEntity<Map<String, Object>> invalidos() {
		return ResponseEntity.badRequest().body(codigo("Datos invalidos", 400, null));
	}
}