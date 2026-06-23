package com.car.CarMcQueen.repository;

import com.car.CarMcQueen.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    // Buscar ventas por vendedor
    @Query("SELECT v FROM Venta v WHERE v.vendedor.id = ?1")
    List<Venta> findByVendedorId(Long vendedorId);

    // Buscar ventas por cliente
    @Query("SELECT v FROM Venta v WHERE v.cliente.id = ?1")
    List<Venta> findByClienteId(Long clienteId);

    // Verificar si un vehículo ya fue vendido (estado COMPLETADA)
    @Query("SELECT COUNT(v) > 0 FROM Venta v WHERE v.vehiculo.id = ?1 AND v.estado = 'COMPLETADA'")
    boolean vehiculoYaVendido(Long vehiculoId);
}
