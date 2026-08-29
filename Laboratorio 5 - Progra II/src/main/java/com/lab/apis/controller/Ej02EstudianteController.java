package com.lab.apis.controller;

import com.lab.apis.model.Ej02Estudiante;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estudiantes")
public class Ej02EstudianteController {

	private final List<Ej02Estudiante> estudiantes = new ArrayList<>(List.of(
		new Ej02Estudiante(1L, "Ana", "Lopez", "Ingenieria", 20),
		new Ej02Estudiante(2L, "Luis", "Martinez", "Derecho", 22),
		new Ej02Estudiante(3L, "Maria", "Gomez", "Medicina", 21),
		new Ej02Estudiante(4L, "Carlos", "Ramirez", "Arquitectura", 23)
	));

	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de estudiantes obtenida correctamente", "estudiantes", estudiantes));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej02Estudiante estudiante : estudiantes) {
			if (estudiante.getId().equals(id)) {
				return ResponseEntity.ok(body("Estudiante encontrado", estudiante));
			}
		}
		return noEncontrado("Estudiante no encontrado");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej02Estudiante estudiante) {
		if (!valido(estudiante)) return invalidos();
		estudiante.setId((long) estudiantes.size() + 1);
		estudiantes.add(estudiante);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Estudiante creado correctamente", 201, estudiante));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej02Estudiante actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej02Estudiante estudiante : estudiantes) {
			if (estudiante.getId().equals(id)) {
				estudiante.setNombre(actualizado.getNombre());
				estudiante.setApellido(actualizado.getApellido());
				estudiante.setCarrera(actualizado.getCarrera());
				estudiante.setEdad(actualizado.getEdad());
				return ResponseEntity.ok(codigo("Estudiante actualizado correctamente", 200, estudiante));
			}
		}
		return noEncontrado("Estudiante no encontrado");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej02Estudiante actualizado) {
		for (Ej02Estudiante estudiante : estudiantes) {
			if (estudiante.getId().equals(id)) {
				if (actualizado.getNombre() != null) estudiante.setNombre(actualizado.getNombre());
				if (actualizado.getApellido() != null) estudiante.setApellido(actualizado.getApellido());
				if (actualizado.getCarrera() != null) estudiante.setCarrera(actualizado.getCarrera());
				if (actualizado.getEdad() != null) estudiante.setEdad(actualizado.getEdad());
				return ResponseEntity.ok(codigo("Estudiante actualizado parcialmente", 200, estudiante));
			}
		}
		return noEncontrado("Estudiante no encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return estudiantes.removeIf(estudiante -> estudiante.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Estudiante no encontrado");
	}

	private boolean valido(Ej02Estudiante estudiante) {
		return estudiante != null && texto(estudiante.getNombre()) && texto(estudiante.getApellido())
			&& texto(estudiante.getCarrera()) && estudiante.getEdad() != null && estudiante.getEdad() > 0;
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

	private Map<String, Object> lista(String mensaje, String clave, Object datos) {
		Map<String, Object> respuesta = new LinkedHashMap<>();
		respuesta.put("mensaje", mensaje);
		respuesta.put(clave, estudiantes.size());
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