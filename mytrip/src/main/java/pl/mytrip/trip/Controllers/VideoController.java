package pl.mytrip.trip.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mytrip.trip.DTOs.PhotoInfoDTO;
import pl.mytrip.trip.DTOs.VideoDTO;
import pl.mytrip.trip.Services.PhotoService;
import pl.mytrip.trip.Services.VideoService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/trips")
public class VideoController {

    private final VideoService videoService;

    @RequestMapping(value = "/{tripId}/videos", method = RequestMethod.POST, consumes = "multipart/form-data")
    String addVideo(@PathVariable String tripId, @RequestParam String videoInfo, @RequestParam MultipartFile video) {
        try {
            String [] fileName = video.getOriginalFilename().split("[.]");
            String extension = fileName[fileName.length - 1];
            byte[] myVideo = video.getBytes();
            VideoDTO dto  = new ObjectMapper().readValue(videoInfo, VideoDTO.class);

            return videoService.addVideo(tripId, myVideo, dto, extension);
        } catch (IOException e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @RequestMapping(value = "/{tripId}/videos/{videoId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody void deleteVideo(@PathVariable String tripId, @PathVariable Long videoId) {
        videoService.deleteVideo(tripId, videoId);
    }
}
