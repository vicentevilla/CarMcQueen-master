package com.car.CarMcQueen.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// ═══════════════════════════════════════════════════
// VehiculoRequestDTO.java
// DTO de ENTRADA: datos que el cliente envía en POST/PUT.
//
// REGLA CLAVE:
//   Las validaciones (Bean Validation JSR-380) viven
//   AQUÍ y solo aquí, no en la entidad Vehiculo.java.
//   Cuando el Controller usa @Valid, Spring valida este
//   DTO. Si falla → GlobalExceptionHandler → 400 con
//   mapa { "campo": "mensaje de error" }.
//
// CORRECCIÓN respecto al original:
//   precio era @NotBlank (solo para String) en BigDecimal
//   → se cambió a @NotNull + @Positive (correcto para números).
// ═══════════════════════════════════════════════════

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoRequestDTO {

    @NotBlank(message = "La patente es obligatoria")
    @Size(min = 6, max = 6, message = "La patente debe tener exactamente 6 caracteres")
    private String patente;

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año es obligatorio")
    @Min(value = 1980, message = "El año debe ser mayor o igual a 1980")
    private Integer anio;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "El tipo de vehículo es obligatorio")
    private Long tipoVehiculoId;
}
