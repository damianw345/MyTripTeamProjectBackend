package pl.mytrip.trip.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mytrip.trip.DTOs.Jobs.PresentationStatusDTO;
import pl.mytrip.trip.Services.QueueJobService;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/trips")
public class JobController {

    private final QueueJobService queueJobService;

    @RequestMapping(value = "/{tripId}/poster", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createPoster(@PathVariable String tripId) {
        try {
            return queueJobService.addPosterJob(tripId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/{tripId}/presentation", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String createVideoPresentation(@PathVariable String tripId) {
        try {
            return queueJobService.addVideoPresentationJob(tripId);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/{tripId}/poster", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String getPoster(@PathVariable String tripId) {
        return queueJobService.getPoster(tripId);
    }

    @RequestMapping(value = "/{tripId}/presentation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.CREATED)
    public String getVideoPresentation(@PathVariable String tripId) {
        return queueJobService.getVideoPresentation(tripId);
    }

    @RequestMapping(value = "/{tripId}/posterCreated", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void posterCreated(@PathVariable String tripId, @RequestBody PresentationStatusDTO dto) {
        queueJobService.addPosterUrl(tripId, dto.getResultStorageUrl());
    }

    @RequestMapping(value = "/{tripId}/presentationCreated", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void videoPresentationCreated(@PathVariable String tripId, @RequestBody PresentationStatusDTO dto) {
        queueJobService.addVideoPresentationUrl(tripId, dto.getResultStorageUrl());
    }

    @RequestMapping(value = "/{tripId}/photos/{photoId}/thumbnail", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void thumbnailCreated(@PathVariable String tripId, @PathVariable Long photoId, @RequestBody PresentationStatusDTO dto) {
        queueJobService.addThumbnailUrl(photoId, dto.getResultStorageUrl());
    }

}
