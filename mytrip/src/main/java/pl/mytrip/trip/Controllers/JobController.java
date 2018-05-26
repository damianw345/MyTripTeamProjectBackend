//package pl.mytrip.trip.Controllers;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import pl.mytrip.trip.Services.QueueJobService;
//import pl.mytrip.trip.dto.TripDTO;
//
//@RestController
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//@RequestMapping("/api/trips")
//public class JobController {
//
//    private final QueueJobService queueJobService;
//
//    @RequestMapping(value = "/{tripId}/poster", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.CREATED)
//    public TripDTO createPoster(@PathVariable Long tripId) {
//        return queueJobService.addPosterJob(tripId);
//    }
//
//}
