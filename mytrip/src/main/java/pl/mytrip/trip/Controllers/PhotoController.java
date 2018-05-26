package pl.mytrip.trip.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mytrip.trip.Services.PhotoService;
import pl.mytrip.trip.dto.PhotoDTO;

import java.io.IOException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/trips")
public class PhotoController {

    private final PhotoService photoService;

    @RequestMapping(value = "/{tripId}/photos", method = RequestMethod.POST, consumes = "multipart/form-data")
    String addPhoto(@PathVariable Long tripId, @RequestParam String photoInfo, @RequestParam MultipartFile photo) {
        try {
            byte[] myPhoto = photo.getBytes();
            PhotoDTO photoDTO  = new ObjectMapper().readValue(photoInfo, PhotoDTO.class);
            return photoService.addPhoto(tripId, photoDTO, myPhoto);
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @RequestMapping(value = "/{tripId}/photos/{photoId}", method = RequestMethod.PUT)
    String updatePhoto(@RequestBody PhotoDTO dto, @PathVariable Long tripId, @PathVariable Long photoId) {
        return photoService.updatePhoto(dto, tripId, photoId);
    }

    @RequestMapping(value = "/{tripId}/photos/{photoId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePhoto(@PathVariable Long tripId, @PathVariable Long photoId) {
        photoService.deletePhoto(tripId, photoId);
    }
}
