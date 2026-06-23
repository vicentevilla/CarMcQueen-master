package com.car.CarMcQueen.controller;

import com.car.CarMcQueen.dto.VehiculoRequestDTO;
import com.car.CarMcQueen.dto.VehiculoResponseDTO;
import com.car.CarMcQueen.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ═══════════════════════════════════════════════════
// VehiculoController.java
// Capa web: recibe HTTP, delega al Service, responde JSON.
// No importa la entidad Vehiculo en ningún lugar.
//
// POST y PUT reciben @Valid VehiculoRequestDTO.
//   Si la validación falla → GlobalExceptionHandler
//   devuelve 400 con mapa { campo: mensaje }.
// Todos los métodos devuelven VehiculoResponseDTO.
// ═══════════════════════════════════════════════════

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    // GET /api/vehiculos → 200 OK con lista completa
    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    // GET /api/vehiculos/{id} → 200 OK o 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return vehiculoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/vehiculos → 201 Created
    // @Valid dispara las validaciones del DTO.
    // Si un campo falla → GlobalExceptionHandler → 400
    //   { "patente": "La patente es obligatoria" }
    // Si el tipoVehiculoId no existe → GlobalExceptionHandler → 400
    //   { "error": "TipoVehiculo no encontrado con id: 99" }
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> crear(
            @Valid @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.status(201).body(vehiculoService.guardar(dto));
    }

    // PUT /api/vehiculos/{id} → 200 OK o 404 Not Found
    // @Valid también aplica aquí: mismas validaciones que en POST.
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody VehiculoRequestDTO dto) {
        return vehiculoService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/vehiculos/{id} → 204 No Content o 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vehiculoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorMarca(
            @PathVariable String marca) {
        return ResponseEntity.ok(vehiculoService.buscarPorMarca(marca));
    }

    @GetMapping("/año/{anio}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorAnio(@PathVariable Integer anio) {
        return ResponseEntity.ok(vehiculoService.buscarPorAnioDesde(anio));
    }

    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<VehiculoResponseDTO>> buscarPorTipo(
            @PathVariable Long tipoId) {
        return ResponseEntity.ok(vehiculoService.buscarPorTipo(tipoId));
    }
}
