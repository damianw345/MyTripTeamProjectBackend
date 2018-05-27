package pl.mytrip.trip;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.dto.BasicTripDTO;
import pl.mytrip.trip.dto.TripDTO;
import pl.mytrip.user.LoggedUserGetter;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final LoggedUserGetter loggedUserGetter;

    Page<BasicTripDTO> getTrips(Pageable pageable) {
        return Optional.ofNullable(tripRepository.findByOwner(loggedUserGetter.getLoggedUserLogin(), pageable))
                .map(trips -> tripMapper.toDtoPage(trips, pageable))
                .orElseThrow(NotFoundException::new);
    }

    TripDTO addTrip(TripDTO dto) {
        return Optional.ofNullable(dto)
                .map(tripMapper::toEntity)
                .map(this::updateTripOwner)
                .map(tripRepository::save)
                .map(tripMapper::toDto)
                .orElseThrow(BadRequestException::new);
    }

    TripDTO updateTrip(TripDTO dto, Long id) {
        return Optional.ofNullable(dto)
                .map(trip -> tripMapper.updateEntity(trip, tripRepository.findOne(id)))
                .map(this::checkTripOwner)
                .map(tripRepository::save)
                .map(tripMapper::toDto)
                .orElseThrow(BadRequestException::new);
    }

    TripDTO getTrip(Long id) {
        return Optional.ofNullable(tripRepository.findOne(id))
                .map(this::checkTripOwner)
                .map(tripMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    void deleteTrip(Long id) {
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
}