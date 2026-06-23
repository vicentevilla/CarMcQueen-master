package com.car.CarMcQueen.service;

import com.car.CarMcQueen.dto.VehiculoRequestDTO;
import com.car.CarMcQueen.dto.VehiculoResponseDTO;
import com.car.CarMcQueen.model.TipoVehiculo;
import com.car.CarMcQueen.model.Vehiculo;
import com.car.CarMcQueen.repository.TipoVehiculoRepository;
import com.car.CarMcQueen.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Lógica de negocio
// Recibe y devuelve DTOs (no entidades) mapToDTO convierte Vehiculo = VehiculoResponseDTO 
// guardar y actualizar reciben VehiculoRequestDTO
// Reglas de negocio implementadas:
//   - Patente única
//   - TipoVehiculo debe existir en BD
//   - Patente se guarda en mayúscula

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    private final TipoVehiculoRepository tipoVehiculoRepository;

    private VehiculoResponseDTO mapToDTO(Vehiculo vehiculo) {
        return new VehiculoResponseDTO(
                vehiculo.getId(),
                vehiculo.getPatente(),
                vehiculo.getMarca(),
                vehiculo.getModelo(),
                vehiculo.getAnio(),
                vehiculo.getPrecio(),
                vehiculo.getTipoVehiculo().getNombre()
        );
    }
// Obtener todos los vehiculos
    public List<VehiculoResponseDTO> obtenerTodos() {
        return vehiculoRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
// Obtener por ID
    public Optional<VehiculoResponseDTO> obtenerPorId(Long id) {
        return vehiculoRepository.findById(id).map(this::mapToDTO);
    }
// Guardar nuevo vehículo
    public VehiculoResponseDTO guardar(VehiculoRequestDTO dto) {
        if (vehiculoRepository.existsByPatente(dto.getPatente().toUpperCase())) {
            throw new RuntimeException("Ya existe un vehículo con la patente: " + dto.getPatente());
        }

        TipoVehiculo tipo = tipoVehiculoRepository
                .findById(dto.getTipoVehiculoId())
                .orElseThrow(() -> new RuntimeException(
                        "TipoVehiculo no encontrado con id: " + dto.getTipoVehiculoId()));

        Vehiculo vehiculo = new Vehiculo(
                null,
                dto.getPatente().toUpperCase(),  // Regla: patente en mayúsculas
                dto.getMarca(),
                dto.getModelo(),
                dto.getAnio(),
                dto.getPrecio(),
                tipo
        );

        Vehiculo guardado = vehiculoRepository.save(vehiculo);
        return mapToDTO(guardado);
    }

// Actualizar vehículo existente
    public Optional<VehiculoResponseDTO> actualizar(Long id, VehiculoRequestDTO dto) {

        return vehiculoRepository.findById(id).map(existente -> {
            // Regla: si cambia la patente, verificar que no esté en uso
            String nuevaPatente = dto.getPatente().toUpperCase();
            if (!existente.getPatente().equals(nuevaPatente)
                    && vehiculoRepository.existsByPatente(nuevaPatente)) {
                throw new RuntimeException("Ya existe un vehículo con la patente: " + nuevaPatente);
            }

            TipoVehiculo tipo = tipoVehiculoRepository.findById(dto.getTipoVehiculoId()).orElseThrow
            (() -> new RuntimeException("TipoVehiculo no encontrado con id: " + dto.getTipoVehiculoId()));

            existente.setPatente(nuevaPatente);
            existente.setMarca(dto.getMarca());
            existente.setModelo(dto.getModelo());
            existente.setAnio(dto.getAnio());
            existente.setPrecio(dto.getPrecio());
            existente.setTipoVehiculo(tipo);

            Vehiculo actualizado = vehiculoRepository.save(existente);
            return mapToDTO(actualizado);
        });
    }

    // ── ELIMINAR ─────────────────────────────────────
    public void eliminar(Long id) {
        vehiculoRepository.deleteById(id);
    }

    // ── BÚSQUEDAS (usando los @Query del Repository) ──

    // GET /api/vehiculos/marca?marca=Toyota
    public List<VehiculoResponseDTO> buscarPorMarca(String marca) {
        return vehiculoRepository.findByMarca(marca)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // GET /api/vehiculos/año?desde=2015
    public List<VehiculoResponseDTO> buscarPorAnioDesde(Integer anio) {
        return vehiculoRepository.findByAnio(anio)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // GET /api/vehiculos/tipo/{tipoId}
    public List<VehiculoResponseDTO> buscarPorTipo(Long tipoId) {
        return vehiculoRepository.findByTipoVehiculoId(tipoId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
