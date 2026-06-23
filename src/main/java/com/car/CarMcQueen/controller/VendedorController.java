package com.car.CarMcQueen.controller;

import com.car.CarMcQueen.model.Vendedor;
import com.car.CarMcQueen.service.VendedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedores")
@RequiredArgsConstructor
public class VendedorController {

    private final VendedorService vendedorService;

    @GetMapping
    public ResponseEntity<List<Vendedor>> obtenerTodos() {
        return ResponseEntity.ok(vendedorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendedor> obtenerPorId(@PathVariable Long id) {
        return vendedorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vendedor> crear(@RequestBody Vendedor vendedor) {
        return ResponseEntity.status(201).body(vendedorService.guardar(vendedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vendedor> actualizar(@PathVariable Long id, @RequestBody Vendedor datos) {
        return vendedorService.actualizar(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (vendedorService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        vendedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
