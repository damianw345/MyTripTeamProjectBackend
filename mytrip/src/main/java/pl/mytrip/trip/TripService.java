package pl.mytrip.trip;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.dto.BasicTripDTO;
import pl.mytrip.trip.dto.TripDTO;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TripService {

    private final TripRepository tripRepository;
    private final TripMapper tripMapper;

    Page<BasicTripDTO> getTrips(Pageable pageable) {
        return Optional.ofNullable(tripRepository.findAll(pageable))
                .map(trips -> tripMapper.toDtoPage(trips, pageable))
                .orElseThrow(NotFoundException::new);
    }

    TripDTO addTrip(TripDTO dto) {
        Trip entity = Optional.ofNullable(dto)
                .map(tripMapper::toEntity)
                .orElseThrow(BadRequestException::new);
        return tripMapper.toDto(tripRepository.save(entity));
    }

    TripDTO updateTrip(TripDTO dto, Long id) {
        Trip entity = Optional.ofNullable(dto)
                .map(tripMapper::toEntity)
                .orElseThrow(BadRequestException::new);
        entity.setTripId(id);
        return tripMapper.toDto(tripRepository.save(entity));
    }

    TripDTO getTrip(Long id) {
        return Optional.ofNullable(tripRepository.findOne(id))
                .map(tripMapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    void deleteTrip(Long id) {
        tripRepository.delete(id);
    }
}
