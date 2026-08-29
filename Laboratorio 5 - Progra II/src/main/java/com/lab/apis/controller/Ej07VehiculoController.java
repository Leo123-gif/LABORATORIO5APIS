package com.lab.apis.controller;

import com.lab.apis.model.Ej07Vehiculo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehiculos")
public class Ej07VehiculoController {

	private final List<Ej07Vehiculo> vehiculos = new ArrayList<>(List.of(
		new Ej07Vehiculo(1L, "Toyota", "Corolla", 2022, 22000.0),
		new Ej07Vehiculo(2L, "Honda", "Civic", 2021, 24000.0),
		new Ej07Vehiculo(3L, "Ford", "Mustang", 2023, 38000.0),
		new Ej07Vehiculo(4L, "Nissan", "Sentra", 2020, 19000.0)
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de vehiculos obtenida correctamente", "vehiculos", vehiculos));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej07Vehiculo vehiculo : vehiculos) {
			if (vehiculo.getId().equals(id)) return ResponseEntity.ok(body("Vehiculo encontrado", vehiculo));
		}
		return noEncontrado("Vehiculo no encontrado");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej07Vehiculo vehiculo) {
		if (!valido(vehiculo)) return invalidos();
		vehiculo.setId((long) vehiculos.size() + 1);
		vehiculos.add(vehiculo);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Vehiculo creado correctamente", 201, vehiculo));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej07Vehiculo actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej07Vehiculo vehiculo : vehiculos) {
			if (vehiculo.getId().equals(id)) {
				vehiculo.setMarca(actualizado.getMarca());
				vehiculo.setModelo(actualizado.getModelo());
				vehiculo.setAnio(actualizado.getAnio());
				vehiculo.setPrecio(actualizado.getPrecio());
				return ResponseEntity.ok(codigo("Vehiculo actualizado correctamente", 200, vehiculo));
			}
		}
		return noEncontrado("Vehiculo no encontrado");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej07Vehiculo actualizado) {
		for (Ej07Vehiculo vehiculo : vehiculos) {
			if (vehiculo.getId().equals(id)) {
				if (actualizado.getMarca() != null) vehiculo.setMarca(actualizado.getMarca());
				if (actualizado.getModelo() != null) vehiculo.setModelo(actualizado.getModelo());
				if (actualizado.getAnio() != null) vehiculo.setAnio(actualizado.getAnio());
				if (actualizado.getPrecio() != null && actualizado.getPrecio() > 0) vehiculo.setPrecio(actualizado.getPrecio());
				return ResponseEntity.ok(codigo("Vehiculo actualizado parcialmente", 200, vehiculo));
			}
		}
		return noEncontrado("Vehiculo no encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return vehiculos.removeIf(vehiculo -> vehiculo.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Vehiculo no encontrado");
	}
	private boolean valido(Ej07Vehiculo v) { return v != null && texto(v.getMarca()) && texto(v.getModelo()) && v.getAnio() != null && v.getAnio() > 0 && v.getPrecio() != null && v.getPrecio() > 0; }
	private boolean texto(String v) { return v != null && !v.isBlank(); }
	private Map<String, Object> body(String m, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("datos", d); return r; }
	private Map<String, Object> codigo(String m, int c, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("codigo", c); r.put("datos", d); return r; }
	private Map<String, Object> lista(String m, String k, List<?> d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put(k, d.size()); r.put("datos", d); return r; }
	private ResponseEntity<Map<String, Object>> noEncontrado(String m) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(codigo(m, 404, null)); }
	private ResponseEntity<Map<String, Object>> invalidos() { return ResponseEntity.badRequest().body(codigo("Datos invalidos", 400, null)); }
}