package pl.mytrip.trip.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.DTOs.WaypointDTO;
import pl.mytrip.trip.Mappers.WaypointMapperClass;
import pl.mytrip.trip.Model.Waypoint;
import pl.mytrip.trip.Repositories.WaypointRepository;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WaypointService {

    private final WaypointRepository waypointRepository;
    private final WaypointMapperClass waypointMapper;

    public WaypointDTO addWaypoint(WaypointDTO dto) {
        Waypoint entity = Optional.ofNullable(dto)
                .map(waypointMapper::toEntity)
                .orElseThrow(BadRequestException::new);
        return waypointMapper.toDto(waypointRepository.save(entity));
    }

    public WaypointDTO updateWaypoint(WaypointDTO dto, Long id) {
        Waypoint entity = Optional.ofNullable(dto)
                .map(waypointMapper::toEntity)
                .orElseThrow(BadRequestException::new);
        entity.setWaypointId(id);
        return waypointMapper.toDto(waypointRepository.save(entity));
    }

    public void deleteWaypoint(Long id) {
        waypointRepository.delete(id);
    }
}
