package com.lab.apis.controller;

import com.lab.apis.model.Ej06Curso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cursos")
public class Ej06CursoController {

	private final List<Ej06Curso> cursos = new ArrayList<>(List.of(
		new Ej06Curso(1L, "Programacion", "Fundamentos de programacion", 4, "Presencial"),
		new Ej06Curso(2L, "Bases de datos", "Diseno y consultas SQL", 3, "Virtual"),
		new Ej06Curso(3L, "Redes", "Conceptos de redes informaticas", 3, "Presencial"),
		new Ej06Curso(4L, "Ingenieria de software", "Analisis y desarrollo de sistemas", 4, "Hibrida")
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de cursos obtenida correctamente", "cursos", cursos));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej06Curso curso : cursos) {
			if (curso.getId().equals(id)) return ResponseEntity.ok(body("Curso encontrado", curso));
		}
		return noEncontrado("Curso no encontrado");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej06Curso curso) {
		if (!valido(curso)) return invalidos();
		curso.setId((long) cursos.size() + 1);
		cursos.add(curso);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Curso creado correctamente", 201, curso));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej06Curso actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej06Curso curso : cursos) {
			if (curso.getId().equals(id)) {
				curso.setNombre(actualizado.getNombre());
				curso.setDescripcion(actualizado.getDescripcion());
				curso.setCreditos(actualizado.getCreditos());
				curso.setModalidad(actualizado.getModalidad());
				return ResponseEntity.ok(codigo("Curso actualizado correctamente", 200, curso));
			}
		}
		return noEncontrado("Curso no encontrado");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej06Curso actualizado) {
		for (Ej06Curso curso : cursos) {
			if (curso.getId().equals(id)) {
				if (actualizado.getNombre() != null) curso.setNombre(actualizado.getNombre());
				if (actualizado.getDescripcion() != null) curso.setDescripcion(actualizado.getDescripcion());
				if (actualizado.getCreditos() != null) curso.setCreditos(actualizado.getCreditos());
				if (actualizado.getModalidad() != null) curso.setModalidad(actualizado.getModalidad());
				return ResponseEntity.ok(codigo("Curso actualizado parcialmente", 200, curso));
			}
		}
		return noEncontrado("Curso no encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return cursos.removeIf(curso -> curso.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Curso no encontrado");
	}

	private boolean valido(Ej06Curso curso) {
		return curso != null && texto(curso.getNombre()) && texto(curso.getDescripcion())
			&& curso.getCreditos() != null && curso.getCreditos() > 0 && texto(curso.getModalidad());
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