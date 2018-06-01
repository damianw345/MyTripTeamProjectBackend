package pl.mytrip.trip.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mytrip.trip.DTOs.BasicTripDTO;
import pl.mytrip.trip.DTOs.TripDTO;
import pl.mytrip.trip.DTOs.WaypointDTO;
import pl.mytrip.trip.Mappers.TripMapper;
import pl.mytrip.trip.Mappers.WaypointMapper;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Repositories.TripRepository;
import pl.mytrip.trip.Repositories.WaypointRepository;
import pl.mytrip.user.LoggedUserGetter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final LoggedUserGetter loggedUserGetter;
    private final WaypointRepository waypointRepository;

    public Page<BasicTripDTO> getTrips(Pageable pageable) {
        return Optional.ofNullable(tripRepository.findByOwner(loggedUserGetter.getLoggedUserLogin(), pageable))
                .map(trips -> tripMapper.toDtoPage(trips, pageable))
                .orElseThrow(NotFoundException::new);
    }

    public TripDTO getTrip(String id) {
        return Optional.ofNullable(tripRepository.findOne(id))
                .map(this::checkTripOwner)
                .map(tripMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public TripDTO addTrip(TripDTO dto) {
        return Optional.ofNullable(dto)
                .map(tripMapper::toEntity)
                .map(this::updateTripOwner)
                .map(tripRepository::save)
                .map(this::updateTripWaypoints)
                .map(tripMapper::toDto)
                .orElseThrow(BadRequestException::new);
    }

    @Transactional
    public TripDTO updateTrip(TripDTO dto, String id) {
        Trip entity = Optional.ofNullable(tripRepository.findOne(id))
                .map(this::checkTripOwner)
                .orElseThrow(NotFoundException::new);
        return Optional.of(dto)
                .map(this::persistTripWaypoints)
                .map(trip -> tripMapper.updateEntity(trip, entity))
                .map(tripRepository::save)
                .map(tripMapper::toDto)
                .orElseThrow(BadRequestException::new);
    }

    @Transactional
    public void deleteTrip(String id) {
        Optional.ofNullable(tripRepository.findOne(id))
                .map(this::checkTripOwner)
                .map(Trip::getTripId)
                .ifPresent(tripRepository::delete);
    }

    private Trip checkTripOwner(Trip trip) {
        if(!loggedUserGetter.getLoggedUserLogin().equals(trip.getOwner())) {
            throw new NotAuthorizedException("Forbidden resource");
        }
        return trip;
    }

    private Trip updateTripOwner(Trip trip) {
        trip.setOwner(loggedUserGetter.getLoggedUserLogin());
        return trip;
    }

    private Trip updateTripWaypoints(Trip trip) {
        if(Objects.nonNull(trip.getPoints())){
            trip.getPoints().forEach(waypoint -> waypoint.setTrip(trip));
        }
        return trip;
    }

    private TripDTO persistTripWaypoints(TripDTO dto) {
        for(WaypointDTO waypointDTO : dto.getWaypoints()) {
            Optional.ofNullable(waypointRepository.findOne(waypointDTO.getWaypointId()))
                    .map(waypointEntity -> tripMapper.updateEntity(waypointDTO, waypointEntity))
                    .map(waypointRepository::save);
        }
        return dto;
    }
}