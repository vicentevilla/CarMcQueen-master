package com.car.CarMcQueen.service;

import com.car.CarMcQueen.model.Vendedor;
import com.car.CarMcQueen.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendedorService {

    private final VendedorRepository vendedorRepository;

    public List<Vendedor> obtenerTodos() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> obtenerPorId(Long id) {
        return vendedorRepository.findById(id);
    }

    public Vendedor guardar(Vendedor vendedor) {
        // Regla de negocio: RUT único
        if (vendedorRepository.existsByRut(vendedor.getRut())) {
            throw new RuntimeException("Ya existe un vendedor con el RUT: " + vendedor.getRut());
        }
        return vendedorRepository.save(vendedor);
    }

    public Optional<Vendedor> actualizar(Long id, Vendedor datos) {
        return vendedorRepository.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setEmail(datos.getEmail());
            existente.setTelefono(datos.getTelefono());
            return vendedorRepository.save(existente);
        });
    }

    public void eliminar(Long id) {
        vendedorRepository.deleteById(id);
    }
}
