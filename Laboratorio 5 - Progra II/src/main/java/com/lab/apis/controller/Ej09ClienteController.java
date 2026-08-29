package com.lab.apis.controller;

import com.lab.apis.model.Ej09Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class Ej09ClienteController {

	private final List<Ej09Cliente> clientes = new ArrayList<>(List.of(
		new Ej09Cliente(1L, "Ana", "Lopez", "ana@example.com", "555-1001"),
		new Ej09Cliente(2L, "Luis", "Martinez", "luis@example.com", "555-1002"),
		new Ej09Cliente(3L, "Maria", "Gomez", "maria@example.com", "555-1003"),
		new Ej09Cliente(4L, "Carlos", "Ramirez", "carlos@example.com", "555-1004")
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de clientes obtenida correctamente", "clientes", clientes));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej09Cliente cliente : clientes) {
			if (cliente.getId().equals(id)) return ResponseEntity.ok(body("Cliente encontrado", cliente));
		}
		return noEncontrado("Cliente no encontrado");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej09Cliente cliente) {
		if (!valido(cliente)) return invalidos();
		cliente.setId((long) clientes.size() + 1);
		clientes.add(cliente);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Cliente creado correctamente", 201, cliente));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej09Cliente actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej09Cliente cliente : clientes) {
			if (cliente.getId().equals(id)) {
				cliente.setNombre(actualizado.getNombre());
				cliente.setApellido(actualizado.getApellido());
				cliente.setCorreo(actualizado.getCorreo());
				cliente.setTelefono(actualizado.getTelefono());
				return ResponseEntity.ok(codigo("Cliente actualizado correctamente", 200, cliente));
			}
		}
		return noEncontrado("Cliente no encontrado");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej09Cliente actualizado) {
		for (Ej09Cliente cliente : clientes) {
			if (cliente.getId().equals(id)) {
				if (actualizado.getNombre() != null) cliente.setNombre(actualizado.getNombre());
				if (actualizado.getApellido() != null) cliente.setApellido(actualizado.getApellido());
				if (actualizado.getCorreo() != null) cliente.setCorreo(actualizado.getCorreo());
				if (actualizado.getTelefono() != null) cliente.setTelefono(actualizado.getTelefono());
				return ResponseEntity.ok(codigo("Cliente actualizado parcialmente", 200, cliente));
			}
		}
		return noEncontrado("Cliente no encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return clientes.removeIf(cliente -> cliente.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Cliente no encontrado");
	}
	private boolean valido(Ej09Cliente c) { return c != null && texto(c.getNombre()) && texto(c.getApellido()) && texto(c.getCorreo()) && texto(c.getTelefono()); }
	private boolean texto(String v) { return v != null && !v.isBlank(); }
	private Map<String, Object> body(String m, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("datos", d); return r; }
	private Map<String, Object> codigo(String m, int c, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("codigo", c); r.put("datos", d); return r; }
	private Map<String, Object> lista(String m, String k, List<?> d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put(k, d.size()); r.put("datos", d); return r; }
	private ResponseEntity<Map<String, Object>> noEncontrado(String m) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(codigo(m, 404, null)); }
	private ResponseEntity<Map<String, Object>> invalidos() { return ResponseEntity.badRequest().body(codigo("Datos invalidos", 400, null)); }
}