package com.car.CarMcQueen.repository;

import com.car.CarMcQueen.model.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository tiene el CRUD completo

public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long> {
}
