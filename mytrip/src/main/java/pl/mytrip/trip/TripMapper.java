package pl.mytrip.trip;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.mytrip.trip.dto.BasicTripDTO;
import pl.mytrip.trip.dto.TripDTO;
import pl.mytrip.trip.dto.TripPointDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "tripId", ignore = true)
    @Mapping(target = "poster", ignore = true)
    @Mapping(target = "presentation", ignore = true)
    @Mapping(target = "cachedMap", ignore = true)
    Trip toEntity(TripDTO dto);

    TripDTO toDto(Trip entity);

    @Mapping(target = "waypointId", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Waypoint toEntity(TripPointDTO dto);

    TripPointDTO toDto(Waypoint entity);

    BasicTripDTO toBasicDto(Trip entity);

    List<BasicTripDTO> toDto(List<Trip> entities);

    default Page<BasicTripDTO> toDtoPage(Page<Trip> entities, Pageable pageable) {
        return new PageImpl<>(toDto(entities.getContent()), pageable, entities.getTotalElements());
    }
}
