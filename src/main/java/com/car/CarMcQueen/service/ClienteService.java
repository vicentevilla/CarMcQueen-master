package com.car.CarMcQueen.service;

import com.car.CarMcQueen.model.Cliente;
import com.car.CarMcQueen.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardar(Cliente cliente) {
        // Regla de negocio: RUT único
        if (clienteRepository.existsByRut(cliente.getRut())) {
            throw new RuntimeException("Ya existe un cliente con el RUT: " + cliente.getRut());
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> actualizar(Long id, Cliente datos) {
        return clienteRepository.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setEmail(datos.getEmail());
            existente.setTelefono(datos.getTelefono());
            return clienteRepository.save(existente);
        });
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
