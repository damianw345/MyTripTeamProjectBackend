package pl.mytrip.trip.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mytrip.trip.DTOs.Jobs.PosterJobDTO;
import pl.mytrip.trip.DTOs.Jobs.SimpleVideoPresentationDTO;
import pl.mytrip.trip.DTOs.Jobs.ThumbnailJobDTO;
import pl.mytrip.trip.Mappers.TripMapper;
import pl.mytrip.trip.Model.Photo;
import pl.mytrip.trip.Model.Trip;
import pl.mytrip.trip.Repositories.PhotoRepository;
import pl.mytrip.trip.Repositories.TripRepository;

import javax.ws.rs.NotFoundException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueueJobService {

    private final TripRepository tripRepository;
    private final PhotoRepository photoRepository;
    private final TripMapper tripMapper;

    public String addThumbnailJob(String photoUrl, String tripId, Long photoId) throws JsonProcessingException {
        log.info("QueueJobService::addThumbnailJob");
        ThumbnailJobDTO dto = new ThumbnailJobDTO();
        dto.setCallbackUrl("/" + tripId + "/photos/" + photoId + "/thumbnail");
        dto.setFullImageStorageUrl(photoUrl);

        return executePost("https://mediaservice-trzye.azurewebsites.net/mediaService/addThumbnailJob",
                new ObjectMapper().writeValueAsString(dto));
    }

    public String addPosterJob(String tripId) throws JsonProcessingException {
        PosterJobDTO dto = new PosterJobDTO();
        dto.setTripDTO(tripMapper.toDto(tripRepository.findOne(tripId)));
        dto.setCallbackUrl("/" + tripId + "/posterCreated");

        return executePost("https://mediaservice-trzye.azurewebsites.net/mediaService/addPosterJob",
                new ObjectMapper().writeValueAsString(dto));
    }

    public String addVideoPresentationJob(String tripId) throws JsonProcessingException {
        SimpleVideoPresentationDTO dto = new SimpleVideoPresentationDTO();
        dto.setPhotos(new ArrayList<>(Arrays.asList("1","2","3","4","5","6","7")));
        dto.setCallbackUrl("/" + tripId + "/presentationCreated");

        return executePost("https://mediaservice-trzye.azurewebsites.net/mediaService/addVideoPresentationJob",
                new ObjectMapper().writeValueAsString(dto));
    }

    private static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection;

        try {
            String encoding = Base64.getEncoder().encodeToString(("serviceapp:serviceapp@123").getBytes());
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addPosterUrl(String tripId, String posterUrl) {
        Trip trip = Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new);
        trip.setPoster(posterUrl);
        tripRepository.save(trip);
    }

    public void addVideoPresentationUrl(String tripId, String videoPresentationUrl) {
        Trip trip = Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new);
        trip.setPresentation(videoPresentationUrl);
        tripRepository.save(trip);
    }

//    public void addThumbnailUrl(Long photoId, String thumbnailUrl) {
//        Photo photo = Optional.ofNullable(photoRepository.findOne(photoId))
//                .orElseThrow(NotFoundException::new);
//        photo.setThumbnailUrl(thumbnailUrl);
//        photoRepository.save(photo);
//    }

    public String getPoster(String tripId) {
        return Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new).getPoster();
    }

    public String getVideoPresentation(String tripId) {
        return Optional.ofNullable(tripRepository.findOne(tripId))
                .orElseThrow(NotFoundException::new).getPresentation();
    }
}
