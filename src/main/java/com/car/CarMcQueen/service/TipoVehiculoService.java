package com.car.CarMcQueen.service;

import com.car.CarMcQueen.model.TipoVehiculo;
import com.car.CarMcQueen.repository.TipoVehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Slf4j (Lombok): genera automáticamente un logger 'log' para usar log.info(), log.warn(), etc.

@Service
@RequiredArgsConstructor
public class TipoVehiculoService {

    private final TipoVehiculoRepository tipoVehiculoRepository;

    public List<TipoVehiculo> obtenerTodos() {
        return tipoVehiculoRepository.findAll();
    }

    public Optional<TipoVehiculo> obtenerPorId(Long id) {
        return tipoVehiculoRepository.findById(id);
    }

    public TipoVehiculo guardar(TipoVehiculo tipoVehiculo) {
        TipoVehiculo guardado = tipoVehiculoRepository.save(tipoVehiculo);
        return guardado;
    }

    public Optional<TipoVehiculo> actualizar(Long id, TipoVehiculo datos) {
        return tipoVehiculoRepository.findById(id).map(existente -> {
            existente.setNombre(datos.getNombre());
            existente.setDescripcion(datos.getDescripcion());
            TipoVehiculo actualizado = tipoVehiculoRepository.save(existente);
            return actualizado;
        });
    }

    public void eliminar(Long id) {
        tipoVehiculoRepository.deleteById(id);
    }
}
