package pl.mytrip.trip.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.DTOs.BasicWaypointDTO;
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


    public WaypointDTO addWaypoint(BasicWaypointDTO dto, String tripId) {
        Waypoint entity = Optional.ofNullable(dto)
                .map(f -> waypointMapper.toEntity(dto, tripId))
                .orElseThrow(BadRequestException::new);
        return waypointMapper.toDto(waypointRepository.save(entity));
    }

    public WaypointDTO updateWaypoint(BasicWaypointDTO dto, String tripId, Long wayId) {
        Waypoint entity = Optional.ofNullable(dto)
                .map(f -> waypointMapper.toEntity(dto, tripId))
                .orElseThrow(BadRequestException::new);
        entity.setWaypointId(wayId);
        return waypointMapper.toDto(waypointRepository.save(entity));
    }

    public void deleteWaypoint(Long id) {
        waypointRepository.delete(id);
    }
}
