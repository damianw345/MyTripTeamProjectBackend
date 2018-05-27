package pl.mytrip.trip.Mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.mytrip.trip.DTOs.WaypointDTO;
import pl.mytrip.trip.Model.Waypoint;
import pl.mytrip.trip.Repositories.TripRepository;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WaypointMapperClass {

    private final TripRepository tripRepository;

    public Waypoint toEntity(WaypointDTO dto){
        return new Waypoint(null, dto.getLongitude(), dto.getLatitude(), dto.getDate(), tripRepository.findOne(dto.getTripId()));
    }

    public WaypointDTO toDto(Waypoint entity){
        return new WaypointDTO(entity.getWaypointId(), entity.getLongitude(), entity.getLatitude(), entity.getDate());
    }
}
