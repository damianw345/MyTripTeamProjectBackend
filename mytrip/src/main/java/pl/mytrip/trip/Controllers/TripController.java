package pl.mytrip.trip.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mytrip.trip.Services.TripService;
import pl.mytrip.trip.DTOs.BasicTripDTO;
import pl.mytrip.trip.DTOs.TripDTO;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    @RequestMapping(method = RequestMethod.GET)
    Page<BasicTripDTO> getTrips(Pageable pageable) {
        return tripService.getTrips(pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    TripDTO addTrip(@RequestBody TripDTO dto) {
        return tripService.addTrip(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    TripDTO getTrip(@PathVariable Long id) {
        return tripService.getTrip(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    TripDTO updateTrip(@RequestBody TripDTO dto, @PathVariable Long id) {
        return tripService.updateTrip(dto, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
    }
}
