package pl.mytrip.trip.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.DTOs.WaypointDTO;
import pl.mytrip.trip.Mappers.WaypointMapper;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Model.Waypoint;
import pl.mytrip.trip.Repositories.TripRepository;
import pl.mytrip.trip.Repositories.WaypointRepository;
import pl.mytrip.user.LoggedUserGetter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WaypointService {

    private final WaypointRepository waypointRepository;
    private final WaypointMapper waypointMapper;
    private final LoggedUserGetter loggedUserGetter;
    private final TripRepository tripRepository;

    public WaypointDTO addWaypoint(Long tripId, WaypointDTO dto) {
        Trip entity = Optional.ofNullable(tripRepository.findOne(tripId))
                .map(this::checkTripOwner)
                .orElseThrow(NotFoundException::new);
        return Optional.ofNullable(dto)
                .map(waypointMapper::toEntity)
                .map(waypoint -> { waypoint.setTrip(entity); return waypoint; })
                .map(waypointRepository::save)
                .map(waypointMapper::toDto)
                .orElseThrow(BadRequestException::new);
    }

    public WaypointDTO updateWaypoint(WaypointDTO dto, Long id) {
        Waypoint entity = getWaypointEntityAndCheckOwnership(id);
        return Optional.ofNullable(dto)
                .map(waypoint -> waypointMapper.updateEntity(waypoint, entity))
                .map(waypoint -> { waypoint.setWaypointId(id); return waypoint; })
                .map(waypointRepository::save)
                .map(waypointMapper::toDto)
                .orElseThrow(BadRequestException::new);
    }

    public void deleteWaypoint(Long id) {
        getWaypointEntityAndCheckOwnership(id);
        waypointRepository.delete(id);
    }

    private Waypoint checkWaypointOwner(Waypoint waypoint) {
        if(!loggedUserGetter.getLoggedUserLogin().equals(waypoint.getTrip().getOwner())) {
            throw new NotAuthorizedException("Forbidden resource");
        }
        return waypoint;
    }

    private Trip checkTripOwner(Trip trip) {
        if(!loggedUserGetter.getLoggedUserLogin().equals(trip.getOwner())) {
            throw new NotAuthorizedException("Forbidden resource");
        }
        return trip;
    }

    private Waypoint getWaypointEntityAndCheckOwnership(Long id) {
        return Optional.ofNullable(waypointRepository.findOne(id))
                .map(this::checkWaypointOwner)
                .orElseThrow(NotFoundException::new);
    }
}
