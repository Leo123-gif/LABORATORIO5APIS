package com.lab.apis.controller;

import com.lab.apis.model.Ej08Tarea;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
public class Ej08TareaController {

	private final List<Ej08Tarea> tareas = new ArrayList<>(List.of(
		new Ej08Tarea(1L, "Estudiar", "Repasar Java", "Alta", false),
		new Ej08Tarea(2L, "Comprar", "Comprar materiales", "Media", false),
		new Ej08Tarea(3L, "Entrenar", "Ir al gimnasio", "Baja", true),
		new Ej08Tarea(4L, "Leer", "Leer un capitulo", "Media", false)
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de tareas obtenida correctamente", "tareas", tareas));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej08Tarea tarea : tareas) {
			if (tarea.getId().equals(id)) return ResponseEntity.ok(body("Tarea encontrada", tarea));
		}
		return noEncontrado("Tarea no encontrada");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej08Tarea tarea) {
		if (!valido(tarea)) return invalidos();
		tarea.setId((long) tareas.size() + 1);
		tareas.add(tarea);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Tarea creada correctamente", 201, tarea));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej08Tarea actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej08Tarea tarea : tareas) {
			if (tarea.getId().equals(id)) {
				tarea.setTitulo(actualizado.getTitulo());
				tarea.setDescripcion(actualizado.getDescripcion());
				tarea.setPrioridad(actualizado.getPrioridad());
				tarea.setCompletada(actualizado.getCompletada());
				return ResponseEntity.ok(codigo("Tarea actualizada correctamente", 200, tarea));
			}
		}
		return noEncontrado("Tarea no encontrada");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej08Tarea actualizado) {
		for (Ej08Tarea tarea : tareas) {
			if (tarea.getId().equals(id)) {
				if (actualizado.getTitulo() != null) tarea.setTitulo(actualizado.getTitulo());
				if (actualizado.getDescripcion() != null) tarea.setDescripcion(actualizado.getDescripcion());
				if (actualizado.getPrioridad() != null) tarea.setPrioridad(actualizado.getPrioridad());
				if (actualizado.getCompletada() != null) tarea.setCompletada(actualizado.getCompletada());
				return ResponseEntity.ok(codigo("Tarea actualizada parcialmente", 200, tarea));
			}
		}
		return noEncontrado("Tarea no encontrada");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return tareas.removeIf(tarea -> tarea.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Tarea no encontrada");
	}
	private boolean valido(Ej08Tarea t) { return t != null && texto(t.getTitulo()) && texto(t.getDescripcion()) && texto(t.getPrioridad()) && t.getCompletada() != null; }
	private boolean texto(String v) { return v != null && !v.isBlank(); }
	private Map<String, Object> body(String m, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("datos", d); return r; }
	private Map<String, Object> codigo(String m, int c, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("codigo", c); r.put("datos", d); return r; }
	private Map<String, Object> lista(String m, String k, List<?> d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put(k, d.size()); r.put("datos", d); return r; }
	private ResponseEntity<Map<String, Object>> noEncontrado(String m) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(codigo(m, 404, null)); }
	private ResponseEntity<Map<String, Object>> invalidos() { return ResponseEntity.badRequest().body(codigo("Datos invalidos", 400, null)); }
}