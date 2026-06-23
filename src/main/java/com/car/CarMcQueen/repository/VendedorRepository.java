package com.car.CarMcQueen.repository;

import com.car.CarMcQueen.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {
    boolean existsByRut(String rut);
}
