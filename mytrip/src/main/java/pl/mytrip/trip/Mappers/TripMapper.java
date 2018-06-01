package pl.mytrip.trip.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.mytrip.trip.DTOs.*;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Model.Waypoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
    Waypoint toEntity(WaypointDTO dto);

    @Mapping(target = "trip", ignore = true)
    Waypoint updateEntity(WaypointDTO dto, @MappingTarget Waypoint entity);

    @Mapping(source = "entity.trip.tripId", target = "tripId")
    WaypointDTO toDto(Waypoint entity);

    @Mapping(source = "entity.waypoint.waypointId", target = "waypointId")
    PhotoWaypointDTO toDto(Photo entity);

    BasicTripDTO toBasicDto(Trip entity);

    List<BasicTripDTO> toDto(List<Trip> entities);

    default Page<BasicTripDTO> toDtoPage(Page<Trip> entities, Pageable pageable) {
        return new PageImpl<>(toDto(entities.getContent()), pageable, entities.getTotalElements());
    }
}
