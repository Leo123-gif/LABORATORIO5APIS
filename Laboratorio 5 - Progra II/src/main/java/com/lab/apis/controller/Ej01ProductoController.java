package com.lab.apis.controller;
import com.lab.apis.model.Ej01Producto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Actualización laboratorio 5


@RestController
@RequestMapping("/api/productos")
public class Ej01ProductoController {

    private List<Ej01Producto> productos = new ArrayList<>(
    List.of(
        new Ej01Producto(1L, "Monitor", 150.0, "Electrónica"),
        new Ej01Producto(2L, "Teclado", 50.0, "Electrónica")
        )
    );

    // GET - Todos
    @GetMapping
    public ResponseEntity<?> obtenerTodosLosProductos() {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("Mensaje", "Lista de productos obtenida correctamente");
        respuesta.put("Productos", productos.size());
        respuesta.put("Datos", productos);

        return ResponseEntity.ok(respuesta);
    }

    // GET - Por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long id) {

        for (Ej01Producto producto : productos) {
            if (producto.getId().equals(id)) {
                return ResponseEntity.ok(
                    Map.of(
                        "Mensaje", "Producto encontrado",
                        "Datos", producto
                    )
                );
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            Map.of(
                "Mensaje", "Producto no encontrado"
            )
        );
    }

    // POST - Crear
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Ej01Producto producto) {

        // Validación simple
        if (producto.getNombre() == null || producto.getNombre().isBlank()
                || producto.getPrecio() == null || producto.getPrecio() <= 0) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of(
                    "mensaje", "Datos inválidos",
                    "codigo", 400
                )
            );
        }

        // Validar duplicado
        for (Ej01Producto item : productos) {
            if (item.getNombre().equalsIgnoreCase(producto.getNombre())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of(
                        "mensaje", "Ya existe un producto con ese nombre",
                        "codigo", 409
                    )
                );
            }
        }

        producto.setId((long) productos.size() + 1);
        productos.add(producto);

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Producto se ha creado correctamente");
        respuesta.put("codigo", 201);
        respuesta.put("datos", producto);

        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // PUT - Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(
        @PathVariable Long id,
        @RequestBody Ej01Producto productoActualizado) {

    for (Ej01Producto producto : productos) {
        if (producto.getId().equals(id)) {

            if (productoActualizado.getNombre() == null
                    || productoActualizado.getNombre().isBlank()
                    || productoActualizado.getPrecio() == null
                    || productoActualizado.getPrecio() <= 0) {

                return ResponseEntity.badRequest().body(
                    Map.of(
                        "mensaje", "Datos inválidos",
                        "codigo", 400
                    )
                );
            }

            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setCategoria(productoActualizado.getCategoria());

            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Producto ha sido actualizado correctamente",
                    "codigo", 200,
                    "datos", producto
                )
            );
        }
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Map.of(
            "mensaje", "Producto no encontrado",
            "codigo", 404
        )
    );
    }

    // PATCH - Actualizar parcialmente
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcialmente(
        @PathVariable Long id,
        @RequestBody Ej01Producto productoActualizado) {

    for (Ej01Producto producto : productos) {
        if (producto.getId().equals(id)) {

            if (productoActualizado.getNombre() != null) {

                if (productoActualizado.getNombre().isBlank()) {
                    return ResponseEntity.badRequest().body(
                        Map.of(
                            "mensaje", "El nombre no puede estar vacío",
                            "codigo", 400
                        )
                    );
                }

                producto.setNombre(productoActualizado.getNombre());
            }

            if (productoActualizado.getPrecio() != null) {

                if (productoActualizado.getPrecio() <= 0) {
                    return ResponseEntity.badRequest().body(
                        Map.of(
                            "mensaje", "El precio debe ser mayor que cero",
                            "codigo", 400
                        )
                    );
                }

                producto.setPrecio(productoActualizado.getPrecio());
            }

            if (productoActualizado.getCategoria() != null) {
                producto.setCategoria(productoActualizado.getCategoria());
            }

            return ResponseEntity.ok(
                Map.of(
                    "mensaje", "Producto ha sido actualizado parcialmente",
                    "codigo", 200,
                    "datos", producto
                )
            );
        }
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Map.of(
            "mensaje", "Producto no encontrado",
            "codigo", 404
        )
    );
}

    // DELETE - Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {

    for (Ej01Producto producto : productos) {
        if (producto.getId().equals(id)) {

            productos.remove(producto);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        Map.of(
            "mensaje", "Producto no encontrado",
            "codigo", 404
        )
    );
}

}