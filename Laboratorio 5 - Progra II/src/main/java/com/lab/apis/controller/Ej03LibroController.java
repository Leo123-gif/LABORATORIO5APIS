package com.lab.apis.controller;

import com.lab.apis.model.Ej03Libro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/libros")
public class Ej03LibroController {

	private final List<Ej03Libro> libros = new ArrayList<>(List.of(
		new Ej03Libro(1L, "Cien anos de soledad", "Gabriel Garcia Marquez", "Novela", 25.0),
		new Ej03Libro(2L, "El principito", "Antoine de Saint-Exupery", "Fabula", 18.0),
		new Ej03Libro(3L, "1984", "George Orwell", "Distopia", 22.0),
		new Ej03Libro(4L, "Don Quijote", "Miguel de Cervantes", "Aventura", 30.0)
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de libros obtenida correctamente", "libros", libros));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej03Libro libro : libros) {
			if (libro.getId().equals(id)) return ResponseEntity.ok(body("Libro encontrado", libro));
		}
		return noEncontrado("Libro no encontrado");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej03Libro libro) {
		if (!valido(libro)) return invalidos();
		libro.setId((long) libros.size() + 1);
		libros.add(libro);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Libro creado correctamente", 201, libro));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej03Libro actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej03Libro libro : libros) {
			if (libro.getId().equals(id)) {
				libro.setTitulo(actualizado.getTitulo());
				libro.setAutor(actualizado.getAutor());
				libro.setGenero(actualizado.getGenero());
				libro.setPrecio(actualizado.getPrecio());
				return ResponseEntity.ok(codigo("Libro actualizado correctamente", 200, libro));
			}
		}
		return noEncontrado("Libro no encontrado");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej03Libro actualizado) {
		for (Ej03Libro libro : libros) {
			if (libro.getId().equals(id)) {
				if (actualizado.getTitulo() != null) libro.setTitulo(actualizado.getTitulo());
				if (actualizado.getAutor() != null) libro.setAutor(actualizado.getAutor());
				if (actualizado.getGenero() != null) libro.setGenero(actualizado.getGenero());
				if (actualizado.getPrecio() != null && actualizado.getPrecio() > 0) libro.setPrecio(actualizado.getPrecio());
				return ResponseEntity.ok(codigo("Libro actualizado parcialmente", 200, libro));
			}
		}
		return noEncontrado("Libro no encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return libros.removeIf(libro -> libro.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Libro no encontrado");
	}

	private boolean valido(Ej03Libro libro) {
		return libro != null && texto(libro.getTitulo()) && texto(libro.getAutor())
			&& texto(libro.getGenero()) && libro.getPrecio() != null && libro.getPrecio() > 0;
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