package com.car.CarMcQueen.repository;

import com.car.CarMcQueen.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Consultas personalizadas

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // Para verificar si existe un vehículo con una patente dada.
    boolean existsByPatente(String patente);

    @Query("SELECT v FROM Vehiculo v WHERE v.marca = ?1")
    List<Vehiculo> findByMarca(String marca);

    @Query("SELECT v FROM Vehiculo v WHERE v.anio >= ?1")
    List<Vehiculo> findByAnio(Integer anio);

    @Query("SELECT v FROM Vehiculo v WHERE v.tipoVehiculo.id = :tipoID")
    List<Vehiculo> findByTipoVehiculoId(@Param("tipoID") Long tipoId);
}
