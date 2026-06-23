package com.car.CarMcQueen.controller;

import com.car.CarMcQueen.model.TipoVehiculo;
import com.car.CarMcQueen.service.TipoVehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ═══════════════════════════════════════════════════
// TipoVehiculoController.java
// Capa web: recibe HTTP, delega al Service, responde JSON.
// Usa ResponseEntity para controlar códigos HTTP exactos.
//
// @RestController = @Controller + @ResponseBody
//   Todos los métodos retornan JSON automáticamente.
// @RequestMapping: ruta base para todos los métodos.
// @RequiredArgsConstructor: Lombok inyecta el Service.
// ═══════════════════════════════════════════════════

@RestController
@RequestMapping("/api/tipos-vehiculo")
@RequiredArgsConstructor
public class TipoVehiculoController {

    private final TipoVehiculoService tipoVehiculoService;

    // GET /api/tipos-vehiculo → 200 OK con lista (puede ser [])
    @GetMapping
    public ResponseEntity<List<TipoVehiculo>> obtenerTodos() {
        return ResponseEntity.ok(tipoVehiculoService.obtenerTodos());
    }

    // GET /api/tipos-vehiculo/{id} → 200 OK o 404 Not Found
    // @PathVariable extrae el {id} de la URL.
    @GetMapping("/{id}")
    public ResponseEntity<TipoVehiculo> obtenerPorId(@PathVariable Long id) {
        return tipoVehiculoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/tipos-vehiculo → 201 Created
    @PostMapping
    public ResponseEntity<TipoVehiculo> crear(@RequestBody TipoVehiculo tipoVehiculo) {
        TipoVehiculo nuevo = tipoVehiculoService.guardar(tipoVehiculo);
        return ResponseEntity.status(201).body(nuevo);
    }

    // PUT /api/tipos-vehiculo/{id} → 200 OK o 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<TipoVehiculo> actualizar(
            @PathVariable Long id,
            @RequestBody TipoVehiculo datos) {
        return tipoVehiculoService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tipos-vehiculo/{id} → 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (tipoVehiculoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        tipoVehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
