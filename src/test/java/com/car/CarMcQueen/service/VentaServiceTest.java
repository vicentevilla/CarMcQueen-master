package com.car.CarMcQueen.service;

import com.car.CarMcQueen.model.*;
import com.car.CarMcQueen.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class VentaServiceTest {

    @Autowired
    private VentaService ventaService;

    @MockitoBean
    private VentaRepository ventaRepository;

    @MockitoBean
    private VendedorRepository vendedorRepository;

    @MockitoBean
    private ClienteRepository clienteRepository;

    @MockitoBean
    private VehiculoRepository vehiculoRepository;

    // ── HELPER ───────────────────────────────────────
    private Venta crearVenta() {
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedán", "4 puertas");
        Vehiculo vehiculo = new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
        Vendedor vendedor = new Vendedor(1L, "Carlos", "12345678-9", "carlos@mail.cl", "+56912345678");
        Cliente cliente   = new Cliente(1L, "Pedro",   "11111111-1", "pedro@mail.cl",  "+56911111111");
        return new Venta(1L, LocalDate.now(), new BigDecimal("15000000"), "COMPLETADA", vendedor, cliente, vehiculo);
    }

    // ── TEST 1: obtener todas las ventas ──────────────
    @Test
    public void testObtenerTodos() {
        // Given
        when(ventaRepository.findAll()).thenReturn(List.of(crearVenta()));

        // When
        List<Venta> resultado = ventaService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("COMPLETADA", resultado.get(0).getEstado());
    }

    // ── TEST 2: crear venta exitosa ───────────────────
    @Test
    public void testCrearVentaExitosa() {
        // Given
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedán", "4 puertas");
        Vehiculo vehiculo = new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
        Vendedor vendedor = new Vendedor(1L, "Carlos", "12345678-9", "carlos@mail.cl", "+56912345678");
        Cliente cliente   = new Cliente(1L, "Pedro",   "11111111-1", "pedro@mail.cl",  "+56911111111");
        Venta venta       = crearVenta();

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(ventaRepository.vehiculoYaVendido(1L)).thenReturn(false);
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        // When
        Venta resultado = ventaService.crear(1L, 1L, 1L);

        // Then
        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstado());
        assertEquals(new BigDecimal("15000000"), resultado.getPrecioTotal());
    }

    // ── TEST 3: crear venta con vehículo ya vendido ───
    @Test
    public void testCrearVentaVehiculoYaVendido() {
        // Given
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedán", "4 puertas");
        Vehiculo vehiculo = new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
        Vendedor vendedor = new Vendedor(1L, "Carlos", "12345678-9", "carlos@mail.cl", "+56912345678");
        Cliente cliente   = new Cliente(1L, "Pedro",   "11111111-1", "pedro@mail.cl",  "+56911111111");

        when(vendedorRepository.findById(1L)).thenReturn(Optional.of(vendedor));
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));
        when(ventaRepository.vehiculoYaVendido(1L)).thenReturn(true);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> ventaService.crear(1L, 1L, 1L));
        assertTrue(ex.getMessage().contains("ya fue vendido"));
    }

    // ── TEST 4: anular venta ──────────────────────────
    @Test
    public void testAnularVenta() {
        // Given
        Venta venta = crearVenta();
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        // When
        Optional<Venta> resultado = ventaService.anular(1L);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("ANULADA", resultado.get().getEstado());
    }
}
