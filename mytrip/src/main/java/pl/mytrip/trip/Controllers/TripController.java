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

import java.util.Date;

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
        System.out.println(new Date().toString());
        return tripService.addTrip(dto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    TripDTO getTrip(@PathVariable String id) {
        return tripService.getTrip(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    TripDTO updateTrip(@RequestBody TripDTO dto, @PathVariable String id) {
        return tripService.updateTrip(dto, id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteTrip(@PathVariable String id) {
        tripService.deleteTrip(id);
    }
}
