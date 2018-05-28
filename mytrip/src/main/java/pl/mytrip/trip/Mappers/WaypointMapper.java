package pl.mytrip.trip.Mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import pl.mytrip.trip.DTOs.WaypointDTO;
import pl.mytrip.trip.Model.Waypoint;

@Mapper(componentModel = "spring")
public interface WaypointMapper {

    @Mapping(target = "waypointId", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Waypoint toEntity(WaypointDTO dto);

    @Mapping(target = "waypointId", ignore = true)
    @Mapping(target = "trip", ignore = true)
    Waypoint updateEntity(WaypointDTO dto, @MappingTarget Waypoint waypoint);

    WaypointDTO toDto(Waypoint entity);
}
