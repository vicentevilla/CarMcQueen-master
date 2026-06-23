package com.car.CarMcQueen.service;

import com.car.CarMcQueen.model.Cliente;
import com.car.CarMcQueen.model.Venta;
import com.car.CarMcQueen.model.Vendedor;
import com.car.CarMcQueen.model.Vehiculo;
import com.car.CarMcQueen.repository.ClienteRepository;
import com.car.CarMcQueen.repository.VentaRepository;
import com.car.CarMcQueen.repository.VendedorRepository;
import com.car.CarMcQueen.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final VendedorRepository vendedorRepository;
    private final ClienteRepository clienteRepository;
    private final VehiculoRepository vehiculoRepository;

    public List<Venta> obtenerTodos() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta crear(Long vendedorId, Long clienteId, Long vehiculoId) {

        // Regla de negocio: vendedor debe existir
        Vendedor vendedor = vendedorRepository.findById(vendedorId)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado con id: " + vendedorId));

        // Regla de negocio: cliente debe existir
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + clienteId));

        // Regla de negocio: vehículo debe existir
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con id: " + vehiculoId));

        // Regla de negocio: vehículo no puede haberse vendido antes
        if (ventaRepository.vehiculoYaVendido(vehiculoId)) {
            throw new RuntimeException("El vehículo con id " + vehiculoId + " ya fue vendido");
        }

        // El precio total se toma del precio del vehículo
        Venta venta = new Venta(
                null,
                LocalDate.now(),
                vehiculo.getPrecio(),
                "COMPLETADA",
                vendedor,
                cliente,
                vehiculo
        );

        return ventaRepository.save(venta);
    }

    public Optional<Venta> anular(Long id) {
        return ventaRepository.findById(id).map(existente -> {
            if ("ANULADA".equals(existente.getEstado())) {
                throw new RuntimeException("La venta ya está anulada");
            }
            existente.setEstado("ANULADA");
            return ventaRepository.save(existente);
        });
    }

    public List<Venta> buscarPorVendedor(Long vendedorId) {
        return ventaRepository.findByVendedorId(vendedorId);
    }

    public List<Venta> buscarPorCliente(Long clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }
}
