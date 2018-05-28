package pl.mytrip.trip.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Model.Waypoint;
import pl.mytrip.trip.DTOs.BasicTripDTO;
import pl.mytrip.trip.DTOs.TripDTO;
import pl.mytrip.trip.DTOs.TripPointDTO;

import javax.ws.rs.BadRequestException;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TripMapper {

    @Mapping(target = "tripId", ignore = true)
    @Mapping(target = "poster", ignore = true)
    @Mapping(target = "presentation", ignore = true)
    @Mapping(target = "cachedMap", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(source = "waypoints", target = "points")
    Trip toEntity(TripDTO dto);

    @Mapping(target = "tripId", ignore = true)
    @Mapping(target = "poster", ignore = true)
    @Mapping(target = "presentation", ignore = true)
    @Mapping(target = "cachedMap", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "points", ignore = true)
    Trip updateEntity(TripDTO dto, @MappingTarget Trip trip);

    @Mapping(source = "points", target = "waypoints")
    TripDTO toDto(Trip entity);

    @Mapping(target = "trip", ignore = true)
    Waypoint toEntity(TripPointDTO dto);

    @Mapping(target = "trip", ignore = true)
    Waypoint updateEntity(TripPointDTO dto, @MappingTarget Waypoint entity);

    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "videos", ignore = true)
    TripPointDTO toDto(Waypoint entity);

    BasicTripDTO toBasicDto(Trip entity);

    List<BasicTripDTO> toDto(List<Trip> entities);

    default Page<BasicTripDTO> toDtoPage(Page<Trip> entities, Pageable pageable) {
        return new PageImpl<>(toDto(entities.getContent()), pageable, entities.getTotalElements());
    }
}
