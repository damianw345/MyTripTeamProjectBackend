package pl.mytrip.trip.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mytrip.trip.DTOs.BasicWaypointDTO;
import pl.mytrip.trip.DTOs.WaypointDTO;
import pl.mytrip.trip.Services.WaypointService;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/trips")
public class WaypointController {

    private final WaypointService waypointService;

    @RequestMapping(value = "/{tripId}/waypoints", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    WaypointDTO addWaypoint(@RequestBody WaypointDTO dto, @PathVariable String tripId) {
        return waypointService.addWaypoint(tripId, dto);
    }

    @RequestMapping(value = "/{tripId}/waypoints/{wayId}", method = RequestMethod.PUT)
    WaypointDTO updateWaypoint(@RequestBody WaypointDTO dto, @PathVariable String tripId, @PathVariable Long wayId) {
        return waypointService.updateWaypoint(dto, wayId);
    }

    @RequestMapping(value = "/{tripId}/waypoints/{wayId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteWaypoint(@PathVariable String tripId, @PathVariable Long wayId) {
        waypointService.deleteWaypoint(wayId);
    }
}
