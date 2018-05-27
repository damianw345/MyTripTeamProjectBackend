package pl.mytrip.trip.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.TripMapper;
import pl.mytrip.trip.Repositories.TripRepository;
import pl.mytrip.trip.DTOs.BasicTripDTO;
import pl.mytrip.trip.DTOs.TripDTO;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    public Page<BasicTripDTO> getTrips(Pageable pageable) {
        return Optional.ofNullable(tripRepository.findAll(pageable))
                .map(trips -> tripMapper.toDtoPage(trips, pageable))
                .orElseThrow(NotFoundException::new);
    }

    public TripDTO addTrip(TripDTO dto) {
        Trip entity = Optional.ofNullable(dto)
                .map(tripMapper::toEntity)
                .orElseThrow(BadRequestException::new);
        return tripMapper.toDto(tripRepository.save(entity));
    }

    public TripDTO updateTrip(TripDTO dto, Long id) {
        Trip entity = Optional.ofNullable(dto)
                .map(tripMapper::toEntity)
                .orElseThrow(BadRequestException::new);
        entity.setTripId(id);
        return tripMapper.toDto(tripRepository.save(entity));
    }

    public TripDTO getTrip(Long id) {
        return Optional.ofNullable(tripRepository.findOne(id))
                .map(tripMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    public void deleteTrip(Long id) {
        tripRepository.delete(id);
    }
}
