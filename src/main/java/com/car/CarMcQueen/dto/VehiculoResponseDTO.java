package com.car.CarMcQueen.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ═══════════════════════════════════════════════════
// VehiculoResponseDTO.java
// DTO de SALIDA: lo que el servidor devuelve al cliente.
// Sin anotaciones de validación: este DTO lo construye
// el Service, no viene del cliente, por eso no necesita @Valid.
//
// Expone el nombre del tipo (string legible) en lugar
// del objeto TipoVehiculo completo, evitando referencias
// circulares y exponiendo solo lo que el cliente necesita.
// ═══════════════════════════════════════════════════

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDTO {

    private Long id;
    private String patente;
    private String marca;
    private String modelo;
    private Integer anio;
    private BigDecimal precio;

    // Solo el nombre del tipo, no el objeto completo.
    private String tipoVehiculoNombre;
}
