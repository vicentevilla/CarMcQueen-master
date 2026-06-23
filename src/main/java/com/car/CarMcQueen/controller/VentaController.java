package com.car.CarMcQueen.controller;

import com.car.CarMcQueen.model.Venta;
import com.car.CarMcQueen.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    // GET /api/ventas
    @GetMapping
    public ResponseEntity<List<Venta>> obtenerTodos() {
        return ResponseEntity.ok(ventaService.obtenerTodos());
    }

    // GET /api/ventas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/ventas
    // Body: { "vendedorId": 1, "clienteId": 2, "vehiculoId": 3 }
    @PostMapping
    public ResponseEntity<Venta> crear(@RequestBody Map<String, Long> body) {
        Long vendedorId = body.get("vendedorId");
        Long clienteId  = body.get("clienteId");
        Long vehiculoId = body.get("vehiculoId");
        return ResponseEntity.status(201).body(ventaService.crear(vendedorId, clienteId, vehiculoId));
    }

    // PUT /api/ventas/{id}/anular → anula la venta
    @PutMapping("/{id}/anular")
    public ResponseEntity<Venta> anular(@PathVariable Long id) {
        return ventaService.anular(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/ventas/vendedor/{vendedorId}
    @GetMapping("/vendedor/{vendedorId}")
    public ResponseEntity<List<Venta>> buscarPorVendedor(@PathVariable Long vendedorId) {
        return ResponseEntity.ok(ventaService.buscarPorVendedor(vendedorId));
    }

    // GET /api/ventas/cliente/{clienteId}
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> buscarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ventaService.buscarPorCliente(clienteId));
    }

    // DELETE /api/ventas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
