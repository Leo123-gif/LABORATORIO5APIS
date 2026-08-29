package com.lab.apis.controller;

import com.lab.apis.model.Ej04Empleado;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empleados")
public class Ej04EmpleadoController {

	private final List<Ej04Empleado> empleados = new ArrayList<>(List.of(
		new Ej04Empleado(1L, "Sofia", "Analista", 1200.0, "Finanzas"),
		new Ej04Empleado(2L, "Diego", "Programador", 1800.0, "Tecnologia"),
		new Ej04Empleado(3L, "Laura", "Disenadora", 1500.0, "Marketing"),
		new Ej04Empleado(4L, "Jorge", "Gerente", 2500.0, "Operaciones")
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de empleados obtenida correctamente", "empleados", empleados));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej04Empleado empleado : empleados) {
			if (empleado.getId().equals(id)) return ResponseEntity.ok(body("Empleado encontrado", empleado));
		}
		return noEncontrado("Empleado no encontrado");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej04Empleado empleado) {
		if (!valido(empleado)) return invalidos();
		empleado.setId((long) empleados.size() + 1);
		empleados.add(empleado);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Empleado creado correctamente", 201, empleado));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej04Empleado actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej04Empleado empleado : empleados) {
			if (empleado.getId().equals(id)) {
				empleado.setNombre(actualizado.getNombre());
				empleado.setPuesto(actualizado.getPuesto());
				empleado.setSalario(actualizado.getSalario());
				empleado.setDepartamento(actualizado.getDepartamento());
				return ResponseEntity.ok(codigo("Empleado actualizado correctamente", 200, empleado));
			}
		}
		return noEncontrado("Empleado no encontrado");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej04Empleado actualizado) {
		for (Ej04Empleado empleado : empleados) {
			if (empleado.getId().equals(id)) {
				if (actualizado.getNombre() != null) empleado.setNombre(actualizado.getNombre());
				if (actualizado.getPuesto() != null) empleado.setPuesto(actualizado.getPuesto());
				if (actualizado.getSalario() != null && actualizado.getSalario() > 0) empleado.setSalario(actualizado.getSalario());
				if (actualizado.getDepartamento() != null) empleado.setDepartamento(actualizado.getDepartamento());
				return ResponseEntity.ok(codigo("Empleado actualizado parcialmente", 200, empleado));
			}
		}
		return noEncontrado("Empleado no encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return empleados.removeIf(empleado -> empleado.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Empleado no encontrado");
	}

	private boolean valido(Ej04Empleado empleado) {
		return empleado != null && texto(empleado.getNombre()) && texto(empleado.getPuesto())
			&& empleado.getSalario() != null && empleado.getSalario() > 0 && texto(empleado.getDepartamento());
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