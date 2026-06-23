package com.car.CarMcQueen.repository;

import com.car.CarMcQueen.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByRut(String rut);
}
