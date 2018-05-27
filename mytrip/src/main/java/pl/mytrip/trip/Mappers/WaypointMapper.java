//package pl.mytrip.trip.Mappers;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import pl.mytrip.trip.DTOs.WaypointDTO;
//import pl.mytrip.trip.Model.Waypoint;
//
//@Mapper(componentModel = "spring")
//public interface WaypointMapper {
//
//    @Mapping(target = "waypointId", ignore = true)
//    @Mapping(target = "trip", ignore = true)
//    Waypoint toEntity(WaypointDTO dto);
//
//    WaypointDTO toDto(Waypoint entity);
//}
