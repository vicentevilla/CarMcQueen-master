package com.car.CarMcQueen.service;

import com.car.CarMcQueen.dto.VehiculoRequestDTO;
import com.car.CarMcQueen.dto.VehiculoResponseDTO;
import com.car.CarMcQueen.model.TipoVehiculo;
import com.car.CarMcQueen.model.Vehiculo;
import com.car.CarMcQueen.repository.TipoVehiculoRepository;
import com.car.CarMcQueen.repository.VehiculoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class VehiculoServiceTest {

    @Autowired
    private VehiculoService vehiculoService;

    @MockitoBean
    private VehiculoRepository vehiculoRepository;

    @MockitoBean
    private TipoVehiculoRepository tipoVehiculoRepository;

    // ── HELPER ───────────────────────────────────────
    private Vehiculo crearVehiculo() {
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedán", "4 puertas");
        return new Vehiculo(1L, "AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), tipo);
    }

    private VehiculoRequestDTO crearDTO() {
        return new VehiculoRequestDTO("AB1234", "Toyota", "Corolla", 2020, new BigDecimal("15000000"), 1L);
    }

    // ── TEST 1: obtener todos ─────────────────────────
    @Test
    public void testObtenerTodos() {
        // Given
        Vehiculo vehiculo = crearVehiculo();
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));

        // When
        List<VehiculoResponseDTO> resultado = vehiculoService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("AB1234", resultado.get(0).getPatente());
    }

    // ── TEST 2: obtener por id existente ──────────────
    @Test
    public void testObtenerPorIdExistente() {
        // Given
        Vehiculo vehiculo = crearVehiculo();
        when(vehiculoRepository.findById(1L)).thenReturn(Optional.of(vehiculo));

        // When
        Optional<VehiculoResponseDTO> resultado = vehiculoService.obtenerPorId(1L);

        // Then
        assertTrue(resultado.isPresent());
        assertEquals("Toyota", resultado.get().getMarca());
    }

    // ── TEST 3: obtener por id inexistente ────────────
    @Test
    public void testObtenerPorIdInexistente() {
        // Given
        when(vehiculoRepository.findById(99L)).thenReturn(Optional.empty());

        // When
        Optional<VehiculoResponseDTO> resultado = vehiculoService.obtenerPorId(99L);

        // Then
        assertFalse(resultado.isPresent());
    }

    // ── TEST 4: guardar vehículo nuevo ────────────────
    @Test
    public void testGuardarVehiculo() {
        // Given
        VehiculoRequestDTO dto = crearDTO();
        TipoVehiculo tipo = new TipoVehiculo(1L, "Sedán", "4 puertas");
        Vehiculo vehiculo = crearVehiculo();

        when(vehiculoRepository.existsByPatente("AB1234")).thenReturn(false);
        when(tipoVehiculoRepository.findById(1L)).thenReturn(Optional.of(tipo));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        // When
        VehiculoResponseDTO resultado = vehiculoService.guardar(dto);

        // Then
        assertNotNull(resultado);
        assertEquals("AB1234", resultado.getPatente());
        assertEquals("Sedán", resultado.getTipoVehiculoNombre());
    }

    // ── TEST 5: guardar con patente duplicada ─────────
    @Test
    public void testGuardarPatenteDuplicada() {
        // Given
        VehiculoRequestDTO dto = crearDTO();
        when(vehiculoRepository.existsByPatente("AB1234")).thenReturn(true);

        // When & Then
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> vehiculoService.guardar(dto));
        assertTrue(ex.getMessage().contains("Ya existe un vehículo con la patente"));
    }

    // ── TEST 6: eliminar vehículo ─────────────────────
    @Test
    public void testEliminar() {
        // Given
        doNothing().when(vehiculoRepository).deleteById(1L);

        // When
        vehiculoService.eliminar(1L);

        // Then
        verify(vehiculoRepository, times(1)).deleteById(1L);
    }
}
