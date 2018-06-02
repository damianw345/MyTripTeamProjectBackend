package pl.mytrip.trip.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mytrip.trip.DTOs.PhotoInfoDTO;
import pl.mytrip.trip.Services.PhotoService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/trips")
public class PhotoController {

    private final PhotoService photoService;

    @RequestMapping(value = "/{tripId}/photos", method = RequestMethod.POST, consumes = "multipart/form-data")
    String addPhoto(@PathVariable String tripId, @RequestParam String photoInfo, @RequestParam MultipartFile photo) {
        try {
            String [] fileName = photo.getOriginalFilename().split("[.]");
            String extension = fileName[fileName.length - 1];
            System.out.println("photo name:" + photo.getOriginalFilename()
                    + "\n extension: " + fileName[fileName.length - 1]);
            byte[] myPhoto = photo.getBytes();
            PhotoInfoDTO dto  = new ObjectMapper().readValue(photoInfo, PhotoInfoDTO.class);
            return photoService.addPhoto(tripId, myPhoto, dto, extension);
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

//    @RequestMapping(value = "/{tripId}/photos/{photoId}", method = RequestMethod.PUT)
//    String updatePhoto(@RequestBody PhotoDTO dto, @PathVariable String tripId, @PathVariable Long photoId) {
//        return photoService.updatePhoto(dto, tripId, photoId);
//    }

    @RequestMapping(value = "/{tripId}/photos/{photoId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody void deletePhoto(@PathVariable String tripId, @PathVariable Long photoId) {
        photoService.deletePhoto(tripId, photoId);
    }
}
