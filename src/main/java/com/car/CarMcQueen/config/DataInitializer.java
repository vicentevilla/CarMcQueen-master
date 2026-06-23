package com.car.CarMcQueen.config;

import com.car.CarMcQueen.model.*;
import com.car.CarMcQueen.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final TipoVehiculoRepository tipoVehiculoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final VendedorRepository vendedorRepository;
    private final ClienteRepository clienteRepository;
    private final VentaRepository ventaRepository;

    @Override
    public void run(String... args) {

        // Si ya hay datos, no insertar más
        if (tipoVehiculoRepository.count() > 0) return;

        // ── TIPOS DE VEHÍCULO ─────────────────────────
        TipoVehiculo sedan     = tipoVehiculoRepository.save(new TipoVehiculo(null, "Sedán",     "Automóvil de 4 puertas"));
        TipoVehiculo suv       = tipoVehiculoRepository.save(new TipoVehiculo(null, "SUV",       "Vehículo deportivo utilitario"));
        TipoVehiculo hatchback = tipoVehiculoRepository.save(new TipoVehiculo(null, "Hatchback", "Compacto con puerta trasera"));
        TipoVehiculo camioneta = tipoVehiculoRepository.save(new TipoVehiculo(null, "Camioneta", "Vehículo de carga"));

        // ── VEHÍCULOS ─────────────────────────────────
        Vehiculo v1 = vehiculoRepository.save(new Vehiculo(null, "AB1234", "Toyota",    "Corolla",   2020, new BigDecimal("15000000"), sedan));
        Vehiculo v2 = vehiculoRepository.save(new Vehiculo(null, "CD5678", "Honda",     "CR-V",      2021, new BigDecimal("22000000"), suv));
        vehiculoRepository.save(new Vehiculo(null, "EF9012", "Ford",      "Ranger",    2019, new BigDecimal("18500000"), camioneta));
        vehiculoRepository.save(new Vehiculo(null, "GH3456", "Chevrolet", "Spark",     2022, new BigDecimal("9800000"),  hatchback));
        vehiculoRepository.save(new Vehiculo(null, "IJ7890", "Nissan",    "Frontier",  2023, new BigDecimal("25000000"), camioneta));

        // ── VENDEDORES ────────────────────────────────
        Vendedor vend1 = vendedorRepository.save(new Vendedor(null, "Carlos Pérez",  "12345678-9", "carlos@carmcqueen.cl",  "+56912345678"));
        Vendedor vend2 = vendedorRepository.save(new Vendedor(null, "Ana González",  "98765432-1", "ana@carmcqueen.cl",     "+56987654321"));

        // ── CLIENTES ──────────────────────────────────
        Cliente cli1 = clienteRepository.save(new Cliente(null, "Pedro Rojas",   "11111111-1", "pedro@gmail.com",   "+56911111111"));
        Cliente cli2 = clienteRepository.save(new Cliente(null, "María Torres",  "22222222-2", "maria@gmail.com",   "+56922222222"));

        // ── VENTAS ────────────────────────────────────
        ventaRepository.save(new Venta(null, LocalDate.now(), v1.getPrecio(), "COMPLETADA", vend1, cli1, v1));
        ventaRepository.save(new Venta(null, LocalDate.now(), v2.getPrecio(), "COMPLETADA", vend2, cli2, v2));
    }
}
