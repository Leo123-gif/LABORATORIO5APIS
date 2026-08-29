package com.lab.apis.controller;

import com.lab.apis.model.Ej10Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class Ej10PedidoController {

	private final List<Ej10Pedido> pedidos = new ArrayList<>(List.of(
		new Ej10Pedido(1L, "Ana Lopez", "Monitor", 1, 150.0, "PENDIENTE"),
		new Ej10Pedido(2L, "Luis Martinez", "Teclado", 2, 100.0, "ENVIADO"),
		new Ej10Pedido(3L, "Maria Gomez", "Bocina", 1, 750.0, "ENTREGADO"),
		new Ej10Pedido(4L, "Carlos Ramirez", "Mouse", 3, 90.0, "PENDIENTE")
	));
	@GetMapping
	public ResponseEntity<?> todos() {
		return ResponseEntity.ok(lista("Lista de pedidos obtenida correctamente", "pedidos", pedidos));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> porId(@PathVariable Long id) {
		for (Ej10Pedido pedido : pedidos) {
			if (pedido.getId().equals(id)) return ResponseEntity.ok(body("Pedido encontrado", pedido));
		}
		return noEncontrado("Pedido no encontrado");
	}

	@PostMapping
	public ResponseEntity<?> crear(@RequestBody Ej10Pedido pedido) {
		if (!valido(pedido)) return invalidos();
		pedido.setId((long) pedidos.size() + 1);
		pedidos.add(pedido);
		return ResponseEntity.status(HttpStatus.CREATED).body(codigo("Pedido creado correctamente", 201, pedido));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Ej10Pedido actualizado) {
		if (!valido(actualizado)) return invalidos();
		for (Ej10Pedido pedido : pedidos) {
			if (pedido.getId().equals(id)) {
				pedido.setCliente(actualizado.getCliente());
				pedido.setProducto(actualizado.getProducto());
				pedido.setCantidad(actualizado.getCantidad());
				pedido.setTotal(actualizado.getTotal());
				pedido.setEstado(actualizado.getEstado());
				return ResponseEntity.ok(codigo("Pedido actualizado correctamente", 200, pedido));
			}
		}
		return noEncontrado("Pedido no encontrado");
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> parcial(@PathVariable Long id, @RequestBody Ej10Pedido actualizado) {
		for (Ej10Pedido pedido : pedidos) {
			if (pedido.getId().equals(id)) {
				if (actualizado.getCliente() != null) pedido.setCliente(actualizado.getCliente());
				if (actualizado.getProducto() != null) pedido.setProducto(actualizado.getProducto());
				if (actualizado.getCantidad() != null && actualizado.getCantidad() > 0) pedido.setCantidad(actualizado.getCantidad());
				if (actualizado.getTotal() != null && actualizado.getTotal() > 0) pedido.setTotal(actualizado.getTotal());
				if (actualizado.getEstado() != null) pedido.setEstado(actualizado.getEstado());
				return ResponseEntity.ok(codigo("Pedido actualizado parcialmente", 200, pedido));
			}
		}
		return noEncontrado("Pedido no encontrado");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		return pedidos.removeIf(pedido -> pedido.getId().equals(id))
			? ResponseEntity.noContent().build() : noEncontrado("Pedido no encontrado");
	}
	private boolean valido(Ej10Pedido p) { return p != null && texto(p.getCliente()) && texto(p.getProducto()) && p.getCantidad() != null && p.getCantidad() > 0 && p.getTotal() != null && p.getTotal() > 0 && texto(p.getEstado()); }
	private boolean texto(String v) { return v != null && !v.isBlank(); }
	private Map<String, Object> body(String m, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("datos", d); return r; }
	private Map<String, Object> codigo(String m, int c, Object d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put("codigo", c); r.put("datos", d); return r; }
	private Map<String, Object> lista(String m, String k, List<?> d) { Map<String, Object> r = new LinkedHashMap<>(); r.put("mensaje", m); r.put(k, d.size()); r.put("datos", d); return r; }
	private ResponseEntity<Map<String, Object>> noEncontrado(String m) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(codigo(m, 404, null)); }
	private ResponseEntity<Map<String, Object>> invalidos() { return ResponseEntity.badRequest().body(codigo("Datos invalidos", 400, null)); }
}